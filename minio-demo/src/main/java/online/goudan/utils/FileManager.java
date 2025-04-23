package online.goudan.utils;

import io.minio.BucketExistsArgs;
import io.minio.GetObjectArgs;
import io.minio.GetObjectResponse;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.SetBucketPolicyArgs;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import io.minio.http.Method;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class FileManager {
    private static MinioClient minioClient;
    private static String accessKey = "iUtWQcxBsTozqGz30bc3";
    private static String secretKey = "avXQsZ5fA0tuWRTNOb3pdbQonNCaC0l6VGHxdh8n";

    private static String ossUrl = "https://oss.91beiming.com";

    private static AtomicInteger counter = new AtomicInteger(1);

    static {
        minioClient =
                MinioClient.builder()
                        .endpoint(ossUrl)
                        .credentials(accessKey, secretKey)
                        .build();
    }

    public static void main(String[] args) throws Exception {
        GetPresignedObjectUrlArgs urlArgs = GetPresignedObjectUrlArgs.builder()
                .bucket("test")
                .object("aaa.jpg")
                .expiry(30, TimeUnit.MINUTES)
                .method(Method.PUT)
                .build();
        String presignedObjectUrl = minioClient.getPresignedObjectUrl(urlArgs);
        System.out.println(presignedObjectUrl);
    }

    public FileResult getObject(String bucketName, String finalFileName) throws Exception {
        GetObjectResponse response = minioClient.getObject(GetObjectArgs.builder()
                .bucket(bucketName)
                .object(finalFileName)
                .build());
        return FileResult.builder()
                .mediaType(MediaTypeFactory.getMediaType(finalFileName).map(MediaType::toString).orElse(null))
                .inputStream(response)
                .build();
    }

    public FileResult putObjectPublic(String bucketName, MultipartFile multipartFile) throws Exception {
        checkPulbicBucket(bucketName);
        return putObject(bucketName, multipartFile);
    }

    public FileResult putObjectPrivate(String bucketName, MultipartFile multipartFile) throws Exception {
        checkBucket(bucketName);
        return putObject(bucketName, multipartFile);
    }


    private FileResult putObject(String bucketName, MultipartFile multipartFile) throws IOException, ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, NoSuchAlgorithmException, ServerException, XmlParserException {
        String originalFilename = multipartFile.getOriginalFilename();
        String finalFileName = getNewFileName() + originalFilename.substring(originalFilename.lastIndexOf("."));
        PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                .bucket(bucketName)
                .object(finalFileName)
                .contentType(multipartFile.getContentType())
                .stream(multipartFile.getInputStream(), multipartFile.getSize(), -1)
                .build();
        minioClient.putObject(putObjectArgs);
        return FileResult.builder()
                .ossUrl(ossUrl)
                .bucketName(bucketName)
                .finalFileName(finalFileName)
                .build();
    }

    private void checkPulbicBucket(String bucketName) throws Exception {
        checkBucketExistsAndCreate(bucketName);
        setPolicyPublic(bucketName);
    }

    private void checkBucket(String bucketName) throws Exception {
        checkBucketExistsAndCreate(bucketName);
    }

    /**
     * 检查bucket是否已经存在，不存在则创建
     *
     * @param bucketName
     * @throws Exception
     */
    private void checkBucketExistsAndCreate(String bucketName) throws Exception {
        if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }
    }

    private String getNewFileName() {
//        return currentAddress + Clock.system(ZoneId.of("GMT+8")).millis() + counter.getAndAdd(1);
        return null;
    }

    /**
     * 设置bucket为只读
     *
     * @param bucketName
     * @throws Exception
     */
    private void setPolicyPublic(String bucketName) throws Exception {
        String config = """
                 {
                     "Statement": [
                         {
                             "Action": "s3:GetObject",
                             "Effect": "Allow",
                             "Principal": "*",
                             "Resource": "arn:aws:s3:::%s/*"
                         }
                     ],
                     "Version": "2012-10-17"
                 }
                """;
        minioClient.setBucketPolicy(SetBucketPolicyArgs.builder()
                .bucket(bucketName)
                .config(String.format(config, bucketName))
                .build());
    }

    @Data
    @Builder
    @AllArgsConstructor
    public static class FileResult {
        private String ossUrl;
        private String bucketName;

        private String finalFileName;

        private String origionFileName;
        private String mediaType;
        private InputStream inputStream;

        public String getMediaType() {
            if (origionFileName != null) {
                return MediaTypeFactory.getMediaType(origionFileName).map(Objects::toString).orElse(null);
            }
            if (finalFileName != null) {
                return MediaTypeFactory.getMediaType(finalFileName).map(Objects::toString).orElse(null);
            }
            return mediaType;
        }
    }
}
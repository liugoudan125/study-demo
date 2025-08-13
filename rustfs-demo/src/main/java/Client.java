import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.net.URI;
import java.nio.file.Paths;

/**
 * Client
 */
public class Client {

    public static void main(String[] args) {
        S3Client s3Client = S3Client.builder()
                .forcePathStyle(true)
                .endpointOverride(URI.create("http://172.17.249.234:9000"))
                .region(Region.US_EAST_1)
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create("liugoudan", "Qwerty7878")
                ))
                .build();

//        CreateBucketResponse bucketResponse = s3Client.createBucket(CreateBucketRequest.builder()
//                .bucket("test")
//                .build());

        PutObjectResponse response = s3Client.putObject(
                PutObjectRequest.builder()
                        .bucket("test")
                        .key("test.png")
                        .build(),
                Paths.get("D:\\好看的\\photo\\00a5dbe0d2ea4fdbb8a2d125c9838006.png")
        );
        System.out.println(response.toString());
    }
}

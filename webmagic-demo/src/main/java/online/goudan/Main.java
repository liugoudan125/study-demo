package online.goudan;

import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;
import io.minio.UploadObjectArgs;
import io.minio.UploadSnowballObjectsArgs;
import online.goudan.processor.HuiChePageProcessor;
import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.processor.example.GithubRepoPageProcessor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Optional;
import java.util.UUID;

/**
 * @author
 * @date 2023/5/8 17:19
 * @desc Main
 */
public class Main {
    public static void main(String[] args) throws Exception {
        /*Spider.create(new HuiChePageProcessor())
                .addUrl("https://mm.enterdesk.com")
                .setDownloader(new HttpClientDownloader())
                .thread(5)
                .run();*/
        MinioClient minioClient = new MinioClient.Builder()
                .endpoint("http://localhost:9900")
                .credentials("goudan", "goudan125")
                .build();

        File file = new File("C:\\Users\\lcl94\\Documents\\备份\\头像\\aa.png");
//        Optional<MediaType> mediaType = MediaTypeFactory.getMediaType(file.getName());
//        mediaType.ifPresent(
//                mediaType1 -> System.out.println(mediaType1.toString())
//        );
        UploadObjectArgs objectArgs = UploadObjectArgs.builder()
                .filename("C:\\Users\\lcl94\\Documents\\备份\\头像\\aa.png")
                .contentType("image/png")
                .object("aa/bb/cc/" + UUID.randomUUID().toString() + ".png")
                .bucket("goudan")
                .build();
        FileInputStream stream = new FileInputStream("C:\\Users\\lcl94\\Documents\\备份\\头像\\aa.png");
        PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                .bucket("goudan")
                .contentType("image/png")
                .stream(stream, stream.available(), -1)
                .object(UUID.randomUUID().toString().replace("-", "") + "/aa.png")
                .build();
        ObjectWriteResponse objectWriteResponse = minioClient.putObject(putObjectArgs);
        System.out.println(objectWriteResponse.object() + ", " + objectWriteResponse.etag());

        ObjectWriteResponse response = minioClient.uploadObject(objectArgs);

        System.out.println(response.object() + ", " + response.etag());
    }
}

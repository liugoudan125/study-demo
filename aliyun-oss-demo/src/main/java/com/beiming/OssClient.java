package com.beiming;

import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * OssClient
 */
public class OssClient {


    public static void main(String[] args) throws IOException {
        String bucketName = "xxx";
        ObjectListing objectListing = OssUtils.listObjects(bucketName);
        long s = System.currentTimeMillis();
        for (OSSObjectSummary objectSummary : objectListing.getObjectSummaries()) {
            String key = objectSummary.getKey();
//            InputStream inputStream = OssUtils.getObject(bucketName, key);
//            if (inputStream == null) {
//                System.out.println("没有该文件");
//                return;
//            }
            File wavFile = new File(new File("c:\\wav"), key);
//            IOUtils.copy(inputStream, new FileOutputStream(wavFile));
            String[] names = key.split("\\.");
            String newFileName = "%s.%s".formatted(names[0], "mp3");
            File file = new File(new File("c:\\mp3"), newFileName);
            AudioUtils.toMp3(wavFile, file);
        }
        System.out.println(System.currentTimeMillis() - s);
    }

}

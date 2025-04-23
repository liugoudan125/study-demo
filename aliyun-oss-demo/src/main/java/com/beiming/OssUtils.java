package com.beiming;

import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.common.auth.CredentialsProvider;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.comm.SignVersion;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectListing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

/**
 * OssUtils
 */
public class OssUtils {

    private static final Logger log = LoggerFactory.getLogger(OssUtils.class);
    private static final CredentialsProvider credentialsProvider;
    private static final OSS ossClient;

    static {
        credentialsProvider = CredentialsProviderFactory.newDefaultCredentialProvider("xxx", "xxx");
        // 创建OSSClient实例。
        ClientBuilderConfiguration clientBuilderConfiguration = new ClientBuilderConfiguration();
        clientBuilderConfiguration.setSignatureVersion(SignVersion.V4);
        ossClient = OSSClientBuilder.create()
                .endpoint("https://oss-cn-shanghai.aliyuncs.com")
                .credentialsProvider(credentialsProvider)
                .clientConfiguration(clientBuilderConfiguration)
                .region("cn-shanghai")
                .build();
    }



    public static ObjectListing listObjects(String bucketName) {
        return ossClient.listObjects(bucketName);
    }

    public static InputStream getObject(String bucketName, String key) {
        try {
            OSSObject object = ossClient.getObject(bucketName, key);
            return object.getObjectContent();
        } catch (OSSException ossException) {
            String errorCode = ossException.getErrorCode();
            GetObjectErrorEnum errorEnum = GetObjectErrorEnum.getByErrorCode(errorCode);
            if (null == errorEnum) {
                log.error("getObject不存在的错误码[{}],[{}]", errorCode, key);
            } else {
                log.error("{},[{}]", errorEnum.getExplain(), key);
            }
            return null;
        }
    }
}

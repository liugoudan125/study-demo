package com.beiming;

import com.aliyun.cloudauth20190307.Client;
import com.aliyun.cloudauth20190307.models.InitFaceVerifyRequest;
import com.aliyun.cloudauth20190307.models.InitFaceVerifyResponse;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.models.RuntimeOptions;

import java.util.Arrays;
import java.util.List;

public class InitFaceVerify {

    public static void main(String[] args) throws Exception {

        InitFaceVerifyRequest request = new InitFaceVerifyRequest();
        // 请输入场景ID+L。
        request.setSceneId(0L);
        // 设置商户请求的唯一标识。
        request.setOuterOrderNo("xxxx");
        // 认证方案。
        request.setProductCode("LR_FR");
        request.setUserId("12345xxxx");
        // 模式。
        request.setModel("LIVENESS");
        //request.setCertType("IDENTITY_CARD");
        //request.setCertName("张三");
        //request.setCertNo("xxxx");
        // MetaInfo环境参数。
        request.setMetaInfo("xxxxxx");
        //request.setMobile("130xxxxxxxx");
        //request.setIp("114.xxx.xxx.xxx");

        // 推荐，支持服务路由。
        InitFaceVerifyResponse response = initFaceVerifyAutoRoute(request);

        // 不支持服务自动路由。
        //InitFaceVerifyResponse response = initFaceVerify("cloudauth.cn-shanghai.aliyuncs.com", request);

        System.out.println(response.getBody().getRequestId());
        System.out.println(response.getBody().getCode());
        System.out.println(response.getBody().getMessage());
        System.out.println(response.getBody().getResultObject() == null ? null
                : response.getBody().getResultObject().getCertifyId());
    }

    private static InitFaceVerifyResponse initFaceVerifyAutoRoute(InitFaceVerifyRequest request) {
        // 第一个为主区域Endpoint，第二个为备区域Endpoint。
        List<String> endpoints = Arrays.asList("cloudauth.cn-shanghai.aliyuncs.com", "cloudauth.cn-beijing.aliyuncs.com");
        InitFaceVerifyResponse lastResponse = null;
        for (int i=0; i<endpoints.size(); i++) {
            try {
                InitFaceVerifyResponse response = initFaceVerify(endpoints.get(i), request);
                lastResponse = response;

                // 服务端错误，切换到下个区域调用。
                if(response != null){
                    if(500 == response.getStatusCode()){
                        continue;
                    }
                    if(response.getBody() != null){
                        if("500".equals(response.getBody().getCode())){
                            continue;
                        }
                    }
                }

                return response;
            } catch (Exception e) {
                e.printStackTrace();
                if(i == endpoints.size()-1){
                    throw new RuntimeException(e);
                }
            }
        }

        return lastResponse;
    }

    private static InitFaceVerifyResponse initFaceVerify(String endpoint, InitFaceVerifyRequest request)
            throws Exception {
        // 阿里云账号AccessKey拥有所有API的访问权限，建议您使用RAM用户进行API访问或日常运维。
        // 强烈建议不要把AccessKey ID和AccessKey Secret保存到工程代码里，否则可能导致AccessKey泄露，威胁您账号下所有资源的安全。
        // 本示例通过阿里云Credentials工具从环境变量中读取AccessKey，来实现API访问的身份验证。如何配置环境变量，请参见https://help.aliyun.com/document_detail/378657.html。
        com.aliyun.credentials.Client credentialClient = new com.aliyun.credentials.Client();
        Config config = new Config();
        config.setCredential(credentialClient);
        config.setEndpoint(endpoint);
        // 设置http代理。
        //config.setHttpProxy("http://xx.xx.xx.xx:xxxx");
        // 设置https代理。
        //config.setHttpsProxy("https://xx.xx.xx.xx:xxxx");
        Client client = new Client(config);

        // 创建RuntimeObject实例并设置运行参数。
        RuntimeOptions runtime = new RuntimeOptions();
        runtime.readTimeout = 10000;
        runtime.connectTimeout = 10000;

        return client.initFaceVerifyWithOptions(request, runtime);
    }
}
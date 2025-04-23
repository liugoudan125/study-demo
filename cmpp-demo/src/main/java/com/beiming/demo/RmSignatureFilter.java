//package com.beiming.demo;
//
//import com.caiyan.sms.Constant;
//import com.caiyan.sms.core.CoreContext;
//import com.caiyan.sms.core.mtsuffix.MtSuffixlFilter;
//import com.caiyan.sms.data.mysql.service.ChannelDataService;
//import com.caiyan.sms.entity.Channel;
//import com.caiyan.sms.message.MobileTerminalMessage;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.ObjectUtils;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//import java.util.regex.Pattern;
//
///**
// * 通道签名处理(去除/不去除)
// */
//@Service
//@Slf4j
//public class RmSignatureFilter implements MtSuffixlFilter {
//
//    @Override
//    public int getOrder() {
//        return 350;
//    }
//
//    /**
//     * 预编译,和每次运行编译会有40%左右的性能差距
//     */
//    private final static Pattern pattern = Pattern.compile("^【(.*?)】");
//
//    @Resource
//    private ChannelDataService channelDataService;
//
//    @Override
//    public void doFilter(MobileTerminalMessage message, CoreContext context) {
//        log.debug("通道签名处理：message:{}", message);
//        Channel channel = channelDataService.findByChannelCodeEquals(message.getChannelCode());
//        if (ObjectUtils.isNotEmpty(channel)) {
//            if (Constant.RM_SIGNATURE.equals(channel.getIsRmSignature())) {
//                //需要去除签名
//                String content = message.getContent();
//                String newContent = pattern.matcher(content).replaceFirst("");
//                message.setContent(newContent);
//            }
//        }
//    }
//
//}

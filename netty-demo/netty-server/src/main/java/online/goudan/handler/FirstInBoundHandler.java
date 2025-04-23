package online.goudan.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import online.goudan.utils.SessionUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * @author gouan
 * @date 2023/8/3 9:36
 * @desc FirstInBoundHandler
 */
@Slf4j
public class FirstInBoundHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        log.info("FirstInBoundHandler.channelRegistered: 注册 {}", ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("FirstInBoundHandler.channelActive: 激活 {}", ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String string = ((ByteBuf) msg).toString(StandardCharsets.UTF_8);
        JSONObject jsonObject = JSON.parseObject(string);
        if ("regist".equals(jsonObject.getString("type"))) {
            SessionUtil.putContext(jsonObject.getString("name"), ctx);
        } else {
            ctx.writeAndFlush(ctx.alloc().buffer().writeBytes(("收到" + SessionUtil.getName(ctx) + "的数据" + jsonObject.getString("data")).getBytes(StandardCharsets.UTF_8)));
        }
        log.info("FirstInBoundHandler.channelRead: 接收 {}", string);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        log.info("FirstInBoundHandler.channelReadComplete: 读取完成 ");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close().addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                log.info("FirstInBoundHandler.operationComplete: 断开 {}", channelFuture.isSuccess());
            }
        });
    }
}

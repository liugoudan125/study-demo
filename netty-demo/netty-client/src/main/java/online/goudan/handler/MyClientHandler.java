package online.goudan.handler;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import online.goudan.utils.SessionUtil;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;

/**
 * @author
 * @date 2023/8/3 10:24
 * @desc MyClientHandler
 */
public class MyClientHandler extends ChannelInboundHandlerAdapter {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MyClientHandler(String name) {
        this.name = name;
    }

    /**
     * 当客户端连接服务器完成就会触发该方法
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        HashMap<Object, Object> map = new HashMap<>() {
            {
                put("type", "regist");
                put("name", name);
            }
        };
        ctx.writeAndFlush(ctx.alloc().buffer().writeBytes(JSON.toJSONString(map).getBytes(StandardCharsets.UTF_8)));
        SessionUtil.putContext(name,ctx);
    }

    //当通道有读取事件时会触发，即服务端发送数据给客户端
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("收到服务端的消息:" + buf.toString(CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}

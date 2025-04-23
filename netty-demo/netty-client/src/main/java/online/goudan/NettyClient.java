package online.goudan;

import com.alibaba.fastjson.JSON;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import online.goudan.handler.MyClientHandler;
import online.goudan.utils.SessionUtil;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author goudan
 * @date 2023/8/3 9:24
 * @desc NettyClient
 */
public class NettyClient {
    public static void main(String[] args) {
        send("aa");
        send("bb");
        send("cc");
        send("dd");
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String flag = scanner.next();
            String data = scanner.next();

            ChannelHandlerContext context = SessionUtil.getContext(flag);
            Map<String,Object> map = new HashMap<>(){
                {
                    put("data",data);
                }
            };
            context.writeAndFlush(context.alloc().buffer().writeBytes(JSON.toJSONString(map).getBytes(StandardCharsets.UTF_8)));
        }
    }

    private static void send(String name) {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(bossGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new MyClientHandler(name));
                    }
                });
        bootstrap.connect("127.0.0.1", 8088).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                System.out.println(channelFuture.isSuccess());
            }
        });


    }

}

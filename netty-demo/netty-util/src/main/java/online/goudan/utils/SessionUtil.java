package online.goudan.utils;

import io.netty.channel.ChannelHandlerContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author
 * @date 2023/8/3 11:02
 * @desc SessionUtil
 */
public class SessionUtil {

    private static final Map<String, ChannelHandlerContext> POOL = new ConcurrentHashMap<>();
    private static final Map<ChannelHandlerContext, String> POOL1 = new ConcurrentHashMap<>();

    public static ChannelHandlerContext getContext(String name) {
        return POOL.get(name);
    }

    public static void putContext(String name, ChannelHandlerContext context) {
        POOL.put(name, context);
        POOL1.put(context, name);
    }

    public static String getName(ChannelHandlerContext context) {
        return POOL1.get(context);
    }

    public static void remove(ChannelHandlerContext context) {
        POOL.remove(POOL1.remove(context));
    }

    public static void remove(String name) {
        POOL1.remove(POOL.remove(name));
    }
}

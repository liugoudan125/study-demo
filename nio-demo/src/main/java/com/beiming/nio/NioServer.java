package com.beiming.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * NioServer
 */
public class NioServer {
    static Map<SocketChannel, String> map = new ConcurrentHashMap<>();
    static AtomicInteger index = new AtomicInteger(0);

    public static void main(String[] args) throws Exception {
        List<Selector> selectors = new ArrayList<>(10);

        ServerSocketChannel channel = ServerSocketChannel.open();
        channel.socket().bind(new InetSocketAddress("10.1.1.180", 10009));
        channel.configureBlocking(false);
        for (int i = 0; i < 10; i++) {
            Selector selector = Selector.open();
            selectors.add(selector);
            channel.register(selector, SelectionKey.OP_ACCEPT);
        }
        for (Selector selector : selectors) {
            new Thread(
                    () -> {
                        while (true) {
                            try {
                                int count = selector.select();
                                if (count > 0) {
                                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                                    while (iterator.hasNext()) {
                                        SelectionKey selectedKey = iterator.next();
                                        if (selectedKey.isAcceptable()) {
                                            SocketChannel socketChannel = channel.accept();
                                            if (null == socketChannel) {
                                                continue;
                                            }
                                            synchronized (socketChannel) {
                                                socketChannel.configureBlocking(false);
                                                socketChannel.register(selectors.get(index.getAndIncrement() % 10), SelectionKey.OP_READ);
                                                InetSocketAddress address = (InetSocketAddress) socketChannel.getRemoteAddress();
                                                System.out.println("%d - %d connected".formatted(address.getPort(), index.intValue()));
                                            }
                                        } else if (selectedKey.isReadable()) {
                                            SocketChannel socketChannel = null;
                                            try {
                                                socketChannel = (SocketChannel) selectedKey.channel();
                                                synchronized (socketChannel) {
                                                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                                                    int read = socketChannel.read(byteBuffer);
                                                    StringBuilder stringBuilder = new StringBuilder();
                                                    while (read > 0) {
                                                        stringBuilder.append(new String(byteBuffer.array(), 0, read));
                                                        read = socketChannel.read(byteBuffer);
                                                    }
                                                    String content = stringBuilder.toString();

//                                                    if (content.startsWith("name:")) {
//                                                        map.put(socketChannel, content.replace("name:", ""));
//                                                        continue;
//                                                    }
                                                    InetSocketAddress address = (InetSocketAddress) socketChannel.getRemoteAddress();
                                                    System.out.println("%s-%s: %s".formatted(Thread.currentThread().getName(), String.valueOf(address.getPort()), content));

                                                    //发送到其他客户端
                                                    for (Selector selector1 : selectors) {

                                                        Set<SelectionKey> keys = selector1.keys();
                                                        for (SelectionKey key : keys) {
                                                            SelectableChannel selectableChannel = key.channel();
                                                            if (selectableChannel instanceof SocketChannel sc && selectableChannel != socketChannel) {
                                                                InetSocketAddress socketAddress = (InetSocketAddress) sc.getRemoteAddress();
                                                                String string = "%s-%s: %s".formatted(Thread.currentThread().getName(), String.valueOf(socketAddress.getPort()), content);
                                                                ByteBuffer bb = ByteBuffer.wrap(string.getBytes());
                                                                sc.write(bb);
                                                            }
                                                        }
                                                    }
                                                }
                                            } catch (Exception e) {
                                                selectedKey.cancel();
                                                if (socketChannel != null) {
                                                    socketChannel.close();
                                                }
                                            }
                                        }
                                    }
                                    iterator.remove();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
            ).start();
        }
        while (true) {
            Thread.sleep(5000L);
        }

    }
}

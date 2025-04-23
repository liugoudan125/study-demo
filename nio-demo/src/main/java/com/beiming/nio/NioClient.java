package com.beiming.nio;

import org.apache.commons.lang3.RandomStringUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * NioClient
 */
public class NioClient {
    private Selector selector;
    SocketChannel socketChannel;

    public NioClient(Selector selector) throws Exception {
        initChannel();
        this.selector = selector;
        socketChannel.register(selector, SelectionKey.OP_READ);
    }

    private void initChannel() throws IOException {
        socketChannel = SocketChannel.open(new InetSocketAddress("10.1.1.180", 10009));
        socketChannel.configureBlocking(false);
    }


    public void sendInfo(String info) throws IOException {
        try {
            socketChannel.write(ByteBuffer.wrap(info.getBytes()));
        } catch (IOException e) {
            initChannel();
            sendInfo(info);
        }
    }

    static List<Selector> selectors = new ArrayList<>(10);

    public static void main(String[] args) throws Exception {
        int max = 100;
        for (int i = 0; i < 10; i++) {
            selectors.add(Selector.open());
        }
        AtomicInteger index = new AtomicInteger(0);

        CountDownLatch downLatch = new CountDownLatch(max);
        List<NioClient> nioClients = new ArrayList<NioClient>();
        for (int i = 0; i < max; i++) {
            Thread.ofVirtual().start(() -> {
                NioClient nioClient = null;
                try {
                    nioClient = new NioClient(selectors.get(index.getAndIncrement() % 10));
                    nioClients.add(nioClient);
                    downLatch.countDown();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            });
        }


        downLatch.await();
        for (Selector selector : selectors) {
            Thread.ofVirtual().start(() -> {
                while (true) {
                    try {
                        int count = selector.select();
                        if (count > 0) {
                            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                            while (iterator.hasNext()) {
                                SelectionKey key = iterator.next();
                                if (key.isReadable()) {
                                    SocketChannel channel = (SocketChannel) key.channel();
                                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                                    int read = channel.read(byteBuffer);
                                    StringBuilder stringBuilder = new StringBuilder();
                                    while (read > 0) {
                                        stringBuilder.append(new String(byteBuffer.array(), 0, read));
                                        read = channel.read(byteBuffer);
                                    }
                                    System.out.println("客户端接收到:%s".formatted(stringBuilder.toString()));
                                }
                            }
                            iterator.remove();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        System.out.println("连接完成");
        while (true) {
            Thread.sleep(3000L);
            String s = RandomStringUtils.randomAlphabetic(10, 30);
            nioClients.get(new Random().nextInt(nioClients.size()))
                    .sendInfo(s);
        }
//        Scanner scanner = new Scanner(System.in);
//        while (scanner.hasNextLine()) {
//            nioClients.get(new Random().nextInt(nioClients.size()))
//                    .sendInfo(scanner.nextLine());
//        }
    }
}

package com.example.jdk21;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lcl
 * @date 2024/2/5 10:41
 */
public class VTDemo {

    public static void main(String[] args) throws InterruptedException {
        List<Thread> list = new ArrayList<>(10000);
        long start = System.currentTimeMillis();
//        for (int i = 0; i < 10000; i++) {
//           Thread thread =  Thread.ofVirtual().name("VT" + i)
//                    .unstarted(() -> {
//                System.out.println(Thread.currentThread().getName());
//            });
//           list.add(thread);
//           thread.start();
//        }

//        for (int i = 0; i < 10000; i++) {
//            Thread thread = new Thread(() -> {
//                System.out.println(Thread.currentThread().getName());
//            }, "Thread" + i);
//            list.add(thread);
//            thread.start();
//        }

        System.out.println("cost:" + (System.currentTimeMillis() - start));
        System.out.println("memory: " + Runtime.getRuntime().totalMemory() / 8 / 1024 / 1024 + "M");
        System.out.println("max memory: " + Runtime.getRuntime().maxMemory() / 8 / 1024 / 1024 + "M");
        System.out.println("fredd memory: " + Runtime.getRuntime().freeMemory() / 8 / 1024 / 1024 + "M");
        System.out.println("cpu: " + Runtime.getRuntime().availableProcessors());

    }
}

package com.example.interrupt;

/**
 * @author lcl
 * @date 2023/9/25 10:15
 * @desc Demo
 */
public class Demo {

    public static void main(String[] args) throws InterruptedException {
        Thread one = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("接收到中断消息,中断标记1: " + Thread.currentThread().isInterrupted());
                    break;
                }


                try {
                    Thread.sleep(2000L);
                    System.out.println("执行中......");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println("接收到中断消息,中断标记2: " + Thread.currentThread().isInterrupted());
                    //再次中断
                    Thread.currentThread().interrupt();
                }
            }
        });
        one.start();
        Thread.sleep(1000L);
        one.interrupt();
    }

}

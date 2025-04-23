package com.example.shell;

import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.OutputStream;

/**
 * @author
 * @date 2023/6/9 15:18
 * @desc Main
 */
public class Main {

    public static void main(String[] args) {
        String host = "goudan.online";
        String user = "root";
        String password = "GouDan125";
        int port = 22; // SSH连接的端口号
        Session session = null;
        ChannelShell  channel = null;
        try {
            // 创建JSch对象
            JSch jsch = new JSch();
            // 创建会话session
            session = jsch.getSession(user, host, port);
            // 设置密码
            session.setPassword(password);
            // 不显示安全提示
            session.setConfig("StrictHostKeyChecking", "no");
            // 连接到远程计算机
            session.connect();
            channel = (ChannelShell) session.openChannel("shell");
            channel.setOutputStream(System.out);
            channel.setPtyType("vt220");


            channel.connect();
            OutputStream outputStream = channel.getOutputStream();
            outputStream.write("touch cc".getBytes());
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
            if (null != session) {
                channel.disconnect();
                session.disconnect();
            }
        }
    }



}

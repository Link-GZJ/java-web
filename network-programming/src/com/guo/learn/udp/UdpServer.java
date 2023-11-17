package com.guo.learn.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.charset.StandardCharsets;

public class UdpServer {
    public static void main(String[] args) throws Exception{
        //监听指定端口
        DatagramSocket ds = new DatagramSocket(9999);
        for(;;){
            //创建数据缓冲区
            byte[] buffer = new byte[1024];
            //使用DatagramPacket 接收数据包
            DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
            System.out.println("正在接收数据......");
            ds.receive(dp);
            Thread.sleep(5000);
            String s = new String(dp.getData(), dp.getOffset(), dp.getLength(), StandardCharsets.UTF_8);
            System.out.println("接收的数据为：" + s);
            byte[] bytes = "ACK".getBytes(StandardCharsets.UTF_8);
            dp.setData(bytes);
            ds.send(dp);
        }

    }
}

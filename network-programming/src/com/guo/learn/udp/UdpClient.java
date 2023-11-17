package com.guo.learn.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

public class UdpClient {
    public static void main(String[] args) throws Exception{
        DatagramSocket ds = new DatagramSocket();
        //如果接收数据包超过这个时间，就会报错
        ds.setSoTimeout(1000);
        ds.connect(InetAddress.getByName("localhost"),9999);
        byte[] bytes = "Hello1".getBytes(StandardCharsets.UTF_8);
        DatagramPacket dp = new DatagramPacket(bytes, bytes.length);
        //第二种写法
        //DatagramPacket dp = new DatagramPacket(bytes, bytes.length,InetAddress.getByName("localhost"),9999);
        ds.send(dp);
        //接收
        byte[] buffer = new byte[1024];
        dp = new DatagramPacket(buffer,buffer.length);
        ds.receive(dp);
        String resp = new String(dp.getData(), dp.getOffset(), dp.getLength(), StandardCharsets.UTF_8);
        System.out.println("返回信息：" + resp);
        ds.disconnect();
        ds.close();
    }
}

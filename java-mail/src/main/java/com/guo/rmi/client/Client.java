package com.guo.rmi.client;

import com.guo.rmi.server.ClockDemo;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalDateTime;

public class Client {
    public static void main(String[] args) throws RemoteException, NotBoundException {
        //连接到服务器localhost,端口号1099
        Registry registry = LocateRegistry.getRegistry(1099);
        //查找服务名为ClockService的服务并强制转型为ClockDemo
        ClockDemo clock = (ClockDemo) registry.lookup("ClockService");
        //正常调用接口方法
        LocalDateTime time = clock.getLocalDateTime("Asia/Shanghai");
        System.out.println(time);
    }
}

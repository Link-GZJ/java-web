package com.guo.rmi.server;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server {
    public static void main(String[] args) throws RemoteException, AlreadyBoundException {
        System.out.println("create World clock remote service...");
        //实例化Clock
        ClockDemo clock = new ClockService();
        //将此服务转化为远程服务端口
        ClockDemo remoteClock = (ClockDemo) UnicastRemoteObject.exportObject(clock,0);
        //将RMI服务注册到1099端口
        Registry registry = LocateRegistry.createRegistry(1099);
        //注册服务ClockService
        registry.bind("ClockService", remoteClock);
    }
}

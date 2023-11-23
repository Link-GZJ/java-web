package com.guo.rmi.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalDateTime;

public interface ClockDemo extends Remote {
    LocalDateTime getLocalDateTime(String zoneId) throws RemoteException;
}

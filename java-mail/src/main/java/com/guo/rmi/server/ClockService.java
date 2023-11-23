package com.guo.rmi.server;

import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class ClockService implements ClockDemo{
    @Override
    public LocalDateTime getLocalDateTime(String zoneId) throws RemoteException {
        return LocalDateTime.now(ZoneId.of(zoneId)).withNano(0);
    }
}

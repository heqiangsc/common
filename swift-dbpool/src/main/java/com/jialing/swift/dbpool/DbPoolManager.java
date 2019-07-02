package com.jialing.swift.dbpool;

public class DbPoolManager {
    private static class createPool {
        private static MyPoolImpl pool = new MyPoolImpl();
    }

    public static MyPoolImpl getInstace() {
        return createPool.pool;
    }
}

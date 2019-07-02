package com.jialing.swift.dbpool;

public interface IMyPool {

    PooledConnection getConnection();


    void createConnections(int count);


}

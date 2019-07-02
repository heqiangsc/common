package com.jialing.swift.dbpool;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Vector;

public class MyPoolImpl implements IMyPool {


    private static String driver = null;
    private static String url = null;

    private static String user = null;
    private static String password = null;

    private static int initCount = 3;
    private static int stepSize = 10;
    private static int poolMaxSize = 150;

    private Vector<PooledConnection> pooledConnections = new Vector<>();

    public MyPoolImpl() {
        init();
    }

    @Override
    public PooledConnection getConnection() {
        if (pooledConnections.size() <= 0) {
            System.out.println("获取数据库连接管道失败， 没有任务管道!");
            createConnections(initCount);
        }

        PooledConnection connection = getRealConnection();
        while (null == connection) {
            createConnections(stepSize);
            connection = getRealConnection();
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    private PooledConnection getRealConnection() {
        for (PooledConnection conn : pooledConnections) {
            if (!conn.isBusy()) {
                Connection connection = conn.getConnection();
                try {
                    if (!connection.isValid(2000)) {
                        connection = DriverManager.getConnection(url, user, password);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                conn.setBusy(true);
                return conn;
            }
        }
        return null;
    }


    @Override
    public void createConnections(int count) {
        if (pooledConnections.size() + count <= poolMaxSize) {
            for (int i = 0; i < count; i++) {
                try {
                    Connection connection = DriverManager.getConnection(url, user, password);
                    PooledConnection pooledConnection = new PooledConnection(connection, false);
                    pooledConnections.add(pooledConnection);
                    System.out.println("初始化" + (i + 1) + "个管道！");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("创建失败， 创建参数非法！");
        }

    }

    private void init() {
        InputStream inStream = MyPoolImpl.class.getClassLoader().getResourceAsStream("jdbcPool.properties");
        Properties pro = new Properties();
        try {
            // 数据加载是否有信息进来
            pro.load(inStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        driver = pro.getProperty("jdbcDriver");
        url = pro.getProperty("jdbcurl");
        user = pro.getProperty("userName");
        password = pro.getProperty("password");
        // 对字节信息进行判断
        if (Integer.valueOf(pro.getProperty("initCount")) > 0) {
            //initCount = Integer.valueOf(pro.getProperty("initCount"));
        } else if (Integer.valueOf(pro.getProperty("stepSize")) > 0) {
            //stepSize = Integer.valueOf(pro.getProperty("stepSize"));
        } else if (Integer.valueOf(pro.getProperty("poolMaxSize")) > 0) {
            poolMaxSize = Integer.valueOf(pro.getProperty("poolMaxSize"));
        }
        // 准备创建什么类型管道 driver
        try {
            Driver dbDriver = (Driver) Class.forName(driver).newInstance();
            DriverManager.registerDriver(dbDriver);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

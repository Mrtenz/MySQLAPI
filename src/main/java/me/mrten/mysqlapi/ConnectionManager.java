package me.mrten.mysqlapi;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionManager {

    private HikariDataSource dataSource;
    private String host;
    private String port;
    private String username;
    private String password;
    private String database;
    private int connectionTimeout;
    private int maximumPoolsize;

    public ConnectionManager(String host, String port, String username, String password, String database, int connectionTimeout, int maximumPoolsize) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.database = database;
        this.connectionTimeout = connectionTimeout;
        this.maximumPoolsize = maximumPoolsize;
    }

    public ConnectionManager(String host, String port, String username, String password, String database) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.database = database;
        this.connectionTimeout = 5000;
        this.maximumPoolsize = 10;
    }

    public Connection getConnection() {
        if (isClosed())
            throw new IllegalStateException("Connection is not open.");

        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean open() {
        try {
            HikariConfig config = new HikariConfig();
            config.setDriverClassName("com.mysql.jdbc.Driver");
            config.setUsername(username);
            config.setPassword(password);
            config.setJdbcUrl(String.format("jdbc:mysql://%s:%s/%s", host, port, database));
            config.setConnectionTimeout(connectionTimeout);
            config.setMaximumPoolSize(maximumPoolsize);
            this.dataSource = new HikariDataSource(config);
            return true;
        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }
    }

    public void close() {
        if (isClosed())
            throw new IllegalStateException("Connection is not open.");

        this.dataSource.close();
    }

    public boolean isClosed() {
        return dataSource == null || dataSource.isClosed();
    }

}

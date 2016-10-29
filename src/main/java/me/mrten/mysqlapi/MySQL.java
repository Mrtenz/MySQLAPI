package me.mrten.mysqlapi;

public class MySQL {

    private ConnectionManager connectionManager;

    public MySQL() {

    }

    public ConnectionManager getConnectionManager() {
        return connectionManager;
    }

    public void connect(String host, String port, String username, String password, String database) {
        connectionManager = new ConnectionManager(host, port, username, password, database);
    }

    public void disconnect() {
        if (connectionManager != null)
            connectionManager.close();
    }
}

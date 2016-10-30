package me.mrten.mysqlapi.queries;

import com.sun.rowset.CachedRowSetImpl;
import me.mrten.mysqlapi.MySQL;

import java.sql.*;

public class Query {

    private Connection connection;
    private PreparedStatement statement;

    public Query(MySQL mysql, String sql) {
        connection = mysql.getConnectionManager().getConnection();
        try {
            statement = connection.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set a parameter of the prepared statement.
     * <p>
     * Parameters are defined using ? in the SQL query.
     *
     * @param index the index of the parameter to set (starts with 1)
     * @param value the value to set the parameter to
     */
    public void setParameter(int index, Object value) {
        try {
            statement.setObject(index, value);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Execute a SQL query that does not return a ResultSet.
     * @return number of rows changed
     */
    public int executeUpdate() throws SQLException {
        int rowsChanged = 0;
        try {
            return statement.executeUpdate();
        } finally {
            if (statement != null) {
                statement.close();
            }

            if (connection != null) {
                connection.close();
            }
        }
    }

    /**
     * Execute a SQL query that does return a ResultSet.
     * <p>
     * Uses a CachedRowSetImpl that is not connected to the database.
     * @return the ResultSet
     */
    public ResultSet executeQuery() throws SQLException {
        CachedRowSetImpl rowSet = new CachedRowSetImpl();
        ResultSet resultSet = null;
        try {
            resultSet = statement.executeQuery();
            rowSet.populate(resultSet);
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (connection != null) {
                connection.close();
            }
        }
        return rowSet;
    }

    /**
     * Execute a SQL query that does not return a ResultSet asynchronously.
     * <p>
     * The query will be run in a seperate thread.
     * @param callback the callback to be executed once the query is done
     */
    public void executeUpdateAsync(final Callback<Integer, SQLException> callback) {
        Thread thread = new Thread(new Runnable() {

            public void run() {
                try {
                    int rowsChanged = executeUpdate();
                    if (callback != null) {
                        callback.call(rowsChanged, null);
                    }
                } catch (SQLException e) {
                    if (callback != null) {
                        callback.call(0, e);
                    } else {
                        e.printStackTrace();
                    }
                }
            }

        });

        thread.run();
    }

    /**
     * Execute a SQL query that does not return a ResultSet asynchronously.
     * <p>
     * The query will be run in a seperate thread.
     */
    public void executeUpdateAsync() {
        executeUpdateAsync(null);
    }

    /**
     * Execute a SQL query that does return a ResultSet asynchronously.
     * <p>
     * The query will be run in a seperate thread.
     * @param callback the callback to be executed once the query is done
     */
    public void executeQueryAsync(final Callback<ResultSet, SQLException> callback) {
        Thread thread = new Thread(new Runnable() {

            public void run() {
                try {
                    ResultSet rs = executeQuery();
                    callback.call(rs, null);
                } catch (SQLException e) {
                    callback.call(null, e);
                }
            }

        });

        thread.run();
    }

}

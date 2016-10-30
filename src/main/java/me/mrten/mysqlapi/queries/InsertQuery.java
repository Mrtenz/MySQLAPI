package me.mrten.mysqlapi.queries;

import me.mrten.mysqlapi.utils.QueryUtils;

import java.util.LinkedHashMap;

public class InsertQuery {

    private String table;
    private LinkedHashMap<String, String> values = new LinkedHashMap<String, String>();

    /**
     * Create an insert query.
     *
     * @param table - the table to be updated
     */
    public InsertQuery(String table) {
        this.table = table;
    }

    /**
     * Set a column to insert to and the value to be inserted.
     * @param column - the column to insert to
     * @param value - the value to be inserted
     * @return the InsertQuery object
     */
    public InsertQuery value(String column, String value) {
        values.put(column, value);
        return this;
    }

    /**
     * Set a column to insert to. Automatically sets the value to ? to be used with prepared statements.
     * @param column - the column to insert to
     * @return the InsertQuery object
     */
    public InsertQuery value(String column) {
        value(column, "?");
        return this;
    }

    /**
     * Build the query as a String.
     * @return the query as a String
     */
    public String build() {
        StringBuilder builder = new StringBuilder();
        builder.append("INSERT INTO ")
                .append(table)
                .append(" (")
                .append(QueryUtils.separate(values.keySet(), ","))
                .append(")")
                .append(" VALUES (")
                .append(QueryUtils.separate(values.values(), ","))
                .append(")");

        return builder.toString();
    }

}

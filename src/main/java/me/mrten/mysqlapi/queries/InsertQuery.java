package me.mrten.mysqlapi.queries;

import me.mrten.mysqlapi.utils.QueryUtils;

import java.util.LinkedHashMap;

public class InsertQuery {

    private String table;
    private LinkedHashMap<String, String> values = new LinkedHashMap<String, String>();

    public InsertQuery(String table) {
        this.table = table;
    }

    public InsertQuery value(String column, String value) {
        values.put(column, value);
        return this;
    }

    public InsertQuery value(String column) {
        value(column, "?");
        return this;
    }

    public String build() {
        StringBuilder builder = new StringBuilder();
        builder.append("INSERT INTO ")
                .append(table)
                .append(" (")
                .append(QueryUtils.seperate(values.keySet(), ","))
                .append(")")
                .append(" VALUES (")
                .append(QueryUtils.seperate(values.values(), ","))
                .append(")");

        return builder.toString();
    }

}

package me.mrten.mysqlapi.queries;

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
                .append(" (");

        String seperator = "";
        for (String key : values.keySet()) {
            builder.append(seperator)
                    .append(key);
            seperator = ",";
        }

        builder.append(")")
                .append(" VALUES (");
        seperator = "";
        for (String value : values.values()) {
            builder.append(seperator)
                    .append(value);
            seperator = ",";
        }

        builder.append(")");

        return builder.toString();
    }

}

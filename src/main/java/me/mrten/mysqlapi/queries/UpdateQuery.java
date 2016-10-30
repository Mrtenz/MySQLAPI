package me.mrten.mysqlapi.queries;

import me.mrten.mysqlapi.utils.QueryUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class UpdateQuery {

    private String table;
    private LinkedHashMap<String, String> values = new LinkedHashMap<String, String>();
    private List<String> wheres = new ArrayList<String>();

    public UpdateQuery(String table) {
        this.table = table;
    }

    public UpdateQuery set(String column, String value) {
        values.put(column, value);
        return this;
    }

    public UpdateQuery set(String column) {
        set(column, "?");
        return this;
    }

    public UpdateQuery where(String expression) {
        wheres.add(expression);
        return this;
    }

    public UpdateQuery and(String expression) {
        where(expression);
        return this;
    }

    public String build() {
        StringBuilder builder = new StringBuilder();
        builder.append("UPDATE ")
                .append(table)
                .append(" SET ");

        String seperator = "";
        for (Map.Entry<String, String> entry : values.entrySet()) {
            String column = entry.getKey();
            String value = entry.getValue();
            builder.append(seperator)
                    .append(column)
                    .append("=")
                    .append(value);
            seperator = ",";
        }

        if (wheres.size() > 0) {
            builder.append(" WHERE ")
                    .append(QueryUtils.seperate(wheres, " AND "));
        }

        return builder.toString();
    }

}

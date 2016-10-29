package me.mrten.mysqlapi.queries;

import java.util.ArrayList;
import java.util.List;

public class CreateTableQuery {

    private String table;
    private boolean ifNotExists = false;
    private List<String> columns = new ArrayList<String>();
    private String primaryKey;

    public CreateTableQuery(String table) {
        this.table = table;
    }

    public CreateTableQuery ifNotExists() {
        this.ifNotExists = true;
        return this;
    }

    public CreateTableQuery column(String column, String settings) {
        columns.add(column + " " + settings);
        return this;
    }

    public CreateTableQuery primaryKey(String column) {
        this.primaryKey = column;
        return this;
    }

    public String build() {
        StringBuilder builder = new StringBuilder();
        builder.append("CREATE TABLE ");

        if (ifNotExists) {
            builder.append("IF NOT EXISTS ");
        }

        builder.append(table);
        builder.append(" (");

        for (String column : columns) {
            builder.append(column + ",");
        }

        if (columns.size() > 0) {
            builder.deleteCharAt(builder.length() - 1);
        }

        if (primaryKey != null) {
            builder.append(",PRIMARY KEY(");
            builder.append(primaryKey);
            builder.append(")");
        }

        builder.append(")");

        return builder.toString();
    }

}

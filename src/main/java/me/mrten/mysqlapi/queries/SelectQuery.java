package me.mrten.mysqlapi.queries;

import me.mrten.mysqlapi.utils.QueryUtils;

import java.util.ArrayList;
import java.util.List;

public class SelectQuery {

    private String table;
    private List<String> columns = new ArrayList<String>();
    private List<String> wheres = new ArrayList<String>();
    private String orderBy;
    private boolean orderByAscending = false;
    private int limitOffset = 0;
    private int limitRowCount = 0;

    public SelectQuery(String table) {
        this.table = table;
    }

    public SelectQuery column(String column) {
        columns.add(column);
        return this;
    }

    public SelectQuery where(String expression) {
        wheres.add(expression);
        return this;
    }

    public SelectQuery and(String expression) {
        where(expression);
        return this;
    }

    public SelectQuery orderBy(String column, boolean ascending) {
        this.orderBy = column;
        this.orderByAscending = ascending;
        return this;
    }

    public SelectQuery limit(int offset, int rowCount) {
        this.limitOffset = offset;
        this.limitRowCount = rowCount;
        return this;
    }

    public SelectQuery limit(int rowCount) {
        this.limitOffset = 0;
        this.limitRowCount = rowCount;
        return this;
    }

    public String build() {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT ")
                .append(QueryUtils.seperate(columns, ","))
                .append(" FROM ")
                .append(table);

        if (wheres.size() > 0) {
            builder.append(" WHERE ")
                    .append(QueryUtils.seperate(wheres, " AND "));
        }

        if (orderBy != null) {
            builder.append(" ORDER BY ")
                    .append(orderBy)
                    .append(orderByAscending ? " ASC" : " DESC");
        }

        if (limitRowCount > 0) {
            builder.append(" LIMIT ")
                    .append(limitOffset)
                    .append(",").append(limitRowCount);
        }

        return builder.toString();
    }

}

package me.mrten.mysqlapi.utils;

import java.util.Collection;

public class QueryUtils {

    public static String seperate(Collection<String> collection, String seperator) {
        StringBuilder builder = new StringBuilder();
        String sep = "";
        for (String item : collection) {
            builder.append(sep)
                    .append(item);
            sep = seperator;
        }
        return builder.toString();
    }

}

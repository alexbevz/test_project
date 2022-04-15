package org.eagleinvsys.test.converters.utils;

public class MyUtils {
    public static String escapeSpecialCharacters(String data, String sep) {
        String escapedData = data.replaceAll("\\R", "");

        if (data.contains(sep) || data.contains("\"") || data.contains("'")) {
            data = escapedData.replaceAll("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return escapedData;
    }
}

package com.github.cinema.home.client.server.common.utils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileSizeConverter {
    private static final Pattern postFixPattern;
    private static final Map<String, BigDecimal> unitMap;

    static {
        postFixPattern = Pattern.compile("([\\d.]+)\\s*([TGMK]i?B)", Pattern.CASE_INSENSITIVE);
        unitMap = new HashMap<>();
        unitMap.put("TB", BigDecimal.valueOf(1000).pow(4));
        unitMap.put("GB", BigDecimal.valueOf(1000).pow(3));
        unitMap.put("MB", BigDecimal.valueOf(1000).pow(2));
        unitMap.put("KB", BigDecimal.valueOf(1000).pow(1));
        unitMap.put("TIB", BigDecimal.valueOf(1024).pow(4));
        unitMap.put("GIB", BigDecimal.valueOf(1024).pow(3));
        unitMap.put("MIB", BigDecimal.valueOf(1024).pow(2));
        unitMap.put("KIB", BigDecimal.valueOf(1024).pow(1));
    }

    public static long toBytesSize(String fileSize) {
        long returnValue;
        Matcher matcher = postFixPattern.matcher(fileSize);
        if (matcher.find()) {
            BigDecimal multiply = unitMap.get(matcher.group(2).toUpperCase());
            BigDecimal bytes = new BigDecimal(matcher.group(1));
            bytes = bytes.multiply(multiply);
            returnValue = bytes.longValue();
        } else {
            returnValue = Math.round(Long.parseLong(fileSize) / 8.0);
        }
        return returnValue;
    }
}

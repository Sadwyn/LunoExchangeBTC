package com.andersenlab.lunoexchangebtc.util;

import java.util.Arrays;

public class EnumUtils {
    private EnumUtils(){}
    public static String[] getNames(Class<? extends Enum<?>> e) {
        return Arrays.toString(e.getEnumConstants()).replaceAll("^.|.$", "").split(", ");
    }
}

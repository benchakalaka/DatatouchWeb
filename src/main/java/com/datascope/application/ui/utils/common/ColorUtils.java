package com.datascope.application.ui.utils.common;

public class ColorUtils {
    public static int getR(String hexCol) {
        return Integer.valueOf(hexCol.substring(1, 3), 16);
    }


    public static int getG(String hexCol) {
        return Integer.valueOf(hexCol.substring(3, 5), 16);
    }

    public static int getB(String hexCol) {
        return Integer.valueOf(hexCol.substring(5, 7), 16);
    }

}


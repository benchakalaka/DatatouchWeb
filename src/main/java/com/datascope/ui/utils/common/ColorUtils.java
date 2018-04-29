package com.datascope.ui.utils.common;


public class ColorUtils {
    public static int getR(String hexCol) {
        //return Integer.valueOf(hexCol.substring(1, 3), 16);
        return getR(parseColor(hexCol));
    }

    public static int getR(int color) {
        color >>= 16;
        color &= 0x000000FF;
        return color;
    }

    public static int getG(String hexCol) {
        //return Integer.valueOf(hexCol.substring(3, 5), 16);
        return getG(parseColor(hexCol));
    }

    public static int getG(int color) {
        color >>= 8;
        color &= 0x000000FF;
        return color;
    }

    public static int getB(String hexCol) {
        //return Integer.valueOf(hexCol.substring(5, 7), 16);
        return getB(parseColor(hexCol));
    }

    public static int getB(int color) {
        color &= 0x000000FF;
        return color;
    }

    public static int getA(int color) {
        color >>= 24;
        color &= 0x000000FF;
        return color;
    }

    public static int getColorFromARGB(int a, int r, int g, int b) {
        int color = 0;
        color |= (a << 24) & 0xFF000000;
        color |= (r << 16) & 0x00FF0000;
        color |= (g << 8) & 0x0000FF00;
        color |= b  & 0x000000FF;
        return color;
    }

    public static String getHexColorFromARGB(int a, int r, int g, int b) {
        int color = ColorUtils.getColorFromARGB(a, r, g, b);
        return ColorUtils.toHexColor(color);
    }

    public static int parseColor(String hexCol) {
        if (hexCol == null) {
            return 0;
        }

        int length = hexCol.length();
        if (length <= 1 || length > 9) {
            return 0;
        }

        String s = hexCol.substring(1);

        int color = (int)Long.parseLong(s, 16);
        if (length < 9) {
            color |= 0xFF000000;
        }

        return color;
    }

    public static String toHexColor(int color) {
        String hexColor = String.format("#%02x%02x%02x%02x", getA(color), getR(color), getG(color), getB(color));
        return hexColor;
    }
}


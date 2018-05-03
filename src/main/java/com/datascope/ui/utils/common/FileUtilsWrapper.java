package com.datascope.ui.utils.common;


import org.apache.commons.io.FileUtils;

import java.io.*;

public class FileUtilsWrapper {

    public static byte[] extractBytes(File file) {
        byte[] bytes = new byte[0];
        if (file == null) {
            return bytes;
        }

        try {
            bytes = FileUtils.readFileToByteArray(file);
        } catch (IOException e) {
        }

       return bytes;
    }

    public static void removeFile(File file) {
        if (file != null) {
            FileUtils.deleteQuietly(file);
        }
    }

    public static boolean isPdfFile(String filePath) {
        if (filePath != null) {
            return filePath.toLowerCase().endsWith(".pdf");
        }
        return false;
    }
}

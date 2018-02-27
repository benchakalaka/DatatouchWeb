package com.datascope.application.ui.utils.html;

public class HtmlFormatter {
    private static String HTML_OPEN_IN_NEW_WINDOWS ="<a href='%s' target='_blank'>View</a>";

    public static String Link(String link){
        return String.format(HTML_OPEN_IN_NEW_WINDOWS, link);
    }
}

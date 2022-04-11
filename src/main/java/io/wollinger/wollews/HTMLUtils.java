package io.wollinger.wollews;

import io.wollinger.wollews.requests.RequestInfo;

import java.io.File;

public class HTMLUtils {
    public static String getFolderListHTML(File file, RequestInfo requestInfo) {
        StringBuilder response = new StringBuilder("<h3>Directory listing:</h3>");
        response.append("<ul>");
        for(File f : file.listFiles()) {
            String link = requestInfo.getRequestedFile().getPath() + File.separator + f.getName();
            response.append("<li>");
            response.append("<a href='").append(link).append("'>");
            response.append(f.getName());
            response.append("</a>");
            response.append("</li>");
        }
        response.append("</ul>");
        return response.toString();
    }
}

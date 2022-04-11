package io.wollinger.wollews;

import io.wollinger.wollews.requests.info.RequestInfo;

import java.io.File;

public class HTMLUtils {
    public static String getFolderListHTML(File file, RequestInfo requestInfo) {
        StringBuilder response = new StringBuilder("<h3>Directory listing:</h3>");
        response.append("<ul>");
        for(File f : file.listFiles()) {
            //We do this to prevent baseFolder adding a double slash needlessly
            String basePath = requestInfo.getRequestedFile().getPath();
            if(basePath.equals("\\")) {
                basePath = "";
            }
            String link = basePath + File.separator + f.getName();
            response.append("<li>");
            response.append("<a href='.").append(link).append("'>");
            response.append(f.getName());
            response.append("</a>");
            response.append("</li>");
        }
        response.append("</ul>");
        return response.toString();
    }
}

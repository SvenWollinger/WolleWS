package io.wollinger.wollews.requests.info;

import io.wollinger.wollews.WolleWS;

import java.io.File;
import java.util.ArrayList;

public class RequestInfo {
    private MethodType method;
    private File requestedFile;
    private String requestType;

    private String host;
    private String userAgent;

    private final ArrayList<AcceptParameter> accepts = new ArrayList<>();
    private final ArrayList<AcceptParameter> acceptsLanguage = new ArrayList<>();
    private final ArrayList<AcceptParameter> acceptsEncoding = new ArrayList<>();

    private ConnectionType connectionType;
    private String referer;
    private int upgradeInsecureRequests;

    public boolean setMethod(String line) {
        //TODO: This is sometimes null, is that alright to just close?
        if(line == null || line.isEmpty())
            return false;

        String[] mainRequest = line.split(" ");
        method = MethodType.valueOf(mainRequest[0]);
        requestedFile = new File(mainRequest[1]);
        requestType = mainRequest[2];
        return true;
    }

    public void set(String line) {
        String[] parts = line.split(" ", 2);
        switch (parts[0]) {
            case "Host:" -> host = parts[1].replaceFirst(":" + WolleWS.PORT, "");
            case "User-Agent:" -> userAgent = parts[1];
            case "Accept:" -> addAcceptParameter(accepts, parts[1]);
            case "Accept-Language:" -> addAcceptParameter(acceptsLanguage, parts[1]);
            case "Accept-Encoding:" -> addAcceptParameter(acceptsEncoding, parts[1]);
            case "Connection:" -> connectionType = ConnectionType.fromString(parts[1]);
            case "Referer:" -> referer = parts[1];
            case "Upgrade-Insecure-Requests:" -> upgradeInsecureRequests = Integer.parseInt(parts[1]);
            default -> System.out.println("RequestInfo.set -> Not handling this yet: " + line);
        }
    }

    public void finish() {
        //Set defaults
        if(connectionType == null)
            connectionType = ConnectionType.DEFAULT_CONNECTION_TYPE;
    }

    private void addAcceptParameter(ArrayList<AcceptParameter> list, String line) {
        for(String part : line.split(",")) {
            if(part.contains(";")) {
                String[] parts = part.split(";q=");
                list.add(new AcceptParameter(parts[0], Float.parseFloat(parts[1])));
            } else {
                //"Accept-Encoding" for some reason spaces them out, like this: Accept-Encoding: gzip, deflate, br
                if(part.startsWith(" "))
                    part = part.replaceFirst(" ", "");
                list.add(new AcceptParameter(part));
            }
        }
    }

    public String toHTMLString() {
        String html = "<h1>Request Info</h1>";
        html += getHTMLParameter("Method", method.toString());
        html += getHTMLParameter("Requested file", requestedFile.getPath());
        html += getHTMLParameter("Request type", requestType);
        html += getHTMLParameter("Host", host);
        html += getHTMLParameter("User-Agent", userAgent);

        html += paramList("Accepts", accepts);
        html += paramList("Accepts-Language", acceptsLanguage);
        html += paramList("Accepts-Encoding", acceptsEncoding);

        html += getHTMLParameter("Connection", connectionType);

        return html;
    }

    private String getHTMLParameter(String key, Object value) {
        return "<p><b>" + key + ":</b> " + value + "</p>";
    }

    private String paramList(String title, ArrayList<AcceptParameter> list) {
        StringBuilder html = new StringBuilder("<b>" + title + ":</b>");
        html.append("<ul>");
        for(AcceptParameter param : list) {
            html.append("<li>Type: ").append(param.getType()).append(", Quality: ").append(param.getQuality()).append("</li>");
        }
        html.append("</ul>");
        return html.toString();
    }

    public File getRequestedFile() {
        return requestedFile;
    }

    public String getHost() {
        return host;
    }

}

package io.wollinger.wollews.requests;

import java.io.File;
import java.util.ArrayList;

public class RequestInfo {
    private Method method;
    private File requestedFile;
    private String requestType;

    private String host;
    private String userAgent;

    private ArrayList<AcceptParameter> accepts = new ArrayList<>();
    private ArrayList<AcceptParameter> acceptsLanguage = new ArrayList<>();
    private ArrayList<AcceptParameter> acceptsEncoding = new ArrayList<>();


    public boolean setMethod(String line) {
        //TODO: This is sometimes null, is that alright to just close?
        if(line == null || line.isEmpty())
            return false;

        String[] mainRequest = line.split(" ");
        method = Method.valueOf(mainRequest[0]);
        requestedFile = new File(mainRequest[1]);
        requestType = mainRequest[2];
        return true;
    }

    public void set(String line) {
        String[] parts = line.split(" ", 2);
        switch (parts[0]) {
            case "Host:" -> host = parts[1];
            case "User-Agent:" -> userAgent = parts[1];
            case "Accept:" -> addAcceptParameter(accepts, parts[1]);
            case "Accept-Language:" -> addAcceptParameter(acceptsLanguage, parts[1]);
            case "Accept-Encoding:" -> addAcceptParameter(acceptsEncoding, parts[1]);
            default -> System.out.println("RequestInfo.set -> Not handling this yet: " + line);
        }
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

}

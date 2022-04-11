package io.wollinger.wollews.response;

import io.wollinger.wollews.ContentType;
import io.wollinger.wollews.WolleWS;

import java.io.PrintWriter;

public class Response {
    private PrintWriter out;
    private ResponseCode code;

    public Response (PrintWriter out, ResponseCode code) {
        this.out = out;
        this.code = code;
    }

    public void sendResponse(String response) {
        out.write(getResponseCode());
        out.write(getServerInfo());
        out.write(getContentType(ContentType.TEXT_HTML));
        out.write("\r\n");
        out.write(response);
    }

    public String getResponseCode() {
        return "HTTP/1.0 " + code.getCode() + "\r\n";
    }

    public String getServerInfo() {
        return "Server: " + WolleWS.SERVER_NAME + " " + WolleWS.SERVER_VERSION + "\r\n";
    }

    public String getContentType(ContentType type) {
        return "Content-Type: " + type.getType() + "\r\n";
    }
}

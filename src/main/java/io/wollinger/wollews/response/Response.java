package io.wollinger.wollews.response;

import io.wollinger.wollews.WolleWS;

import java.io.*;
import java.nio.file.Files;

public class Response {
    private final PrintWriter out;
    private final BufferedOutputStream dataOut;
    private final ResponseCode code;

    public Response(PrintWriter out, BufferedOutputStream dataOut, ResponseCode code) {
        this.out = out;
        this.dataOut = dataOut;
        this.code = code;
    }

    public void sendResponse(String response) {
        out.write(getResponseCode());
        out.write(getServerInfo());
        out.write(getContentType("text/html"));
        out.write("\r\n");
        out.write(response);
        out.flush();
    }

    public void sendResponse(File file) {
        out.write(getResponseCode());
        out.write(getServerInfo());
        out.write(getContentType(file));
        out.write(getFileLength(file));
        out.write("\r\n");
        out.flush();

        try {
            dataOut.write(readFileData(file), 0, (int) file.length());
            dataOut.flush();
        } catch (IOException e) {
            //Sending file failed for some reason, or just stopped. Ignore.
        }

    }

    public String getFileLength(File file) {
        return "Content-Length: " + file.length() + "\r\n";
    }

    public String getResponseCode() {
        return "HTTP/1.0 " + code.getCode() + "\r\n";
    }

    public String getServerInfo() {
        return "Server: " + WolleWS.SERVER_NAME + " " + WolleWS.SERVER_VERSION + "\r\n";
    }

    public String getContentType(String type) {
        return "Content-Type: " + type + "\r\n";
    }

    public String getContentType(File file) {
        String type = null;
        try {
            type = Files.probeContentType(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(type == null)
            type = "text/plain";
        return getContentType(type);
    }

    private byte[] readFileData(File file) {
        FileInputStream fileIn = null;
        byte[] fileData = new byte[(int)file.length()];

        try {
            fileIn = new FileInputStream(file);
            fileIn.read(fileData);
        } catch(IOException io){
            io.printStackTrace();
        } finally {
            try {
                if (fileIn != null)
                    fileIn.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return fileData;
    }
}

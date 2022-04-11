package io.wollinger.wollews.response;

import io.wollinger.wollews.ContentType;
import io.wollinger.wollews.WolleWS;

import java.io.*;

public class Response {
    private PrintWriter out;
    private BufferedOutputStream dataOut;
    private ResponseCode code;

    public Response(PrintWriter out, BufferedOutputStream dataOut, ResponseCode code) {
        this.out = out;
        this.dataOut = dataOut;
        this.code = code;
    }

    public void sendResponse(String response) {
        out.write(getResponseCode());
        out.write(getServerInfo());
        out.write(getContentType(ContentType.TEXT_HTML));
        out.write("\r\n");
        out.write(response);
        out.flush();
    }

    public void sendResponse(File file) {
        out.write(getResponseCode());
        out.write(getServerInfo());
        out.write(getContentType(ContentType.getContentTypeFromFile(file)));
        out.write(getFileLength(file));
        out.write("\r\n");
        out.flush();

        try {
            dataOut.write(readFileData(file), 0, (int) file.length());
            dataOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
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

    public String getContentType(ContentType type) {
        return "Content-Type: " + type.getType() + "\r\n";
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

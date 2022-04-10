package io.wollinger.wollews.requests;

import java.io.*;
import java.net.Socket;

public class Request implements Runnable {
    private Socket socket;

    private BufferedReader in;
    private PrintWriter out;
    private BufferedOutputStream dataOut;

    private RequestInfo requestInfo = new RequestInfo();

    public Request(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        if(!prepareStreams())
            return;
        //Preparing streams was successful. Read.

        if(!read()) {
            //Reading failed. Close streams and exit
            close();
            return;
        }

        if(!write()) {
            //Writing failed. Do something? (And close)
            //TODO: What do?
            close();
            return;
        }

        close();
    }

    private boolean prepareStreams() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
            dataOut = new BufferedOutputStream(socket.getOutputStream());
        } catch(IOException ioException) {
            return false;
        }
        return true;
    }

    private boolean read() {
        try {
            if(!requestInfo.setMethod(in.readLine())) {
                return false;
            }

            for (String line = in.readLine(); line != null && !line.isEmpty(); line = in.readLine()) {
                requestInfo.set(line);
            }
        } catch (IOException ioException) {
            return false;
        }
        return true;
    }

    private boolean write() {
        out.write("HTTP/1.0 200 OK\r\n");
        out.write("Server: WolleWS\r\n");
        out.write("Content-Type: text/html\r\n");
        out.write("\r\n");
        out.write(requestInfo.toHTMLString());

        return true;
    }

    private boolean close() {
        try {
            out.close();
            socket.close();
        } catch(IOException exception) {
            return false;
        }
        return true;
    }
}

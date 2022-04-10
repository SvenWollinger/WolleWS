package io.wollinger.wollews;

import java.io.*;
import java.net.Socket;

public class RequestHandler implements Runnable {
    private Socket socket;

    private BufferedReader in;
    private PrintWriter out;
    private BufferedOutputStream dataOut;


    private Method method;
    private File requestedFile;
    private String requestType;

    public RequestHandler(Socket socket) {
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
            String mainRequestFull = in.readLine();
            //TODO: This is sometimes null, is that alright to just close?
            if(mainRequestFull == null || mainRequestFull.isEmpty())
                return false;

            String[] mainRequest = mainRequestFull.split(" ");
            method = Method.valueOf(mainRequest[0]);
            requestedFile = new File(mainRequest[1]);
            requestType = mainRequest[2];
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
        out.write("Hello world");

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

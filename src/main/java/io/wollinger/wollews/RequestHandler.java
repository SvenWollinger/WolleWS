package io.wollinger.wollews;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.Socket;

public class RequestHandler implements Runnable {
    private Socket socket;
    private Method method;

    public RequestHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader in = null;
        PrintWriter out = null;
        BufferedOutputStream dataOut = null;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
            dataOut = new BufferedOutputStream(socket.getOutputStream());

            System.out.println("Method: " + in.readLine());
            for (String line = in.readLine(); line != null && !line.isEmpty(); line = in.readLine()) {
                System.out.println(line);

            }


            out.write("HTTP/1.0 200 OK\r\n");
            out.write("Server: WolleWS\r\n");
            out.write("Content-Type: text/html\r\n");
            out.write("\r\n");
            out.write("Hello world");

            out.close();
            in.close();
            socket.close();
        } catch(Exception e) {

        }
    }
}

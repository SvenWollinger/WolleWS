package io.wollinger.wollews;

import java.io.IOException;
import java.net.ServerSocket;

public class Main {
    public static void main(String[] args) throws IOException {
        ServerSocket socket = new ServerSocket(8080);
        while(true) {
            RequestHandler handler = new RequestHandler(socket.accept());
            new Thread(handler).start();
        }
    }
}

package io.wollinger.wollews;

import io.wollinger.wollews.requests.Request;

import java.io.IOException;
import java.net.ServerSocket;

public class Main {
    public static void main(String[] args) throws IOException {
        ServerSocket socket = new ServerSocket(8080);
        while(true) {
            Request handler = new Request(socket.accept());
            new Thread(handler).start();
        }
    }
}

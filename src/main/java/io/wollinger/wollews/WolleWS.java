package io.wollinger.wollews;

import io.wollinger.wollews.requests.Request;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;

public class WolleWS {
    public static final String SERVER_NAME = "WolleWS";
    public static final String SERVER_VERSION = "0.0.1";
    public static final int PORT = 8080;

    private boolean running = true;
    private final HashMap<String, Site> sites = new HashMap<>();

    public WolleWS() {
        addSite(new Site("testdomain.com", new File("D:\\test\\")));
        try {
            requestLoop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void requestLoop() throws IOException {
        ServerSocket socket = new ServerSocket(PORT);
        while(running) {
            Request handler = new Request(socket.accept(), this);
            new Thread(handler).start();
        }
    }

    public Site getSite(String host) {
        if(!sites.containsKey(host))
            return null;
        return sites.get(host);
    }

    public void addSite(Site site) {
        sites.put(site.getHost(), site);
    }
}

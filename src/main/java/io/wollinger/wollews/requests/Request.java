package io.wollinger.wollews.requests;

import io.wollinger.wollews.HTMLUtils;
import io.wollinger.wollews.Site;
import io.wollinger.wollews.WolleWS;
import io.wollinger.wollews.requests.info.RequestInfo;
import io.wollinger.wollews.response.Response;
import io.wollinger.wollews.response.ResponseCode;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Request implements Runnable {
    private final Socket socket;
    private final WolleWS webserver;

    private Site site;
    private InetAddress requester;

    private BufferedReader in;
    private PrintWriter out;
    private BufferedOutputStream dataOut;

    private final RequestInfo requestInfo = new RequestInfo();

    public Request(Socket socket, WolleWS webserver) {
        this.socket = socket;
        this.webserver = webserver;
        requester = socket.getInetAddress();
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

            requestInfo.finish();

            System.out.println("Request from <" + requester + "> for site <" + requestInfo.getHost() + "> for file <" + requestInfo.getRequestedFile().getPath() + ">");

            site = webserver.getSite(requestInfo.getHost());
            if(site == null)
                return false;
        } catch (IOException ioException) {
            return false;
        }
        return true;
    }

    private boolean write() {
        File toCheck = new File(site.getWebroot(), requestInfo.getRequestedFile().getPath());

        if(toCheck.exists()) {
            if(toCheck.isDirectory()) {
                new Response(out, dataOut, ResponseCode.OK).sendResponse(HTMLUtils.getFolderListHTML(toCheck, requestInfo));
            } else {
                new Response(out, dataOut, ResponseCode.OK).sendResponse(toCheck);
            }
        } else {
            new Response(out, dataOut, ResponseCode.NOT_FOUND).sendResponse("File not found");
        }

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

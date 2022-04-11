package io.wollinger.wollews;

import java.io.File;

public class Site {
    private final String host;
    private final File webroot;

    public Site(String host, File webroot) {
        this.host = host;
        this.webroot = webroot;
    }

    public String getHost() {
        return host;
    }

    public File getWebroot() {
        return webroot;
    }
}

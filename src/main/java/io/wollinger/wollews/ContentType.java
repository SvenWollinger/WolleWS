package io.wollinger.wollews;

public enum ContentType {
    TEXT_HTML ("text/html");

    private final String type;

    ContentType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}

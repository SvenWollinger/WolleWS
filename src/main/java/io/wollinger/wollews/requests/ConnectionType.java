package io.wollinger.wollews.requests;

public enum ConnectionType {
    CLOSE, KEEP_ALIVE;

    public static final ConnectionType DEFAULT_CONNECTION_TYPE = CLOSE;

    public static ConnectionType fromString(String value) {
        if(value.equalsIgnoreCase("keep-alive"))
            return KEEP_ALIVE;
        else if(value.equalsIgnoreCase("close"))
            return CLOSE;
        return DEFAULT_CONNECTION_TYPE;
    }
}

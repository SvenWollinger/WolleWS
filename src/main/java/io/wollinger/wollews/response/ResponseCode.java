package io.wollinger.wollews.response;

public enum ResponseCode {
    OK ("200 OK"),
    BAD_REQUEST ("400 Bad Request"),
    UNAUTHORIZED ("401 Unauthorized"),
    FORBIDDEN ("403 Forbidden"),
    NOT_FOUND ("404 Not Found"),
    METHOD_NOT_ALLOWED ("405 Method Not Allowed");

    private final String code;

    ResponseCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}

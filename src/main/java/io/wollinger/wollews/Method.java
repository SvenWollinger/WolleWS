package io.wollinger.wollews;

import java.util.HashMap;

public enum Method {
    GET ("GET"),
    POST ("POST"),
    PUT ("PUT"),
    PATCH ("PATCH"),
    DELETE ("DELETE"),
    COPY ("COPY"),
    HEAD ("HEAD"),
    OPTIONS ("OPTIONS"),
    LINK ("LINK"),
    UNLINK ("UNLINK"),
    PURGE ("PURGE"),
    LOCK ("LOCK"),
    UNLOCK ("UNLOCK"),
    PROPFIND ("PROPFIND"),
    VIEW ("VIEW");

    private final HashMap<String, Method> methods = new HashMap<>();

    Method(String method) {
        methods.put(method, this);
    }

    public Method fromString(String method) {
        return methods.get(method);
    }
}

package io.wollinger.wollews;

public class KeyValueStorage {
    private final String key;
    private final String value;

    public KeyValueStorage(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}

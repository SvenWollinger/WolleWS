package io.wollinger.wollews;

import java.io.File;
import java.util.HashMap;

public enum ContentType {
    //Text
    TEXT_HTML ("text/html", new String[]{"html", "htm"}),
    TEXT_PLAIN ("text/plain", new String[]{"txt"}),

    //Images
    IMAGE_APNG ("image/apng", new String[]{"apng"}),
    IMAGE_AVIF ("image/avif", new String[]{"avif"}),
    IMAGE_GIF ("image/gif", new String[]{"gif"}),
    IMAGE_JPEG ("image/jpeg", new String[]{"jpeg"}),
    IMAGE_PNG ("image/png", new String[]{"png"}),
    IMAGE_SVG ("image/svg+xml", new String[]{"svg"}),
    IMAGE_WEBP ("image/webp", new String[]{"webp"});

    //Audio / Video


    private static HashMap<String, ContentType> types;
    private final String type;

    ContentType(String type, String[] extensions) {
        this.type = type;
        for(String ext : extensions)
            ContentType.addType(ext, this);
    }

    public String getType() {
        return type;
    }

    public static ContentType getContentTypeFromFile(File file) {
        String extension = getExtensionFromFile(file);

        if(types.containsKey(extension))
            return types.get(extension);

        return TEXT_PLAIN;
    }

    public static void addType(String extension, ContentType type) {
        if(types == null)
            types = new HashMap<>();
        types.put(extension, type);
    }

    public static String getExtensionFromFile(File file) {
        String name = file.getName();
        int index = name.lastIndexOf('.');
        if (index > 0) {
            return name.substring(index + 1);
        }
        return null;
    }
}

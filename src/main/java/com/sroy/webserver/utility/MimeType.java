package com.sroy.webserver.utility;

/**
 * Created by shamalroy
 */
public enum MimeType {
    TEXT("text/plain"),
    HTML("text/html"),
    JPEG("image/jpeg"),
    PNG("image/png"),
    CSS("text/css"),
    JS("application/javascript"),
    JSON("application/json");

    private final String mineType;

    MimeType(String mineType) {
        this.mineType = mineType;
    }

    @Override
    public String toString() {
        return this.mineType;
    }

    public static MimeType get(String name) {
        for (MimeType mimeType : MimeType.values()) {
            if (mimeType.name().equalsIgnoreCase(name)) {
                return mimeType;
            }
        }
        return null;
    }
}

package com.github.mcollovati.vaadin.filesystem;

/**
 * Represents the kind of a {@link FileSystemHandle}.
 *
 * <p>Corresponds to the {@code FileSystemHandle.kind} property in the
 * browser's File System API.
 *
 * @see FileSystemHandle#getKind()
 */
public enum HandleKind {
    /** A file handle. */
    FILE("file"),
    /** A directory handle. */
    DIRECTORY("directory");

    private final String jsValue;

    HandleKind(String jsValue) {
        this.jsValue = jsValue;
    }

    /**
     * Returns the JavaScript string value for this kind.
     *
     * @return the JS value ({@code "file"} or {@code "directory"})
     */
    public String getJsValue() {
        return jsValue;
    }

    /**
     * Returns the enum constant matching the given JavaScript string value.
     *
     * @param jsValue the JS value to look up
     * @return the matching {@code HandleKind}
     * @throws IllegalArgumentException if no constant matches the given value
     */
    public static HandleKind fromJsValue(String jsValue) {
        for (HandleKind kind : values()) {
            if (kind.jsValue.equals(jsValue)) {
                return kind;
            }
        }
        throw new IllegalArgumentException("Unknown HandleKind JS value: " + jsValue);
    }
}

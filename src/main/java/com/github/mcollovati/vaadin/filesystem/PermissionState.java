package com.github.mcollovati.vaadin.filesystem;

/**
 * Represents the state of a permission for a {@link FileSystemHandle}.
 *
 * <p>Returned by {@link FileSystemHandle#queryPermission(PermissionMode)} and
 * {@link FileSystemHandle#requestPermission(PermissionMode)}.
 */
public enum PermissionState {
    /** Permission has been granted. */
    GRANTED("granted"),
    /** Permission has been denied. */
    DENIED("denied"),
    /** The user will be prompted for permission. */
    PROMPT("prompt");

    private final String jsValue;

    PermissionState(String jsValue) {
        this.jsValue = jsValue;
    }

    /**
     * Returns the JavaScript string value for this state.
     *
     * @return the JS value ({@code "granted"}, {@code "denied"}, or
     *         {@code "prompt"})
     */
    public String getJsValue() {
        return jsValue;
    }

    /**
     * Returns the enum constant matching the given JavaScript string value.
     *
     * @param jsValue the JS value to look up
     * @return the matching {@code PermissionState}
     * @throws IllegalArgumentException if no constant matches the given value
     */
    public static PermissionState fromJsValue(String jsValue) {
        for (PermissionState state : values()) {
            if (state.jsValue.equals(jsValue)) {
                return state;
            }
        }
        throw new IllegalArgumentException("Unknown PermissionState JS value: " + jsValue);
    }
}

package com.github.mcollovati.vaadin.filesystem;

/**
 * Thrown when the browser does not support the File System API.
 *
 * <p>This typically occurs in browsers that have not implemented the
 * File System Access API (e.g., Firefox, older Safari versions).
 */
public class FileSystemApiNotSupportedException extends FileSystemApiException {

    /**
     * Creates a new exception with the given message.
     *
     * @param message the detail message
     */
    public FileSystemApiNotSupportedException(String message) {
        super(message);
    }

    /**
     * Creates a new exception with the given message and cause.
     *
     * @param message the detail message
     * @param cause   the cause
     */
    public FileSystemApiNotSupportedException(String message, Throwable cause) {
        super(message, cause);
    }
}

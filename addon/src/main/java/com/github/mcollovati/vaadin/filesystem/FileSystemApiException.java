package com.github.mcollovati.vaadin.filesystem;

/**
 * Base exception for errors originating from the browser's File System API.
 *
 * <p>Subclasses represent specific error conditions such as missing entries,
 * denied permissions, or type mismatches. When a JavaScript {@code DOMException}
 * is thrown during a File System API operation, it is mapped to the
 * appropriate subclass on the server side.
 *
 * @see FileSystemNotFoundException
 * @see FileSystemNotAllowedException
 * @see FileSystemTypeMismatchException
 * @see FileSystemApiNotSupportedException
 */
public class FileSystemApiException extends RuntimeException {

    /**
     * Creates a new exception with the given message.
     *
     * @param message the detail message
     */
    public FileSystemApiException(String message) {
        super(message);
    }

    /**
     * Creates a new exception with the given message and cause.
     *
     * @param message the detail message
     * @param cause   the cause
     */
    public FileSystemApiException(String message, Throwable cause) {
        super(message, cause);
    }
}

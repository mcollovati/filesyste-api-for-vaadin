package com.github.mcollovati.vaadin.filesystem;

/**
 * Thrown when a file or directory is not found.
 *
 * <p>This corresponds to the browser's {@code NotFoundError} DOMException,
 * which occurs when attempting to access a file system entry that does not
 * exist (e.g., calling {@code getFileHandle()} for a non-existent file
 * without the {@code create} option).
 */
public class FileSystemNotFoundException extends FileSystemApiException {

    /**
     * Creates a new exception with the given message.
     *
     * @param message the detail message
     */
    public FileSystemNotFoundException(String message) {
        super(message);
    }

    /**
     * Creates a new exception with the given message and cause.
     *
     * @param message the detail message
     * @param cause   the cause
     */
    public FileSystemNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

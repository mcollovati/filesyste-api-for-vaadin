package com.github.mcollovati.vaadin.filesystem;

/**
 * Thrown when the type of a file system entry does not match the expected type.
 *
 * <p>This corresponds to the browser's {@code TypeMismatchError} DOMException,
 * which occurs when an entry is found but its kind (file vs. directory) does
 * not match the requested operation (e.g., calling {@code getFileHandle()} on
 * a name that refers to a directory).
 */
public class FileSystemTypeMismatchException extends FileSystemApiException {

    /**
     * Creates a new exception with the given message.
     *
     * @param message the detail message
     */
    public FileSystemTypeMismatchException(String message) {
        super(message);
    }

    /**
     * Creates a new exception with the given message and cause.
     *
     * @param message the detail message
     * @param cause   the cause
     */
    public FileSystemTypeMismatchException(String message, Throwable cause) {
        super(message, cause);
    }
}

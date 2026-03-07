package com.github.mcollovati.vaadin.filesystem;

/**
 * Thrown when a File System API operation is not allowed or was cancelled
 * by the user.
 *
 * <p>This corresponds to the browser's {@code NotAllowedError} and
 * {@code AbortError} DOMExceptions. Common scenarios include:
 * <ul>
 *   <li>The user cancelling a file or directory picker dialog</li>
 *   <li>The user denying a permission request</li>
 *   <li>Attempting an operation without the required permission</li>
 * </ul>
 */
public class FileSystemNotAllowedException extends FileSystemApiException {

    /**
     * Creates a new exception with the given message.
     *
     * @param message the detail message
     */
    public FileSystemNotAllowedException(String message) {
        super(message);
    }

    /**
     * Creates a new exception with the given message and cause.
     *
     * @param message the detail message
     * @param cause   the cause
     */
    public FileSystemNotAllowedException(String message, Throwable cause) {
        super(message, cause);
    }
}

/*-
 * Copyright 2026 Marco Collovati
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

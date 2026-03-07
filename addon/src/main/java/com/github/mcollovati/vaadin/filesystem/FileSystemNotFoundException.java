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

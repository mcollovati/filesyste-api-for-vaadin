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

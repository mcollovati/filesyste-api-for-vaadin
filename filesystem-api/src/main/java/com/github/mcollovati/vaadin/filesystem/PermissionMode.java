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

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Represents the permission mode for a {@link FileSystemHandle}.
 *
 * <p>Used with {@link FileSystemHandle#queryPermission(PermissionMode)} and
 * {@link FileSystemHandle#requestPermission(PermissionMode)} to check or
 * request read or read-write access.
 */
public enum PermissionMode {
    /** Read-only access. */
    READ("read"),
    /** Read and write access. */
    READWRITE("readwrite");

    private final String jsValue;

    PermissionMode(String jsValue) {
        this.jsValue = jsValue;
    }

    /**
     * Returns the JavaScript string value for this mode.
     *
     * @return the JS value ({@code "read"} or {@code "readwrite"})
     */
    @JsonValue
    public String getJsValue() {
        return jsValue;
    }
}

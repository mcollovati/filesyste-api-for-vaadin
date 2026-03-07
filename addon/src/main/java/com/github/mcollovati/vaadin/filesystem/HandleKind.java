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
 * Represents the kind of a {@link FileSystemHandle}.
 *
 * <p>Corresponds to the {@code FileSystemHandle.kind} property in the
 * browser's File System API.
 *
 * @see FileSystemHandle#getKind()
 */
public enum HandleKind {
    /** A file handle. */
    FILE("file"),
    /** A directory handle. */
    DIRECTORY("directory");

    private final String jsValue;

    HandleKind(String jsValue) {
        this.jsValue = jsValue;
    }

    /**
     * Returns the JavaScript string value for this kind.
     *
     * @return the JS value ({@code "file"} or {@code "directory"})
     */
    public String getJsValue() {
        return jsValue;
    }

    /**
     * Returns the enum constant matching the given JavaScript string value.
     *
     * @param jsValue the JS value to look up
     * @return the matching {@code HandleKind}
     * @throws IllegalArgumentException if no constant matches the given value
     */
    public static HandleKind fromJsValue(String jsValue) {
        for (HandleKind kind : values()) {
            if (kind.jsValue.equals(jsValue)) {
                return kind;
            }
        }
        throw new IllegalArgumentException("Unknown HandleKind JS value: " + jsValue);
    }
}

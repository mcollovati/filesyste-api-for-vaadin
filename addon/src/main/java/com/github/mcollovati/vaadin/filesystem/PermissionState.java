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
 * Represents the state of a permission for a {@link FileSystemHandle}.
 *
 * <p>Returned by {@link FileSystemHandle#queryPermission(PermissionMode)} and
 * {@link FileSystemHandle#requestPermission(PermissionMode)}.
 */
public enum PermissionState {
    /** Permission has been granted. */
    GRANTED("granted"),
    /** Permission has been denied. */
    DENIED("denied"),
    /** The user will be prompted for permission. */
    PROMPT("prompt");

    private final String jsValue;

    PermissionState(String jsValue) {
        this.jsValue = jsValue;
    }

    /**
     * Returns the JavaScript string value for this state.
     *
     * @return the JS value ({@code "granted"}, {@code "denied"}, or
     *         {@code "prompt"})
     */
    public String getJsValue() {
        return jsValue;
    }

    /**
     * Returns the enum constant matching the given JavaScript string value.
     *
     * @param jsValue the JS value to look up
     * @return the matching {@code PermissionState}
     * @throws IllegalArgumentException if no constant matches the given value
     */
    public static PermissionState fromJsValue(String jsValue) {
        for (PermissionState state : values()) {
            if (state.jsValue.equals(jsValue)) {
                return state;
            }
        }
        throw new IllegalArgumentException("Unknown PermissionState JS value: " + jsValue);
    }
}

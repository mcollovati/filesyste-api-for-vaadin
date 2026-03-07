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
 * Well-known directories recognized by the File System API pickers
 * as starting locations.
 *
 * @see OpenFilePickerOptions.Builder#startIn(WellKnownDirectory)
 * @see SaveFilePickerOptions.Builder#startIn(WellKnownDirectory)
 * @see DirectoryPickerOptions.Builder#startIn(WellKnownDirectory)
 */
public enum WellKnownDirectory {
    /** The user's desktop directory. */
    DESKTOP("desktop"),
    /** The user's documents directory. */
    DOCUMENTS("documents"),
    /** The user's downloads directory. */
    DOWNLOADS("downloads"),
    /** The user's music directory. */
    MUSIC("music"),
    /** The user's pictures directory. */
    PICTURES("pictures"),
    /** The user's videos directory. */
    VIDEOS("videos");

    private final String value;

    WellKnownDirectory(String value) {
        this.value = value;
    }

    /**
     * Returns the string value expected by the browser API.
     *
     * @return the directory identifier
     */
    public String getValue() {
        return value;
    }
}

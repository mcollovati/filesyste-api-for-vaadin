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
package com.github.mcollovati.vaadin.filesystem.unit;

import static org.junit.jupiter.api.Assertions.*;

import com.github.mcollovati.vaadin.filesystem.DirectoryPickerOptions;
import com.github.mcollovati.vaadin.filesystem.PermissionMode;
import com.github.mcollovati.vaadin.filesystem.WellKnownDirectory;
import org.junit.jupiter.api.Test;

class DirectoryPickerOptionsTest {

    @Test
    void defaultOptionsHaveNullFields() {
        var options = DirectoryPickerOptions.builder().build();
        assertNull(options.getStartIn());
        assertNull(options.getMode());
    }

    @Test
    void builderSetsMode() {
        var options =
                DirectoryPickerOptions.builder().mode(PermissionMode.READWRITE).build();
        assertEquals(PermissionMode.READWRITE, options.getMode());
    }

    @Test
    void builderSetsStartInString() {
        var options = DirectoryPickerOptions.builder().startIn("desktop").build();
        assertEquals("desktop", options.getStartIn());
    }

    @Test
    void builderSetsStartInWellKnownDirectory() {
        var options = DirectoryPickerOptions.builder()
                .startIn(WellKnownDirectory.DOCUMENTS)
                .build();
        assertEquals("documents", options.getStartIn());
    }

    @Test
    void rebuildCopiesAllFields() {
        var original = DirectoryPickerOptions.builder()
                .startIn("desktop")
                .mode(PermissionMode.READWRITE)
                .build();

        var copy = original.rebuild().build();

        assertEquals(original.getStartIn(), copy.getStartIn());
        assertEquals(original.getMode(), copy.getMode());
    }

    @Test
    void rebuildAllowsSelectiveOverride() {
        var original = DirectoryPickerOptions.builder()
                .startIn("desktop")
                .mode(PermissionMode.READ)
                .build();

        var modified = original.rebuild().mode(PermissionMode.READWRITE).build();

        assertEquals(PermissionMode.READWRITE, modified.getMode());
        assertEquals("desktop", modified.getStartIn());
    }

    @Test
    void rebuildDoesNotMutateOriginal() {
        var original =
                DirectoryPickerOptions.builder().mode(PermissionMode.READ).build();

        original.rebuild().mode(PermissionMode.READWRITE).build();

        assertEquals(PermissionMode.READ, original.getMode());
    }
}

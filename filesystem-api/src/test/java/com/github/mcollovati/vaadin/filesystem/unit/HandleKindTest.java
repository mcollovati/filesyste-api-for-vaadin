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

import com.github.mcollovati.vaadin.filesystem.HandleKind;
import org.junit.jupiter.api.Test;

class HandleKindTest {

    @Test
    void fileJsValue() {
        assertEquals("file", HandleKind.FILE.getJsValue());
    }

    @Test
    void directoryJsValue() {
        assertEquals("directory", HandleKind.DIRECTORY.getJsValue());
    }

    @Test
    void fromJsValueFile() {
        assertEquals(HandleKind.FILE, HandleKind.fromJsValue("file"));
    }

    @Test
    void fromJsValueDirectory() {
        assertEquals(HandleKind.DIRECTORY, HandleKind.fromJsValue("directory"));
    }

    @Test
    void fromJsValueUnknownThrows() {
        assertThrows(IllegalArgumentException.class, () -> HandleKind.fromJsValue("unknown"));
    }

    @Test
    void enumValues() {
        assertEquals(2, HandleKind.values().length);
    }
}

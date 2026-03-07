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

import com.github.mcollovati.vaadin.filesystem.FileTypeFilter;
import java.util.List;
import org.junit.jupiter.api.Test;

class FileTypeFilterTest {

    @Test
    void ofFactory() {
        var filter = FileTypeFilter.of("Images", "image/*", ".png", ".jpg");
        assertEquals("Images", filter.description());
        assertEquals(1, filter.accept().size());
        assertEquals(List.of(".png", ".jpg"), filter.accept().get("image/*"));
    }

    @Test
    void ofFactoryNoExtensions() {
        var filter = FileTypeFilter.of("All Images", "image/*");
        assertEquals("All Images", filter.description());
        assertEquals(List.of(), filter.accept().get("image/*"));
    }
}

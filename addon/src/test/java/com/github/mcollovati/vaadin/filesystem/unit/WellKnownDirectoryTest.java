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

import com.github.mcollovati.vaadin.filesystem.WellKnownDirectory;
import org.junit.jupiter.api.Test;

class WellKnownDirectoryTest {

    @Test
    void values() {
        assertEquals("desktop", WellKnownDirectory.DESKTOP.getValue());
        assertEquals("documents", WellKnownDirectory.DOCUMENTS.getValue());
        assertEquals("downloads", WellKnownDirectory.DOWNLOADS.getValue());
        assertEquals("music", WellKnownDirectory.MUSIC.getValue());
        assertEquals("pictures", WellKnownDirectory.PICTURES.getValue());
        assertEquals("videos", WellKnownDirectory.VIDEOS.getValue());
    }

    @Test
    void allValuesPresent() {
        assertEquals(6, WellKnownDirectory.values().length);
    }
}

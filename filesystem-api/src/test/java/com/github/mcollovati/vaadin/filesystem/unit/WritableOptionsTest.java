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

import com.github.mcollovati.vaadin.filesystem.WritableOptions;
import org.junit.jupiter.api.Test;

class WritableOptionsTest {

    @Test
    void defaultOptions() {
        var options = WritableOptions.builder().build();
        assertFalse(options.keepExistingData());
    }

    @Test
    void keepExistingDataTrue() {
        var options = WritableOptions.builder().keepExistingData(true).build();
        assertTrue(options.keepExistingData());
    }

    @Test
    void defaultConstructor() {
        var options = new WritableOptions();
        assertFalse(options.keepExistingData());
    }
}

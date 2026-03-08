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

import com.github.mcollovati.vaadin.filesystem.PermissionMode;
import com.github.mcollovati.vaadin.filesystem.PermissionState;
import org.junit.jupiter.api.Test;

class PermissionEnumsTest {

    @Test
    void permissionModeReadJsValue() {
        assertEquals("read", PermissionMode.READ.getJsValue());
    }

    @Test
    void permissionModeReadwriteJsValue() {
        assertEquals("readwrite", PermissionMode.READWRITE.getJsValue());
    }

    @Test
    void permissionModeValues() {
        assertEquals(2, PermissionMode.values().length);
    }

    @Test
    void permissionStateGrantedJsValue() {
        assertEquals("granted", PermissionState.GRANTED.getJsValue());
    }

    @Test
    void permissionStateDeniedJsValue() {
        assertEquals("denied", PermissionState.DENIED.getJsValue());
    }

    @Test
    void permissionStatePromptJsValue() {
        assertEquals("prompt", PermissionState.PROMPT.getJsValue());
    }

    @Test
    void fromJsValueGranted() {
        assertEquals(PermissionState.GRANTED, PermissionState.fromJsValue("granted"));
    }

    @Test
    void fromJsValueDenied() {
        assertEquals(PermissionState.DENIED, PermissionState.fromJsValue("denied"));
    }

    @Test
    void fromJsValuePrompt() {
        assertEquals(PermissionState.PROMPT, PermissionState.fromJsValue("prompt"));
    }

    @Test
    void fromJsValueUnknownThrows() {
        assertThrows(IllegalArgumentException.class, () -> PermissionState.fromJsValue("unknown"));
    }

    @Test
    void permissionStateValues() {
        assertEquals(3, PermissionState.values().length);
    }
}

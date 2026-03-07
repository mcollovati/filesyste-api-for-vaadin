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

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

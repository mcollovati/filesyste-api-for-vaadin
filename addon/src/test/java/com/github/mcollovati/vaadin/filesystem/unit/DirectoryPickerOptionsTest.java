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
}

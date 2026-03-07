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

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

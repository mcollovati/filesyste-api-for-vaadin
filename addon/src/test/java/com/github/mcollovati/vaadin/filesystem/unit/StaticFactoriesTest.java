package com.github.mcollovati.vaadin.filesystem.unit;

import static org.junit.jupiter.api.Assertions.*;

import com.github.mcollovati.vaadin.filesystem.GetHandleOptions;
import com.github.mcollovati.vaadin.filesystem.RemoveEntryOptions;
import com.github.mcollovati.vaadin.filesystem.WritableOptions;
import org.junit.jupiter.api.Test;

class StaticFactoriesTest {

    @Test
    void getHandleOptionsCreating() {
        var options = GetHandleOptions.creating();
        assertTrue(options.create());
    }

    @Test
    void removeEntryOptionsRecursively() {
        var options = RemoveEntryOptions.recursively();
        assertTrue(options.recursive());
    }

    @Test
    void writableOptionsKeepingExistingData() {
        var options = WritableOptions.keepingExistingData();
        assertTrue(options.keepExistingData());
    }
}

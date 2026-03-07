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

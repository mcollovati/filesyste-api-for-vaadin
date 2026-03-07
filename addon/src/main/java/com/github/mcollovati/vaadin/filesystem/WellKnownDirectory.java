package com.github.mcollovati.vaadin.filesystem;

/**
 * Well-known directories recognized by the File System API pickers
 * as starting locations.
 *
 * @see OpenFilePickerOptions.Builder#startIn(WellKnownDirectory)
 * @see SaveFilePickerOptions.Builder#startIn(WellKnownDirectory)
 * @see DirectoryPickerOptions.Builder#startIn(WellKnownDirectory)
 */
public enum WellKnownDirectory {
    /** The user's desktop directory. */
    DESKTOP("desktop"),
    /** The user's documents directory. */
    DOCUMENTS("documents"),
    /** The user's downloads directory. */
    DOWNLOADS("downloads"),
    /** The user's music directory. */
    MUSIC("music"),
    /** The user's pictures directory. */
    PICTURES("pictures"),
    /** The user's videos directory. */
    VIDEOS("videos");

    private final String value;

    WellKnownDirectory(String value) {
        this.value = value;
    }

    /**
     * Returns the string value expected by the browser API.
     *
     * @return the directory identifier
     */
    public String getValue() {
        return value;
    }
}

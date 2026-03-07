package com.github.mcollovati.vaadin.filesystem;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Describes a file type filter for use in file picker options.
 *
 * <p>Each filter has a user-visible description and an accept map that
 * maps MIME types to lists of file extensions.
 *
 * <pre>{@code
 * var filter = new FileTypeFilter("Images",
 *         Map.of("image/*", List.of(".png", ".jpg")));
 * }</pre>
 *
 * @param description a human-readable description of the filter
 * @param accept a map of MIME types to accepted file extensions
 * @see OpenFilePickerOptions
 * @see SaveFilePickerOptions
 */
public record FileTypeFilter(String description, Map<String, List<String>> accept) implements Serializable {

    /**
     * Creates a filter for a single MIME type with optional extensions.
     *
     * <pre>{@code
     * FileTypeFilter.of("Images", "image/*", ".png", ".jpg")
     * }</pre>
     *
     * @param description a human-readable description
     * @param mimeType    the MIME type (e.g. {@code "image/*"})
     * @param extensions  file extensions (e.g. {@code ".png"})
     * @return a new filter
     */
    public static FileTypeFilter of(String description, String mimeType, String... extensions) {
        return new FileTypeFilter(description, Map.of(mimeType, List.of(extensions)));
    }
}

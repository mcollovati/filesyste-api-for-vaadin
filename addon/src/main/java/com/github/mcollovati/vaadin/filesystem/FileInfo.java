package com.github.mcollovati.vaadin.filesystem;

/**
 * Internal DTO for deserializing file metadata and content returned
 * from the client-side {@code getFile()} call.
 */
record FileInfo(String name, long size, String type, long lastModified, String content) {}

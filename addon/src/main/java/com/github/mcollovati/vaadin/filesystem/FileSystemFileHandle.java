package com.github.mcollovati.vaadin.filesystem;

import java.util.concurrent.CompletableFuture;

/**
 * A handle representing a file in the browser's file system.
 *
 * <p>This is the Java counterpart of the browser's
 * {@code FileSystemFileHandle} interface. Instances are obtained through
 * picker methods on {@link FileSystemAPI} or through
 * {@link FileSystemDirectoryHandle#getFileHandle(String, GetHandleOptions)}.
 *
 * @see FileSystemHandle
 * @see FileSystemDirectoryHandle
 */
public final class FileSystemFileHandle extends AbstractFileSystemHandle {

    FileSystemFileHandle(String handleId, String name, JsBridge bridge) {
        super(handleId, name, bridge);
    }

    @Override
    public HandleKind getKind() {
        return HandleKind.FILE;
    }

    /**
     * Reads the file content from the browser.
     *
     * <p>The file is read entirely into memory and transferred to the
     * server via base64 encoding. For very large files, consider the
     * memory implications on both the browser and server sides.
     *
     * @return a future that completes with the file data including
     *         metadata and content
     */
    public CompletableFuture<FileData> getFile() {
        return bridge().getFile(handleId());
    }

    /**
     * Creates a writable stream for this file with default options.
     *
     * <p>The returned stream can be used to write content to the file.
     * After writing, {@link FileSystemWritableFileStream#close()} must
     * be called to commit the changes.
     *
     * @return a future that completes with a writable stream
     * @see #createWritable(WritableOptions)
     */
    public CompletableFuture<FileSystemWritableFileStream> createWritable() {
        return createWritable(WritableOptions.builder().build());
    }

    /**
     * Creates a writable stream for this file with the given options.
     *
     * <p>The returned stream can be used to write content to the file.
     * After writing, {@link FileSystemWritableFileStream#close()} must
     * be called to commit the changes.
     *
     * @param options the writable stream options
     * @return a future that completes with a writable stream
     */
    public CompletableFuture<FileSystemWritableFileStream> createWritable(WritableOptions options) {
        return bridge().createWritable(handleId(), options);
    }
}

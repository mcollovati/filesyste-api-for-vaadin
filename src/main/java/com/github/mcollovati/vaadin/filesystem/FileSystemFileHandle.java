package com.github.mcollovati.vaadin.filesystem;

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
}

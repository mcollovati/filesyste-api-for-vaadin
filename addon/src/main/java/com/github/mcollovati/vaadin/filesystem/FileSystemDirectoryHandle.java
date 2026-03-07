package com.github.mcollovati.vaadin.filesystem;

/**
 * A handle representing a directory in the browser's file system.
 *
 * <p>This is the Java counterpart of the browser's
 * {@code FileSystemDirectoryHandle} interface. Instances are obtained
 * through {@link FileSystemAPI#showDirectoryPicker()} or through
 * {@link #getDirectoryHandle(String, GetHandleOptions)}.
 *
 * @see FileSystemHandle
 * @see FileSystemFileHandle
 */
public final class FileSystemDirectoryHandle extends AbstractFileSystemHandle {

    FileSystemDirectoryHandle(String handleId, String name, JsBridge bridge) {
        super(handleId, name, bridge);
    }

    @Override
    public HandleKind getKind() {
        return HandleKind.DIRECTORY;
    }
}

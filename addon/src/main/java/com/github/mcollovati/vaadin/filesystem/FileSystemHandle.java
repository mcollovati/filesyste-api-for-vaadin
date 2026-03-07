package com.github.mcollovati.vaadin.filesystem;

import java.io.Serializable;
import java.util.concurrent.CompletableFuture;

/**
 * Represents a handle to a file system entry (file or directory).
 *
 * <p>This is the Java counterpart of the browser's
 * {@code FileSystemHandle} interface. A handle is obtained through picker
 * methods on {@link FileSystemAPI} or through directory traversal on
 * {@link FileSystemDirectoryHandle}.
 *
 * <p>This is a sealed interface with two permitted implementations:
 * {@link FileSystemFileHandle} and {@link FileSystemDirectoryHandle}.
 *
 * @see FileSystemFileHandle
 * @see FileSystemDirectoryHandle
 */
public sealed interface FileSystemHandle extends Serializable permits AbstractFileSystemHandle {

    /**
     * Returns the kind of this handle.
     *
     * @return {@link HandleKind#FILE} or {@link HandleKind#DIRECTORY}
     */
    HandleKind getKind();

    /**
     * Returns the name of the file system entry represented by this handle.
     *
     * @return the entry name
     */
    String getName();

    /**
     * Checks whether this handle and the given handle represent the same
     * file system entry.
     *
     * @param other the other handle to compare with
     * @return a future that completes with {@code true} if both handles
     *         point to the same entry
     */
    CompletableFuture<Boolean> isSameEntry(FileSystemHandle other);

    /**
     * Queries the current permission state for this handle.
     *
     * @param mode the permission mode to query
     * @return a future that completes with the current permission state
     * @see PermissionMode
     */
    CompletableFuture<PermissionState> queryPermission(PermissionMode mode);

    /**
     * Requests permission for this handle. The browser may show a
     * permission prompt to the user.
     *
     * @param mode the permission mode to request
     * @return a future that completes with the resulting permission state
     * @see PermissionMode
     */
    CompletableFuture<PermissionState> requestPermission(PermissionMode mode);

    /**
     * Releases this handle, removing it from the client-side registry.
     *
     * <p>After calling this method, the handle should no longer be used
     * for any operations.
     */
    void release();
}

package com.github.mcollovati.vaadin.filesystem;

import java.util.concurrent.CompletableFuture;

/**
 * Base implementation of {@link FileSystemHandle} providing shared state
 * and behavior for file and directory handles.
 */
abstract sealed class AbstractFileSystemHandle implements FileSystemHandle
        permits FileSystemFileHandle, FileSystemDirectoryHandle {

    private final String handleId;
    private final String name;
    private final JsBridge bridge;

    AbstractFileSystemHandle(String handleId, String name, JsBridge bridge) {
        this.handleId = handleId;
        this.name = name;
        this.bridge = bridge;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public CompletableFuture<Boolean> isSameEntry(FileSystemHandle other) {
        return bridge.isSameEntry(handleId, bridge.getHandleId(other));
    }

    @Override
    public CompletableFuture<PermissionState> queryPermission(PermissionMode mode) {
        return bridge.queryPermission(handleId, mode);
    }

    @Override
    public CompletableFuture<PermissionState> requestPermission(PermissionMode mode) {
        return bridge.requestPermission(handleId, mode);
    }

    @Override
    public void release() {
        bridge.releaseHandle(handleId);
    }

    String handleId() {
        return handleId;
    }

    JsBridge bridge() {
        return bridge;
    }
}

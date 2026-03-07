/*-
 * Copyright 2026 Marco Collovati
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.mcollovati.vaadin.filesystem;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * An in-memory file system tree with a handle registry, used by
 * {@link FakeJsBridge} to simulate browser File System API operations.
 */
public final class InMemoryFileSystem {

    private final FsDirectory root = new FsDirectory();
    private final Map<String, FsNode> handleNodes = new LinkedHashMap<>();
    private final Map<String, String> handleNames = new LinkedHashMap<>();
    private final AtomicLong nextId = new AtomicLong();

    InMemoryFileSystem() {}

    /**
     * Returns the root directory of this file system.
     *
     * @return the root directory
     */
    public FsDirectory root() {
        return root;
    }

    /**
     * Checks whether a file or directory exists at the given path
     * (relative to root, using {@code /} as separator).
     *
     * @param path the path to check
     * @return {@code true} if the entry exists
     */
    public boolean exists(String path) {
        return resolveNode(path) != null;
    }

    /**
     * Returns the file at the given path (relative to root).
     *
     * @param path the file path
     * @return the file node
     * @throws IllegalArgumentException if the path does not refer to a file
     */
    public FsFile file(String path) {
        FsNode node = resolveNode(path);
        if (node instanceof FsFile f) {
            return f;
        }
        throw new IllegalArgumentException("Not a file: " + path);
    }

    /**
     * Returns the directory at the given path (relative to root).
     *
     * @param path the directory path
     * @return the directory node
     * @throws IllegalArgumentException if the path does not refer to a
     *         directory
     */
    public FsDirectory directory(String path) {
        FsNode node = resolveNode(path);
        if (node instanceof FsDirectory d) {
            return d;
        }
        throw new IllegalArgumentException("Not a directory: " + path);
    }

    String registerHandle(FsNode node, String name) {
        String id = String.valueOf(nextId.getAndIncrement());
        handleNodes.put(id, node);
        handleNames.put(id, name);
        return id;
    }

    FsNode resolveHandle(String handleId) {
        FsNode node = handleNodes.get(handleId);
        if (node == null) {
            throw new IllegalArgumentException("Unknown handle: " + handleId);
        }
        return node;
    }

    String handleName(String handleId) {
        String name = handleNames.get(handleId);
        if (name == null) {
            throw new IllegalArgumentException("Unknown handle: " + handleId);
        }
        return name;
    }

    void removeHandle(String handleId) {
        handleNodes.remove(handleId);
        handleNames.remove(handleId);
    }

    List<String> resolvePath(FsDirectory from, FsNode target) {
        List<String> path = new ArrayList<>();
        if (findPath(from, target, path)) {
            return path;
        }
        return null;
    }

    private boolean findPath(FsDirectory dir, FsNode target, List<String> path) {
        for (Map.Entry<String, FsNode> entry : dir.children().entrySet()) {
            if (entry.getValue() == target) {
                path.add(entry.getKey());
                return true;
            }
            if (entry.getValue() instanceof FsDirectory subDir) {
                path.add(entry.getKey());
                if (findPath(subDir, target, path)) {
                    return true;
                }
                path.removeLast();
            }
        }
        return false;
    }

    private FsNode resolveNode(String path) {
        if (path == null || path.isEmpty()) {
            return root;
        }
        String[] parts = path.split("/");
        FsNode current = root;
        for (String part : parts) {
            if (part.isEmpty()) continue;
            if (current instanceof FsDirectory dir) {
                if (!dir.hasChild(part)) {
                    return null;
                }
                current = dir.child(part);
            } else {
                return null;
            }
        }
        return current;
    }
}

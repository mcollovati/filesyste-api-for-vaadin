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

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A mutable in-memory directory node used by {@link FileSystemTester}.
 */
public final class FsDirectory implements FsNode {

    private final Map<String, FsNode> children = new LinkedHashMap<>();

    FsDirectory() {}

    /**
     * Returns an unmodifiable view of this directory's children.
     *
     * @return the children map (name to node)
     */
    public Map<String, FsNode> children() {
        return Collections.unmodifiableMap(children);
    }

    /**
     * Checks whether a child with the given name exists.
     *
     * @param name the child name
     * @return {@code true} if the child exists
     */
    public boolean hasChild(String name) {
        return children.containsKey(name);
    }

    /**
     * Returns the child node with the given name.
     *
     * @param name the child name
     * @return the child node
     * @throws IllegalArgumentException if no child with that name exists
     */
    public FsNode child(String name) {
        FsNode node = children.get(name);
        if (node == null) {
            throw new IllegalArgumentException("No child named '" + name + "'");
        }
        return node;
    }

    /**
     * Adds or replaces a child in this directory.
     *
     * @param name the child name
     * @param node the child node
     */
    public void putChild(String name, FsNode node) {
        children.put(name, node);
    }

    /**
     * Removes a child from this directory.
     *
     * @param name the child name
     */
    public void removeChild(String name) {
        children.remove(name);
    }
}

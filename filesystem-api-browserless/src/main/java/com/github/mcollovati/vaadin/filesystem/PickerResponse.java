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

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Defines how a fake picker should respond in a browserless test.
 *
 * @param <T> the picker result type
 * @see FileSystemTester.Builder#onOpenFilePicker(PickerResponse)
 * @see FileSystemTester.Builder#onSaveFilePicker(PickerResponse)
 * @see FileSystemTester.Builder#onDirectoryPicker(PickerResponse)
 */
@FunctionalInterface
public interface PickerResponse<T> {

    /**
     * Produces the picker result using the given file system and bridge.
     *
     * @param fs     the in-memory file system
     * @param bridge the fake bridge
     * @return a future with the picker result
     */
    CompletableFuture<T> respond(InMemoryFileSystem fs, FakeJsBridge bridge);

    /**
     * Returns a response that resolves file handles for the named files
     * in the root directory.
     *
     * @param fileNames the file names to return
     * @return a picker response returning file handles
     */
    static PickerResponse<List<FileSystemFileHandle>> returning(String... fileNames) {
        return (fs, bridge) -> {
            List<FileSystemFileHandle> handles = Arrays.stream(fileNames)
                    .map(name -> {
                        FsNode node = fs.root().child(name);
                        if (!(node instanceof FsFile)) {
                            throw new IllegalArgumentException("Not a file: " + name);
                        }
                        String id = fs.registerHandle(node, name);
                        return new FileSystemFileHandle(id, name, bridge);
                    })
                    .toList();
            return CompletableFuture.completedFuture(handles);
        };
    }

    /**
     * Returns a response that resolves a single file handle for a save
     * picker. Creates the file if it does not exist.
     *
     * @param fileName the file name to return
     * @return a picker response returning a file handle
     */
    static PickerResponse<FileSystemFileHandle> returningSingle(String fileName) {
        return (fs, bridge) -> {
            FsFile file;
            if (fs.root().hasChild(fileName)) {
                FsNode node = fs.root().child(fileName);
                if (!(node instanceof FsFile)) {
                    throw new IllegalArgumentException("Not a file: " + fileName);
                }
                file = (FsFile) node;
            } else {
                file = new FsFile(new byte[0], "");
                fs.root().putChild(fileName, file);
            }
            String id = fs.registerHandle(file, fileName);
            return CompletableFuture.completedFuture(new FileSystemFileHandle(id, fileName, bridge));
        };
    }

    /**
     * Returns a response that resolves the root directory.
     *
     * @return a picker response returning the root directory handle
     */
    static PickerResponse<FileSystemDirectoryHandle> returningRoot() {
        return (fs, bridge) -> {
            String id = fs.registerHandle(fs.root(), "");
            return CompletableFuture.completedFuture(new FileSystemDirectoryHandle(id, "", bridge));
        };
    }

    /**
     * Returns a response that resolves a named subdirectory.
     * Creates the directory if it does not exist.
     *
     * @param name the directory name
     * @return a picker response returning the directory handle
     */
    static PickerResponse<FileSystemDirectoryHandle> returningDirectory(String name) {
        return (fs, bridge) -> {
            FsDirectory dir;
            if (fs.root().hasChild(name)) {
                FsNode node = fs.root().child(name);
                if (!(node instanceof FsDirectory)) {
                    throw new IllegalArgumentException("Not a directory: " + name);
                }
                dir = (FsDirectory) node;
            } else {
                dir = new FsDirectory();
                fs.root().putChild(name, dir);
            }
            String id = fs.registerHandle(dir, name);
            return CompletableFuture.completedFuture(new FileSystemDirectoryHandle(id, name, bridge));
        };
    }

    /**
     * Returns a response that fails with a
     * {@link FileSystemNotAllowedException}, simulating the user
     * cancelling the picker.
     *
     * @param <T> the picker result type
     * @return a picker response that always fails
     */
    static <T> PickerResponse<T> cancelling() {
        return (fs, bridge) ->
                CompletableFuture.failedFuture(new FileSystemNotAllowedException("User cancelled the picker"));
    }
}

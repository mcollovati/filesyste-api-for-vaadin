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

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * In-memory writable stream that buffers writes and flushes to a
 * target {@link FsFile} on close.
 */
class FakeWritableStream {

    private byte[] buffer;
    private int position;
    private final FsFile target;

    FakeWritableStream(FsFile target, boolean keepExistingData) {
        this.target = target;
        if (keepExistingData) {
            this.buffer = target.content().clone();
            this.position = this.buffer.length;
        } else {
            this.buffer = new byte[0];
            this.position = 0;
        }
    }

    void writeText(String text) {
        writeBytes(text.getBytes(StandardCharsets.UTF_8));
    }

    void writeBytes(byte[] data) {
        int needed = position + data.length;
        if (needed > buffer.length) {
            buffer = Arrays.copyOf(buffer, needed);
        }
        System.arraycopy(data, 0, buffer, position, data.length);
        position += data.length;
    }

    void seek(long pos) {
        this.position = (int) pos;
    }

    void truncate(long size) {
        int newSize = (int) size;
        if (newSize < buffer.length) {
            buffer = Arrays.copyOf(buffer, newSize);
        } else if (newSize > buffer.length) {
            buffer = Arrays.copyOf(buffer, newSize);
        }
        if (position > newSize) {
            position = newSize;
        }
    }

    void close() {
        target.setContent(buffer.clone());
    }
}

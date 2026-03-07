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

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * A mutable in-memory file node used by {@link FileSystemTester}.
 */
public final class FsFile implements FsNode {

    private byte[] content;
    private String mimeType;
    private long lastModified;

    FsFile(byte[] content, String mimeType) {
        this.content = content;
        this.mimeType = mimeType;
        this.lastModified = System.currentTimeMillis();
    }

    /**
     * Returns the raw file content.
     *
     * @return the content bytes
     */
    public byte[] content() {
        return content;
    }

    /**
     * Returns the file content as a UTF-8 string.
     *
     * @return the content as a string
     */
    public String contentAsString() {
        return contentAsString(StandardCharsets.UTF_8);
    }

    /**
     * Returns the file content as a string in the given charset.
     *
     * @param charset the charset to use
     * @return the content as a string
     */
    public String contentAsString(Charset charset) {
        return new String(content, charset);
    }

    /**
     * Returns the MIME type of this file.
     *
     * @return the MIME type
     */
    public String mimeType() {
        return mimeType;
    }

    /**
     * Returns the last-modified timestamp.
     *
     * @return milliseconds since the Unix epoch
     */
    public long lastModified() {
        return lastModified;
    }

    /**
     * Sets the file content.
     *
     * @param content the new content bytes
     */
    public void setContent(byte[] content) {
        this.content = content;
        this.lastModified = System.currentTimeMillis();
    }

    /**
     * Sets the MIME type.
     *
     * @param mimeType the new MIME type
     */
    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }
}

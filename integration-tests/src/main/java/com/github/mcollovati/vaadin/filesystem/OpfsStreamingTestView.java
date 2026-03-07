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

import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.streams.DownloadHandler;
import com.vaadin.flow.server.streams.DownloadResponse;
import com.vaadin.flow.server.streams.UploadHandler;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

@Route("test/opfs-streaming")
public class OpfsStreamingTestView extends AbstractOpfsTestView {

    @Override
    void addActions() {
        add(button("upload-to-server", "Upload to Server", this::onUpload));
        add(button("download-from-server", "Download from Server", this::onDownload));
    }

    private void onUpload() {
        getOpfsRoot()
                .thenCompose(root -> cleanupOpfs(root).thenApply(v -> root))
                .thenCompose(root -> root.getFileHandle("upload.txt", GetHandleOptions.creating()))
                .thenCompose(file -> file.writeString("upload content").thenApply(v -> file))
                .thenCompose(file -> {
                    UploadHandler handler = UploadHandler.inMemory((metadata, bytes) -> {
                        String received = new String(bytes, StandardCharsets.UTF_8);
                        appendLog("uploaded=" + received);
                        appendLog("fileName=" + metadata.fileName());
                    });
                    return file.uploadTo(handler);
                })
                .thenRun(() -> appendLog("upload-complete"))
                .exceptionally(this::logError);
    }

    private void onDownload() {
        byte[] content = "downloaded content".getBytes(StandardCharsets.UTF_8);
        getOpfsRoot()
                .thenCompose(root -> cleanupOpfs(root).thenApply(v -> root))
                .thenCompose(root -> root.getFileHandle("download.txt", GetHandleOptions.creating()))
                .thenCompose(file -> {
                    DownloadHandler handler = DownloadHandler.fromInputStream(event -> new DownloadResponse(
                            new ByteArrayInputStream(content), "download.txt", "text/plain", content.length));
                    return file.downloadFrom(handler).thenApply(v -> file);
                })
                .thenCompose(FileSystemFileHandle::getFile)
                .thenAccept(data -> {
                    String received = new String(data.getContent(), StandardCharsets.UTF_8);
                    appendLog("downloaded=" + received);
                })
                .exceptionally(this::logError);
    }
}

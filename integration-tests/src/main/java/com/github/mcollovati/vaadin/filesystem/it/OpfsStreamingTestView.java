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
package com.github.mcollovati.vaadin.filesystem.it;

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
        opfs().clear()
                .thenCompose(v -> opfs().writeFile("upload.txt", "upload content"))
                .thenCompose(v -> {
                    UploadHandler handler = UploadHandler.inMemory((metadata, bytes) -> {
                        String received = new String(bytes, StandardCharsets.UTF_8);
                        appendLog("uploaded=" + received);
                        appendLog("fileName=" + metadata.fileName());
                    });
                    return opfs().uploadFile("upload.txt", handler);
                })
                .thenRun(() -> appendLog("upload-complete"))
                .exceptionally(this::logError);
    }

    private void onDownload() {
        byte[] content = "downloaded content".getBytes(StandardCharsets.UTF_8);
        opfs().clear()
                .thenCompose(v -> {
                    DownloadHandler handler = DownloadHandler.fromInputStream(event -> new DownloadResponse(
                            new ByteArrayInputStream(content), "download.txt", "text/plain", content.length));
                    return opfs().downloadFile("download.txt", handler);
                })
                .thenCompose(v -> opfs().readFile("download.txt"))
                .thenAccept(data -> {
                    String received = new String(data.getContent(), StandardCharsets.UTF_8);
                    appendLog("downloaded=" + received);
                })
                .exceptionally(this::logError);
    }
}

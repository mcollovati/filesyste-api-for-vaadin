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

import com.github.mcollovati.vaadin.filesystem.GetHandleOptions;
import com.github.mcollovati.vaadin.filesystem.WritableOptions;
import com.vaadin.flow.router.Route;
import java.nio.charset.StandardCharsets;

@Route("test/opfs-writable")
public class OpfsWritableStreamTestView extends AbstractOpfsTestView {

    @Override
    void addActions() {
        add(button("write-via-stream", "Write via Stream", this::onWriteViaStream));
        add(button("seek-and-write", "Seek and Write", this::onSeekAndWrite));
        add(button("truncate", "Truncate", this::onTruncate));
        add(button("keep-existing-data", "Keep Existing Data", this::onKeepExistingData));
    }

    private void onWriteViaStream() {
        opfs().clear()
                .thenCompose(v -> opfs().getFileHandle("stream.txt", GetHandleOptions.creating()))
                .thenCompose(file -> file.createWritable()
                        .thenCompose(w -> w.write("Hello Stream").thenCompose(v2 -> w.close()))
                        .thenCompose(v2 -> file.getFile()))
                .thenAccept(data -> {
                    String content = new String(data.getContent(), StandardCharsets.UTF_8);
                    appendLog("content=" + content);
                })
                .exceptionally(this::logError);
    }

    private void onSeekAndWrite() {
        opfs().clear()
                .thenCompose(v -> opfs().getFileHandle("seek.txt", GetHandleOptions.creating()))
                .thenCompose(file -> file.createWritable()
                        .thenCompose(w -> w.write("AAABBB")
                                .thenCompose(v2 -> w.seek(3))
                                .thenCompose(v2 -> w.write("CCC"))
                                .thenCompose(v2 -> w.close()))
                        .thenCompose(v2 -> file.getFile()))
                .thenAccept(data -> {
                    String content = new String(data.getContent(), StandardCharsets.UTF_8);
                    appendLog("content=" + content);
                })
                .exceptionally(this::logError);
    }

    private void onTruncate() {
        opfs().clear()
                .thenCompose(v -> opfs().getFileHandle("trunc.txt", GetHandleOptions.creating()))
                .thenCompose(file -> file.createWritable()
                        .thenCompose(w -> w.write("Hello World")
                                .thenCompose(v2 -> w.truncate(5))
                                .thenCompose(v2 -> w.close()))
                        .thenCompose(v2 -> file.getFile()))
                .thenAccept(data -> {
                    String content = new String(data.getContent(), StandardCharsets.UTF_8);
                    appendLog("content=" + content);
                    appendLog("size=" + data.getSize());
                })
                .exceptionally(this::logError);
    }

    private void onKeepExistingData() {
        opfs().clear()
                .thenCompose(v -> opfs().getFileHandle("keep.txt", GetHandleOptions.creating()))
                .thenCompose(file -> file.writeString("Original")
                        .thenCompose(v2 -> file.createWritable(WritableOptions.keepingExistingData()))
                        .thenCompose(w ->
                                w.seek(0).thenCompose(v2 -> w.write("Modified")).thenCompose(v2 -> w.close()))
                        .thenCompose(v2 -> file.getFile()))
                .thenAccept(data -> {
                    String content = new String(data.getContent(), StandardCharsets.UTF_8);
                    appendLog("content=" + content);
                })
                .exceptionally(this::logError);
    }
}

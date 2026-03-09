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
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Route("test/opfs-read-write")
public class OpfsReadWriteTestView extends AbstractOpfsTestView {

    @Override
    void addActions() {
        add(button("write-read-text", "Write & Read Text", this::onWriteReadText));
        add(button("write-read-bytes", "Write & Read Bytes", this::onWriteReadBytes));
    }

    private void onWriteReadText() {
        opfs().clear()
                .thenCompose(v -> opfs().writeFile("test.txt", "Hello OPFS"))
                .thenCompose(v -> opfs().readFile("test.txt"))
                .thenAccept(data -> {
                    String content = new String(data.getContent(), StandardCharsets.UTF_8);
                    appendLog("text=" + content);
                    appendLog("size=" + data.getSize());
                })
                .exceptionally(this::logError);
    }

    private void onWriteReadBytes() {
        byte[] testData = new byte[] {0x01, 0x02, 0x03, (byte) 0xFF};
        opfs().clear()
                .thenCompose(v -> opfs().writeFile("test.bin", testData))
                .thenCompose(v -> opfs().readFile("test.bin"))
                .thenAccept(data -> {
                    appendLog("bytes=" + Arrays.toString(data.getContent()));
                    appendLog("size=" + data.getSize());
                })
                .exceptionally(this::logError);
    }
}

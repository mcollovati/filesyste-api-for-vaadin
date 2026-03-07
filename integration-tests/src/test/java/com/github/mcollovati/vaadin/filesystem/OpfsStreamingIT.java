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

import org.junit.jupiter.api.Test;

class OpfsStreamingIT extends AbstractIT {

    @Test
    void uploadToServer() {
        navigateTo("test/opfs-streaming");
        clickButton("upload-to-server");
        waitForLog("uploaded=upload content", 15000);
        waitForLog("upload-complete", 15000);
    }

    @Test
    void downloadFromServer() {
        navigateTo("test/opfs-streaming");
        clickButton("download-from-server");
        waitForLog("downloaded=downloaded content", 15000);
    }
}

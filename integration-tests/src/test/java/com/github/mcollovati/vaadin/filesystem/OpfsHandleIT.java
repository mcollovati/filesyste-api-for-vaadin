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

class OpfsHandleIT extends AbstractIT {

    @Test
    void isSameEntry() {
        navigateTo("test/opfs-handles");
        clickButton("is-same-entry");
        waitForLog("same=true");
    }

    @Test
    void queryPermission() {
        navigateTo("test/opfs-handles");
        clickButton("query-permission");
        waitForLog("permission=");
    }

    @Test
    void requestPermission() {
        navigateTo("test/opfs-handles");
        clickButton("request-permission");
        waitForLog("permission=");
    }
}

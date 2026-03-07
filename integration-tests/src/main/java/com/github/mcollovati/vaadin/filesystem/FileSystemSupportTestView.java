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

@Route("test/support")
public class FileSystemSupportTestView extends AbstractOpfsTestView {

    @Override
    void addActions() {
        add(button("check-support", "Check Support", this::onCheckSupport));
    }

    private void onCheckSupport() {
        fs().isSupported()
                .thenAccept(supported -> appendLog("supported=" + supported))
                .exceptionally(this::logError);
    }
}

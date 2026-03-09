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
import com.vaadin.flow.router.Route;

@Route("test/opfs-errors")
public class OpfsErrorTestView extends AbstractOpfsTestView {

    @Override
    void addActions() {
        add(button("not-found-error", "Not Found Error", this::onNotFoundError));
        add(button("type-mismatch-error", "Type Mismatch Error", this::onTypeMismatchError));
    }

    private void onNotFoundError() {
        opfs().clear()
                .thenCompose(v -> opfs().getFileHandle("nonexistent.txt"))
                .thenAccept(ignore -> {})
                .exceptionally(this::logError);
    }

    private void onTypeMismatchError() {
        opfs().clear()
                .thenCompose(v -> opfs().getDirectoryHandle("mismatch", GetHandleOptions.creating()))
                .thenCompose(dir -> opfs().getFileHandle("mismatch"))
                .thenAccept(ignore -> {})
                .exceptionally(this::logError);
    }
}

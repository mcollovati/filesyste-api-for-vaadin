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
import java.util.stream.Collectors;

@Route("test/opfs-directory")
public class OpfsDirectoryTestView extends AbstractOpfsTestView {

    @Override
    void addActions() {
        add(button("setup-and-list", "Setup & List", this::onSetupAndList));
        add(button("resolve-path", "Resolve Path", this::onResolvePath));
        add(button("remove-entry", "Remove Entry", this::onRemoveEntry));
        add(button("create-nested", "Create Nested", this::onCreateNested));
    }

    private void onSetupAndList() {
        opfs().clear()
                .thenCompose(v -> opfs().getFileHandle("a.txt", GetHandleOptions.creating()))
                .thenCompose(f -> opfs().getFileHandle("b.txt", GetHandleOptions.creating()))
                .thenCompose(f -> opfs().getDirectoryHandle("subdir", GetHandleOptions.creating()))
                .thenCompose(d -> opfs().list())
                .thenAccept(entries -> {
                    String names = entries.stream()
                            .map(h -> h.getName() + "(" + h.getKind() + ")")
                            .sorted()
                            .collect(Collectors.joining(", "));
                    appendLog("entries=" + names);
                    appendLog("count=" + entries.size());
                })
                .exceptionally(this::logError);
    }

    private void onResolvePath() {
        opfs().clear()
                .thenCompose(v -> opfs().root())
                .thenCompose(root -> root.getDirectoryHandle("sub", GetHandleOptions.creating())
                        .thenCompose(sub -> sub.getFileHandle("deep.txt", GetHandleOptions.creating())
                                .thenCompose(root::resolve)))
                .thenAccept(path -> appendLog("path=" + path.orElse(null)))
                .exceptionally(this::logError);
    }

    private void onRemoveEntry() {
        opfs().clear()
                .thenCompose(v -> opfs().getFileHandle("to-delete.txt", GetHandleOptions.creating()))
                .thenCompose(f -> opfs().removeEntry("to-delete.txt"))
                .thenCompose(v -> opfs().list())
                .thenAccept(entries -> appendLog("after-remove-count=" + entries.size()))
                .exceptionally(this::logError);
    }

    private void onCreateNested() {
        opfs().clear()
                .thenCompose(v -> opfs().writeFile("level1/level2/nested.txt", "nested content"))
                .thenCompose(v -> opfs().root())
                .thenCompose(
                        root -> opfs().getFileHandle("level1/level2/nested.txt").thenCompose(root::resolve))
                .thenAccept(path -> appendLog("nested-path=" + path.orElse(null)))
                .exceptionally(this::logError);
    }
}

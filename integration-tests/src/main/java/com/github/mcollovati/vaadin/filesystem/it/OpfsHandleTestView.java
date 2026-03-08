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
import com.github.mcollovati.vaadin.filesystem.PermissionMode;
import com.vaadin.flow.router.Route;

@Route("test/opfs-handles")
public class OpfsHandleTestView extends AbstractOpfsTestView {

    @Override
    void addActions() {
        add(button("is-same-entry", "Is Same Entry", this::onIsSameEntry));
        add(button("query-permission", "Query Permission", this::onQueryPermission));
        add(button("request-permission", "Request Permission", this::onRequestPermission));
    }

    private void onIsSameEntry() {
        getOpfsRoot()
                .thenCompose(root -> cleanupOpfs(root).thenApply(v -> root))
                .thenCompose(root -> root.getFileHandle("same.txt", GetHandleOptions.creating())
                        .thenCompose(f1 -> root.getFileHandle("same.txt").thenCompose(f2 -> f1.isSameEntry(f2))))
                .thenAccept(same -> appendLog("same=" + same))
                .exceptionally(this::logError);
    }

    private void onQueryPermission() {
        getOpfsRoot()
                .thenCompose(root -> cleanupOpfs(root).thenApply(v -> root))
                .thenCompose(root -> root.getFileHandle("perm.txt", GetHandleOptions.creating()))
                .thenCompose(file -> file.queryPermission(PermissionMode.READ))
                .thenAccept(state -> appendLog("permission=" + state))
                .exceptionally(this::logError);
    }

    private void onRequestPermission() {
        getOpfsRoot()
                .thenCompose(root -> cleanupOpfs(root).thenApply(v -> root))
                .thenCompose(root -> root.getFileHandle("perm.txt", GetHandleOptions.creating()))
                .thenCompose(file -> file.requestPermission(PermissionMode.READWRITE))
                .thenAccept(state -> appendLog("permission=" + state))
                .exceptionally(this::logError);
    }
}

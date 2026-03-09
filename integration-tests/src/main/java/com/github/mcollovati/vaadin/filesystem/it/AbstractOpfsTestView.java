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

import com.github.mcollovati.vaadin.filesystem.OriginPrivateFileSystem;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.html.Pre;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import java.util.concurrent.CompletionException;

abstract class AbstractOpfsTestView extends VerticalLayout {

    private final OriginPrivateFileSystem opfs;
    private final Pre log;

    AbstractOpfsTestView() {
        opfs = new OriginPrivateFileSystem(this);
        log = new Pre();
        log.setId("log");
        log.setWidthFull();
        addActions();
        add(log);
    }

    OriginPrivateFileSystem opfs() {
        return opfs;
    }

    abstract void addActions();

    void appendLog(String message) {
        getUI().ifPresent(ui -> ui.access(() -> {
            String current = log.getText();
            log.setText(current + message + "\n");
        }));
    }

    Void logError(Throwable error) {
        Throwable cause = error instanceof CompletionException ? error.getCause() : error;
        String type = cause.getClass().getSimpleName();
        appendLog(type + ": " + cause.getMessage());
        return null;
    }

    NativeButton button(String id, String text, Runnable action) {
        var btn = new NativeButton(text, e -> action.run());
        btn.setId(id);
        return btn;
    }
}

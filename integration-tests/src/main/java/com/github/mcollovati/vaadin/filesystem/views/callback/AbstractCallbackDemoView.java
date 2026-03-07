package com.github.mcollovati.vaadin.filesystem.views.callback;

import com.github.mcollovati.vaadin.filesystem.FileSystemCallbackAPI;
import com.github.mcollovati.vaadin.filesystem.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Pre;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import java.util.concurrent.CompletionException;

/**
 * Base class for callback API demo views.
 */
abstract class AbstractCallbackDemoView extends VerticalLayout {

    private final FileSystemCallbackAPI fs;
    private final Pre log;

    AbstractCallbackDemoView(String title, String description) {
        fs = new FileSystemCallbackAPI(this);
        log = new Pre();
        log.getStyle()
                .set("background", "var(--lumo-contrast-5pct)")
                .set("padding", "var(--lumo-space-m)")
                .set("min-height", "150px")
                .set("margin", "0");
        log.setWidthFull();

        setPadding(true);
        setSpacing(true);

        add(new H2(title));
        add(new Paragraph(description));
        add(MainLayout.codeBlock(codeSnippet()));
        addActions();
        add(new H2("Log"));
        add(log);
    }

    FileSystemCallbackAPI fs() {
        return fs;
    }

    abstract String codeSnippet();

    abstract void addActions();

    void appendLog(String message) {
        getUI().ifPresent(ui -> ui.access(() -> {
            String current = log.getText();
            log.setText(current + message + "\n");
        }));
    }

    void logError(Throwable error) {
        Throwable cause = error instanceof CompletionException ? error.getCause() : error;
        String type = cause.getClass().getSimpleName();
        appendLog(type + ": " + cause.getMessage());
    }

    void addContent(Component... components) {
        for (var component : components) {
            addComponentAtIndex(getComponentCount() - 2, component);
        }
    }
}

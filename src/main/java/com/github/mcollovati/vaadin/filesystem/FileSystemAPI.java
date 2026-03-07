package com.github.mcollovati.vaadin.filesystem;

import com.vaadin.flow.component.Component;
import java.io.Serializable;
import java.util.concurrent.CompletableFuture;

/**
 * Entry point for the browser's File System API.
 *
 * <p>Create an instance bound to a Vaadin {@link Component} to interact
 * with the browser's file system. The component's DOM element is used
 * as the communication bridge for JavaScript calls.
 *
 * <pre>{@code
 * var fs = new FileSystemAPI(myView);
 * fs.isSupported().thenAccept(supported -> {
 *     if (supported) {
 *         // File System API is available
 *     }
 * });
 * }</pre>
 */
public final class FileSystemAPI implements Serializable {

    private final Component component;
    private JsBridge bridge;

    /**
     * Creates a new instance bound to the given component.
     *
     * <p>The component's DOM element will be used to execute JavaScript
     * calls and to store the client-side handle registry.
     *
     * @param component the component to bind to, not {@code null}
     */
    public FileSystemAPI(Component component) {
        this.component = component;
    }

    /**
     * Checks whether the browser supports the File System API.
     *
     * @return a future that completes with {@code true} if the File System
     *         API is available in the browser
     */
    public CompletableFuture<Boolean> isSupported() {
        return getBridge()
                .executeJs("return typeof window.showOpenFilePicker === 'function';")
                .toCompletableFuture(Boolean.class);
    }

    JsBridge getBridge() {
        if (bridge == null) {
            bridge = JsBridge.getForComponent(component);
        }
        return bridge;
    }
}

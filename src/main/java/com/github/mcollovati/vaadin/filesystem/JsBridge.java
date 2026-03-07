package com.github.mcollovati.vaadin.filesystem;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.page.PendingJavaScriptResult;
import com.vaadin.flow.dom.Element;
import java.io.Serializable;
import java.util.concurrent.CompletableFuture;

/**
 * Internal bridge between Java and browser JavaScript for File System API
 * operations.
 *
 * <p>Manages a client-side handle registry stored on the host component's
 * DOM element and provides methods to execute JS operations on handles.
 * One instance exists per component, obtained via
 * {@link #getForComponent(Component)}.
 */
class JsBridge implements Serializable {

    private final Component component;
    private boolean initialized;

    private JsBridge(Component component) {
        this.component = component;
    }

    /**
     * Returns the bridge instance for the given component, creating one if
     * it does not yet exist.
     *
     * @param component the host component
     * @return the bridge for the component
     */
    static JsBridge getForComponent(Component component) {
        JsBridge bridge = ComponentUtil.getData(component, JsBridge.class);
        if (bridge == null) {
            bridge = new JsBridge(component);
            ComponentUtil.setData(component, JsBridge.class, bridge);
        }
        return bridge;
    }

    PendingJavaScriptResult executeJs(String expression, Object... params) {
        ensureInitialized();
        return element().executeJs(expression, params);
    }

    CompletableFuture<Void> executeVoidJs(String expression, Object... params) {
        return executeJs(expression, params).toCompletableFuture(String.class).thenApply(ignored -> null);
    }

    CompletableFuture<Boolean> isSameEntry(String handleId1, String handleId2) {
        return executeJs(
                        "const h1 = this.__fsApiHandles.get($0);"
                                + "const h2 = this.__fsApiHandles.get($1);"
                                + "return await h1.isSameEntry(h2);",
                        handleId1,
                        handleId2)
                .toCompletableFuture(Boolean.class);
    }

    CompletableFuture<PermissionState> queryPermission(String handleId, PermissionMode mode) {
        return executeJs(
                        "const h = this.__fsApiHandles.get($0);" + "return await h.queryPermission({mode: $1});",
                        handleId,
                        mode.getJsValue())
                .toCompletableFuture(String.class)
                .thenApply(PermissionState::fromJsValue);
    }

    CompletableFuture<PermissionState> requestPermission(String handleId, PermissionMode mode) {
        return executeJs(
                        "const h = this.__fsApiHandles.get($0);" + "return await h.requestPermission({mode: $1});",
                        handleId,
                        mode.getJsValue())
                .toCompletableFuture(String.class)
                .thenApply(PermissionState::fromJsValue);
    }

    void releaseHandle(String handleId) {
        ensureInitialized();
        element().executeJs("this.__fsApiHandles.delete($0);", handleId);
    }

    String getHandleId(FileSystemHandle handle) {
        return ((AbstractFileSystemHandle) handle).handleId();
    }

    private void ensureInitialized() {
        if (!initialized) {
            element()
                    .executeJs("this.__fsApiHandles = this.__fsApiHandles || new Map();"
                            + "this.__fsApiNextId = this.__fsApiNextId || 0;");
            initialized = true;
        }
    }

    private Element element() {
        return component.getElement();
    }
}

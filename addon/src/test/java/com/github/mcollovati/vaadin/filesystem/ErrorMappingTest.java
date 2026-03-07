package com.github.mcollovati.vaadin.filesystem;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import org.junit.jupiter.api.Test;

class ErrorMappingTest {

    @Test
    void mapsNotFoundError() {
        var ex = JsBridge.mapException(
                new RuntimeException("NotFoundError: A requested file or directory could not be found"));
        assertInstanceOf(FileSystemNotFoundException.class, ex);
        assertEquals("A requested file or directory could not be found", ex.getMessage());
    }

    @Test
    void mapsNotAllowedError() {
        var ex = JsBridge.mapException(new RuntimeException("NotAllowedError: The request is not allowed"));
        assertInstanceOf(FileSystemNotAllowedException.class, ex);
        assertEquals("The request is not allowed", ex.getMessage());
    }

    @Test
    void mapsAbortError() {
        var ex = JsBridge.mapException(new RuntimeException("AbortError: The user aborted a request"));
        assertInstanceOf(FileSystemNotAllowedException.class, ex);
        assertEquals("The user aborted a request", ex.getMessage());
    }

    @Test
    void mapsTypeMismatchError() {
        var ex = JsBridge.mapException(new RuntimeException(
                "TypeMismatchError: The path supplied exists, but was not an entry of requested type"));
        assertInstanceOf(FileSystemTypeMismatchException.class, ex);
        assertEquals("The path supplied exists, but was not an entry of requested type", ex.getMessage());
    }

    @Test
    void mapsSecurityError() {
        var ex = JsBridge.mapException(new RuntimeException("SecurityError: Permission denied"));
        assertInstanceOf(FileSystemNotAllowedException.class, ex);
        assertEquals("Permission denied", ex.getMessage());
    }

    @Test
    void unknownErrorMapsToBaseException() {
        var original = new RuntimeException("SomeOtherError: something went wrong");
        var ex = JsBridge.mapException(original);
        assertInstanceOf(FileSystemApiException.class, ex);
        assertNotInstanceOf(FileSystemNotFoundException.class, ex);
        assertNotInstanceOf(FileSystemNotAllowedException.class, ex);
        assertNotInstanceOf(FileSystemTypeMismatchException.class, ex);
        assertEquals("SomeOtherError: something went wrong", ex.getMessage());
        assertSame(original, ex.getCause());
    }

    @Test
    void errorWithNoColonMapsToBaseException() {
        var original = new RuntimeException("plain error message");
        var ex = JsBridge.mapException(original);
        assertInstanceOf(FileSystemApiException.class, ex);
        assertEquals("plain error message", ex.getMessage());
        assertSame(original, ex.getCause());
    }

    @Test
    void nullMessageMapsToBaseException() {
        var original = new RuntimeException((String) null);
        var ex = JsBridge.mapException(original);
        assertInstanceOf(FileSystemApiException.class, ex);
        assertEquals("Unknown File System API error", ex.getMessage());
        assertSame(original, ex.getCause());
    }

    @Test
    void mapErrorsUnwrapsCompletionException() throws Exception {
        CompletableFuture<String> future = new CompletableFuture<>();
        future.completeExceptionally(new CompletionException(new RuntimeException("NotFoundError: file missing")));

        CompletableFuture<String> mapped = JsBridge.mapErrors(future);

        ExecutionException thrown = assertThrows(ExecutionException.class, mapped::get);
        assertInstanceOf(FileSystemNotFoundException.class, thrown.getCause());
        assertEquals("file missing", thrown.getCause().getMessage());
    }

    @Test
    void mapErrorsHandlesDirectException() throws Exception {
        CompletableFuture<String> future = new CompletableFuture<>();
        future.completeExceptionally(new RuntimeException("AbortError: cancelled"));

        CompletableFuture<String> mapped = JsBridge.mapErrors(future);

        ExecutionException thrown = assertThrows(ExecutionException.class, mapped::get);
        assertInstanceOf(FileSystemNotAllowedException.class, thrown.getCause());
        assertEquals("cancelled", thrown.getCause().getMessage());
    }

    @Test
    void mapErrorsPassesThroughSuccessfulFuture() throws Exception {
        CompletableFuture<String> future = CompletableFuture.completedFuture("ok");

        CompletableFuture<String> mapped = JsBridge.mapErrors(future);

        assertEquals("ok", mapped.get());
    }

    private void assertNotInstanceOf(Class<?> type, Object obj) {
        assertFalse(type.isInstance(obj), "Expected not an instance of " + type.getSimpleName());
    }
}

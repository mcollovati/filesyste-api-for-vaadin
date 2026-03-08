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

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import org.junit.jupiter.api.Test;

class EnsurePermissionTest {

    @Test
    void alreadyGranted_doesNotRequestPermission() {
        var requestCalled = new AtomicBoolean(false);
        var bridge = new FakeBridge(PermissionState.GRANTED, PermissionState.GRANTED, requestCalled);
        var handle = new FileSystemFileHandle("1", "test.txt", bridge);

        PermissionState result = handle.ensurePermission(PermissionMode.READ).join();

        assertEquals(PermissionState.GRANTED, result);
        assertFalse(requestCalled.get(), "requestPermission should not be called when already granted");
    }

    @Test
    void prompt_requestsPermission() {
        var requestCalled = new AtomicBoolean(false);
        var bridge = new FakeBridge(PermissionState.PROMPT, PermissionState.GRANTED, requestCalled);
        var handle = new FileSystemFileHandle("1", "test.txt", bridge);

        PermissionState result =
                handle.ensurePermission(PermissionMode.READWRITE).join();

        assertEquals(PermissionState.GRANTED, result);
        assertTrue(requestCalled.get(), "requestPermission should be called when not granted");
    }

    @Test
    void denied_requestsPermissionAndReturnsDenied() {
        var requestCalled = new AtomicBoolean(false);
        var bridge = new FakeBridge(PermissionState.DENIED, PermissionState.DENIED, requestCalled);
        var handle = new FileSystemFileHandle("1", "test.txt", bridge);

        PermissionState result = handle.ensurePermission(PermissionMode.READ).join();

        assertEquals(PermissionState.DENIED, result);
        assertTrue(requestCalled.get(), "requestPermission should be called when denied");
    }

    private static class FakeBridge extends JsBridge {
        private final PermissionState queryResult;
        private final PermissionState requestResult;
        private final AtomicBoolean requestCalled;

        FakeBridge(PermissionState queryResult, PermissionState requestResult, AtomicBoolean requestCalled) {
            super(null);
            this.queryResult = queryResult;
            this.requestResult = requestResult;
            this.requestCalled = requestCalled;
        }

        @Override
        CompletableFuture<PermissionState> queryPermission(String handleId, PermissionMode mode) {
            return CompletableFuture.completedFuture(queryResult);
        }

        @Override
        CompletableFuture<PermissionState> requestPermission(String handleId, PermissionMode mode) {
            requestCalled.set(true);
            return CompletableFuture.completedFuture(requestResult);
        }
    }
}

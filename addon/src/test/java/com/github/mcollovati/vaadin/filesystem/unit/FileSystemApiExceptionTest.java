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
package com.github.mcollovati.vaadin.filesystem.unit;

import static org.junit.jupiter.api.Assertions.*;

import com.github.mcollovati.vaadin.filesystem.FileSystemApiException;
import com.github.mcollovati.vaadin.filesystem.FileSystemApiNotSupportedException;
import com.github.mcollovati.vaadin.filesystem.FileSystemNotAllowedException;
import com.github.mcollovati.vaadin.filesystem.FileSystemNotFoundException;
import com.github.mcollovati.vaadin.filesystem.FileSystemTypeMismatchException;
import org.junit.jupiter.api.Test;

class FileSystemApiExceptionTest {

    @Test
    void baseExceptionWithMessage() {
        var ex = new FileSystemApiException("test error");
        assertEquals("test error", ex.getMessage());
        assertNull(ex.getCause());
    }

    @Test
    void baseExceptionWithMessageAndCause() {
        var cause = new RuntimeException("root cause");
        var ex = new FileSystemApiException("test error", cause);
        assertEquals("test error", ex.getMessage());
        assertSame(cause, ex.getCause());
    }

    @Test
    void notSupportedExceptionIsSubclass() {
        var ex = new FileSystemApiNotSupportedException("not supported");
        assertInstanceOf(FileSystemApiException.class, ex);
        assertEquals("not supported", ex.getMessage());
    }

    @Test
    void notSupportedExceptionWithCause() {
        var cause = new RuntimeException("cause");
        var ex = new FileSystemApiNotSupportedException("not supported", cause);
        assertSame(cause, ex.getCause());
    }

    @Test
    void notFoundExceptionIsSubclass() {
        var ex = new FileSystemNotFoundException("not found");
        assertInstanceOf(FileSystemApiException.class, ex);
        assertEquals("not found", ex.getMessage());
    }

    @Test
    void notFoundExceptionWithCause() {
        var cause = new RuntimeException("cause");
        var ex = new FileSystemNotFoundException("not found", cause);
        assertSame(cause, ex.getCause());
    }

    @Test
    void notAllowedExceptionIsSubclass() {
        var ex = new FileSystemNotAllowedException("not allowed");
        assertInstanceOf(FileSystemApiException.class, ex);
        assertEquals("not allowed", ex.getMessage());
    }

    @Test
    void notAllowedExceptionWithCause() {
        var cause = new RuntimeException("cause");
        var ex = new FileSystemNotAllowedException("not allowed", cause);
        assertSame(cause, ex.getCause());
    }

    @Test
    void typeMismatchExceptionIsSubclass() {
        var ex = new FileSystemTypeMismatchException("type mismatch");
        assertInstanceOf(FileSystemApiException.class, ex);
        assertEquals("type mismatch", ex.getMessage());
    }

    @Test
    void typeMismatchExceptionWithCause() {
        var cause = new RuntimeException("cause");
        var ex = new FileSystemTypeMismatchException("type mismatch", cause);
        assertSame(cause, ex.getCause());
    }

    @Test
    void allExceptionsAreRuntimeExceptions() {
        assertInstanceOf(RuntimeException.class, new FileSystemApiException("a"));
        assertInstanceOf(RuntimeException.class, new FileSystemApiNotSupportedException("b"));
        assertInstanceOf(RuntimeException.class, new FileSystemNotFoundException("c"));
        assertInstanceOf(RuntimeException.class, new FileSystemNotAllowedException("d"));
        assertInstanceOf(RuntimeException.class, new FileSystemTypeMismatchException("e"));
    }
}

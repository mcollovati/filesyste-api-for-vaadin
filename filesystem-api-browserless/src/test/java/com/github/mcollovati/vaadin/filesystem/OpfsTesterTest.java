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

import com.vaadin.browserless.BrowserlessTest;
import com.vaadin.browserless.ViewPackages;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Input;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.router.Route;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class OpfsTesterTest {

    // -- Views --

    @Route("opfs")
    public static class OpfsView extends Div {
        final OriginPrivateFileSystem opfs = new OriginPrivateFileSystem(this);
        final Input fileName = new Input();
        final Input editor = new Input();
        final Span readOutput = new Span();
        final NativeButton writeBtn =
                new NativeButton("Write", e -> opfs.writeFile(fileName.getValue(), editor.getValue()));
        final NativeButton readBtn = new NativeButton("Read", e -> opfs.readFile(fileName.getValue())
                .thenAccept(data -> readOutput.setText(new String(data.getContent()))));

        public OpfsView() {
            add(fileName, editor, writeBtn, readBtn, readOutput);
        }
    }

    @Route("opfs-manage")
    public static class OpfsManageView extends Div {
        final OriginPrivateFileSystem opfs = new OriginPrivateFileSystem(this);
        final Input targetName = new Input();
        final Span result = new Span();
        final NativeButton createDirBtn = new NativeButton(
                "Create Dir", e -> opfs.getDirectoryHandle(targetName.getValue(), GetHandleOptions.creating())
                        .thenAccept(dir -> result.setText("created:" + dir.getName())));
        final NativeButton removeBtn = new NativeButton(
                "Remove", e -> opfs.removeEntry(targetName.getValue()).thenAccept(v -> result.setText("removed")));
        final NativeButton removeRecursiveBtn = new NativeButton(
                "Remove Recursive", e -> opfs.removeEntry(targetName.getValue(), RemoveEntryOptions.recursively())
                        .thenAccept(v -> result.setText("removed")));

        public OpfsManageView() {
            add(targetName, createDirBtn, removeBtn, removeRecursiveBtn, result);
        }
    }

    @Route("writable")
    public static class WritableView extends Div {
        final OriginPrivateFileSystem opfs = new OriginPrivateFileSystem(this);
        final Span status = new Span();
        final NativeButton seekWriteBtn =
                new NativeButton("Seek Write", e -> opfs.getFileHandle("test.txt", GetHandleOptions.creating())
                        .thenCompose(FileSystemFileHandle::createWritable)
                        .thenCompose(w -> w.write("Hello, world!")
                                .thenCompose(v -> w.seek(7))
                                .thenCompose(v -> w.write("Vaadin!"))
                                .thenCompose(v -> w.close()))
                        .thenAccept(v -> status.setText("done")));
        final NativeButton truncateBtn = new NativeButton("Truncate", e -> opfs.getFileHandle(
                        "test.txt", GetHandleOptions.creating())
                .thenCompose(FileSystemFileHandle::createWritable)
                .thenCompose(w ->
                        w.write("Hello, world!").thenCompose(v -> w.truncate(5)).thenCompose(v -> w.close()))
                .thenAccept(v -> status.setText("done")));

        public WritableView() {
            add(seekWriteBtn, truncateBtn, status);
        }
    }

    @Route("keep-existing")
    public static class KeepExistingView extends Div {
        final OriginPrivateFileSystem opfs = new OriginPrivateFileSystem(this);
        final Span status = new Span();
        final NativeButton patchBtn = new NativeButton("Patch", e -> opfs.getFileHandle("existing.txt")
                .thenCompose(handle -> handle.createWritable(WritableOptions.keepingExistingData()))
                .thenCompose(w -> w.seek(4).thenCompose(v -> w.write("new")).thenCompose(v -> w.close()))
                .thenAccept(v -> status.setText("done")));

        public KeepExistingView() {
            add(patchBtn, status);
        }
    }

    @Route("permissions")
    public static class PermissionsView extends Div {
        final OriginPrivateFileSystem opfs = new OriginPrivateFileSystem(this);
        final Span permStatus = new Span();
        final NativeButton checkBtn = new NativeButton("Check", e -> opfs.getFileHandle("a.txt")
                .thenCompose(handle -> handle.queryPermission(PermissionMode.READ))
                .thenAccept(state -> permStatus.setText(state.getJsValue())));

        public PermissionsView() {
            add(checkBtn, permStatus);
        }
    }

    @Route("same-entry")
    public static class SameEntryView extends Div {
        final OriginPrivateFileSystem opfs = new OriginPrivateFileSystem(this);
        final Span result = new Span();
        final NativeButton checkBtn = new NativeButton("Check", e -> opfs.getFileHandle("a.txt")
                .thenCompose(h1 -> opfs.getFileHandle("a.txt").thenCompose(h2 -> h1.isSameEntry(h2)))
                .thenAccept(same -> result.setText(String.valueOf(same))));

        public SameEntryView() {
            add(checkBtn, result);
        }
    }

    @Route("resolve")
    public static class ResolveView extends Div {
        final OriginPrivateFileSystem opfs = new OriginPrivateFileSystem(this);
        final Span path = new Span();
        final NativeButton resolveBtn = new NativeButton("Resolve", e -> opfs.root()
                .thenCompose(root -> root.getDirectoryHandle("sub")
                        .thenCompose(sub -> sub.getFileHandle("deep.txt"))
                        .thenCompose(file -> root.resolve(file)))
                .thenAccept(opt -> path.setText(String.join("/", opt.orElseThrow()))));

        public ResolveView() {
            add(resolveBtn, path);
        }
    }

    // -- Test classes --

    @Nested
    @ViewPackages(classes = OpfsView.class)
    class OpfsTests extends BrowserlessTest {

        @Test
        void write_and_read_round_trip() {
            OpfsView view = navigate(OpfsView.class);
            FileSystemTester tester = FileSystemTester.forComponent(view).install();

            view.fileName.setValue("data.txt");
            view.editor.setValue("opfs content");
            test(view.writeBtn).click();

            assertEquals("opfs content", tester.fileSystem().file("data.txt").contentAsString());

            test(view.readBtn).click();

            assertEquals("opfs content", view.readOutput.getText());
        }

        @Test
        void write_and_read_nested_path() {
            OpfsView view = navigate(OpfsView.class);
            FileSystemTester tester = FileSystemTester.forComponent(view).install();

            view.fileName.setValue("a/b/nested.txt");
            view.editor.setValue("deep content");
            test(view.writeBtn).click();

            assertTrue(tester.fileSystem().exists("a/b/nested.txt"));
            assertEquals(
                    "deep content", tester.fileSystem().file("a/b/nested.txt").contentAsString());

            test(view.readBtn).click();

            assertEquals("deep content", view.readOutput.getText());
        }

        @Test
        void read_nonexistent_file_does_not_crash() {
            OpfsView view = navigate(OpfsView.class);
            FileSystemTester.forComponent(view).install();

            view.fileName.setValue("missing.txt");
            test(view.readBtn).click();

            assertEquals("", view.readOutput.getText());
        }
    }

    @Nested
    @ViewPackages(classes = OpfsManageView.class)
    class OpfsManageTests extends BrowserlessTest {

        @Test
        void click_create_dir_creates_directory() {
            OpfsManageView view = navigate(OpfsManageView.class);
            FileSystemTester tester = FileSystemTester.forComponent(view).install();

            view.targetName.setValue("newdir");
            test(view.createDirBtn).click();

            assertTrue(tester.fileSystem().exists("newdir"));
            assertEquals("created:newdir", view.result.getText());
        }

        @Test
        void click_create_nested_dir_creates_intermediate_directories() {
            OpfsManageView view = navigate(OpfsManageView.class);
            FileSystemTester tester = FileSystemTester.forComponent(view).install();

            view.targetName.setValue("a/b/deep");
            test(view.createDirBtn).click();

            assertTrue(tester.fileSystem().exists("a/b/deep"));
            assertEquals("created:deep", view.result.getText());
        }

        @Test
        void click_remove_deletes_file() {
            OpfsManageView view = navigate(OpfsManageView.class);
            FileSystemTester tester = FileSystemTester.forComponent(view)
                    .withFile("temp.txt", "temp")
                    .install();

            view.targetName.setValue("temp.txt");
            test(view.removeBtn).click();

            assertFalse(tester.fileSystem().exists("temp.txt"));
            assertEquals("removed", view.result.getText());
        }

        @Test
        void click_remove_non_empty_dir_without_recursive_fails() {
            OpfsManageView view = navigate(OpfsManageView.class);
            FileSystemTester tester = FileSystemTester.forComponent(view)
                    .withDirectory("sub", b -> b.withFile("child.txt", "data"))
                    .install();

            view.targetName.setValue("sub");
            test(view.removeBtn).click();

            assertTrue(tester.fileSystem().exists("sub"));
            assertNotEquals("removed", view.result.getText());
        }

        @Test
        void click_remove_recursive_deletes_non_empty_dir() {
            OpfsManageView view = navigate(OpfsManageView.class);
            FileSystemTester tester = FileSystemTester.forComponent(view)
                    .withDirectory("sub", b -> b.withFile("child.txt", "data"))
                    .install();

            view.targetName.setValue("sub");
            test(view.removeRecursiveBtn).click();

            assertFalse(tester.fileSystem().exists("sub"));
            assertEquals("removed", view.result.getText());
        }
    }

    @Nested
    @ViewPackages(classes = WritableView.class)
    class WritableTests extends BrowserlessTest {

        @Test
        void click_seek_write_produces_correct_content() {
            WritableView view = navigate(WritableView.class);
            FileSystemTester tester = FileSystemTester.forComponent(view).install();

            test(view.seekWriteBtn).click();

            assertEquals("done", view.status.getText());
            assertEquals("Hello, Vaadin!", tester.fileSystem().file("test.txt").contentAsString());
        }

        @Test
        void click_truncate_produces_correct_content() {
            WritableView view = navigate(WritableView.class);
            FileSystemTester tester = FileSystemTester.forComponent(view).install();

            test(view.truncateBtn).click();

            assertEquals("done", view.status.getText());
            assertEquals("Hello", tester.fileSystem().file("test.txt").contentAsString());
        }
    }

    @Nested
    @ViewPackages(classes = KeepExistingView.class)
    class KeepExistingTests extends BrowserlessTest {

        @Test
        void click_patch_overwrites_at_position() {
            KeepExistingView view = navigate(KeepExistingView.class);
            FileSystemTester tester = FileSystemTester.forComponent(view)
                    .withFile("existing.txt", "Old content")
                    .install();

            test(view.patchBtn).click();

            assertEquals("done", view.status.getText());
            assertEquals("Old newtent", tester.fileSystem().file("existing.txt").contentAsString());
        }
    }

    @Nested
    @ViewPackages(classes = PermissionsView.class)
    class PermissionTests extends BrowserlessTest {

        @Test
        void click_check_shows_denied_permission() {
            PermissionsView view = navigate(PermissionsView.class);
            FileSystemTester.forComponent(view)
                    .withFile("a.txt", "data")
                    .withPermissionState(PermissionState.DENIED)
                    .install();

            test(view.checkBtn).click();

            assertEquals("denied", view.permStatus.getText());
        }
    }

    @Nested
    @ViewPackages(classes = SameEntryView.class)
    class SameEntryTests extends BrowserlessTest {

        @Test
        void click_check_detects_same_entry() {
            SameEntryView view = navigate(SameEntryView.class);
            FileSystemTester.forComponent(view).withFile("a.txt", "data").install();

            test(view.checkBtn).click();

            assertEquals("true", view.result.getText());
        }
    }

    @Nested
    @ViewPackages(classes = ResolveView.class)
    class ResolveTests extends BrowserlessTest {

        @Test
        void click_resolve_shows_path() {
            ResolveView view = navigate(ResolveView.class);
            FileSystemTester.forComponent(view)
                    .withDirectory("sub", b -> b.withFile("deep.txt", "data"))
                    .install();

            test(view.resolveBtn).click();

            assertEquals("sub/deep.txt", view.path.getText());
        }
    }
}

package com.github.mcollovati.vaadin.filesystem;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.server.streams.DownloadHandler;
import com.vaadin.flow.server.streams.UploadHandler;
import java.io.Serializable;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * High-level, task-oriented API for the browser's File System API.
 *
 * <p>Provides convenient methods that combine picker dialogs with
 * follow-up operations (read, write, stream) so that common workflows
 * can be expressed in a single call.
 *
 * <pre>{@code
 * var fs = new FileSystemAPI(myView);
 *
 * // Pick and read a file in one step
 * fs.openFile().thenAccept(fileData ->
 *     log(fileData.getName() + ": " + fileData.getSize() + " bytes"));
 *
 * // Pick and write text in one step
 * fs.saveFile("Hello, world!");
 * }</pre>
 *
 * <p>For direct access to the low-level picker methods and handle
 * operations, use {@link FileSystemAPIFull}.
 *
 * @see FileSystemAPIFull
 * @see FileSystemCallbackAPI
 */
public final class FileSystemAPI implements Serializable {

    private final FileSystemAPIFull full;

    /**
     * Creates a new instance bound to the given component.
     *
     * @param component the component to bind to, not {@code null}
     */
    public FileSystemAPI(Component component) {
        this.full = new FileSystemAPIFull(component);
    }

    /**
     * Returns the underlying low-level API for advanced operations.
     *
     * @return the full API instance
     */
    public FileSystemAPIFull full() {
        return full;
    }

    /**
     * Checks whether the browser supports the File System API.
     *
     * @return a future that completes with {@code true} if the File System
     *         API is available in the browser
     */
    public CompletableFuture<Boolean> isSupported() {
        return full.isSupported();
    }

    // -- Open & Read --

    /**
     * Opens a file picker and reads the selected file's content.
     *
     * @return a future that completes with the file data
     */
    public CompletableFuture<FileData> openFile() {
        return openFile(OpenFilePickerOptions.builder().build());
    }

    /**
     * Opens a file picker with the given options and reads the selected
     * file's content.
     *
     * @param options the picker options
     * @return a future that completes with the file data
     */
    public CompletableFuture<FileData> openFile(OpenFilePickerOptions options) {
        return full.showOpenFilePicker(options)
                .thenCompose(handles -> handles.get(0).getFile());
    }

    /**
     * Opens a file picker allowing multiple selection and reads all
     * selected files' content.
     *
     * @return a future that completes with the list of file data
     */
    public CompletableFuture<List<FileData>> openFiles() {
        return openFiles(OpenFilePickerOptions.builder().multiple(true).build());
    }

    /**
     * Opens a file picker with the given options and reads all selected
     * files' content.
     *
     * <p>The options should have {@code multiple(true)} set to allow
     * selecting more than one file.
     *
     * @param options the picker options
     * @return a future that completes with the list of file data
     */
    public CompletableFuture<List<FileData>> openFiles(OpenFilePickerOptions options) {
        return full.showOpenFilePicker(options).thenCompose(handles -> {
            List<CompletableFuture<FileData>> futures =
                    handles.stream().map(FileSystemFileHandle::getFile).toList();
            return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new))
                    .thenApply(
                            v -> futures.stream().map(CompletableFuture::join).toList());
        });
    }

    // -- Save & Write --

    /**
     * Opens a save file picker and writes the given text to the
     * selected file.
     *
     * @param text the text to write
     * @return a future that completes when the write is done
     */
    public CompletableFuture<Void> saveFile(String text) {
        return saveFile(SaveFilePickerOptions.builder().build(), text);
    }

    /**
     * Opens a save file picker with the given options and writes the
     * given text to the selected file.
     *
     * @param options the picker options
     * @param text    the text to write
     * @return a future that completes when the write is done
     */
    public CompletableFuture<Void> saveFile(SaveFilePickerOptions options, String text) {
        return full.showSaveFilePicker(options).thenCompose(handle -> handle.writeString(text));
    }

    /**
     * Opens a save file picker and writes the given bytes to the
     * selected file.
     *
     * @param data the bytes to write
     * @return a future that completes when the write is done
     */
    public CompletableFuture<Void> saveFile(byte[] data) {
        return saveFile(SaveFilePickerOptions.builder().build(), data);
    }

    /**
     * Opens a save file picker with the given options and writes the
     * given bytes to the selected file.
     *
     * @param options the picker options
     * @param data    the bytes to write
     * @return a future that completes when the write is done
     */
    public CompletableFuture<Void> saveFile(SaveFilePickerOptions options, byte[] data) {
        return full.showSaveFilePicker(options).thenCompose(handle -> handle.writeBytes(data));
    }

    // -- Streaming transfers --

    /**
     * Opens a file picker and uploads the selected file to the server
     * using the given handler.
     *
     * @param handler the upload handler to receive the file content
     * @return a future that completes when the upload is done
     */
    public CompletableFuture<Void> openFile(UploadHandler handler) {
        return openFile(OpenFilePickerOptions.builder().build(), handler);
    }

    /**
     * Opens a file picker with the given options and uploads the selected
     * file to the server using the given handler.
     *
     * @param options the picker options
     * @param handler the upload handler to receive the file content
     * @return a future that completes when the upload is done
     */
    public CompletableFuture<Void> openFile(OpenFilePickerOptions options, UploadHandler handler) {
        return full.showOpenFilePicker(options)
                .thenCompose(handles -> handles.get(0).uploadTo(handler));
    }

    /**
     * Opens a save file picker and downloads content from the server
     * into the selected file using the given handler.
     *
     * @param handler the download handler providing the content
     * @return a future that completes when the download is done
     */
    public CompletableFuture<Void> saveFile(DownloadHandler handler) {
        return saveFile(SaveFilePickerOptions.builder().build(), handler);
    }

    /**
     * Opens a save file picker with the given options and downloads
     * content from the server into the selected file using the given
     * handler.
     *
     * @param options the picker options
     * @param handler the download handler providing the content
     * @return a future that completes when the download is done
     */
    public CompletableFuture<Void> saveFile(SaveFilePickerOptions options, DownloadHandler handler) {
        return full.showSaveFilePicker(options).thenCompose(handle -> handle.downloadFrom(handler));
    }

    // -- Directory --

    /**
     * Opens a directory picker and returns the selected directory handle.
     *
     * @return a future that completes with the directory handle
     */
    public CompletableFuture<FileSystemDirectoryHandle> openDirectory() {
        return openDirectory(DirectoryPickerOptions.builder().build());
    }

    /**
     * Opens a directory picker with the given options and returns the
     * selected directory handle.
     *
     * @param options the picker options
     * @return a future that completes with the directory handle
     */
    public CompletableFuture<FileSystemDirectoryHandle> openDirectory(DirectoryPickerOptions options) {
        return full.showDirectoryPicker(options);
    }

    /**
     * Opens a directory picker and lists all entries in the selected
     * directory.
     *
     * @return a future that completes with the list of handles
     */
    public CompletableFuture<List<FileSystemHandle>> listDirectory() {
        return listDirectory(DirectoryPickerOptions.builder().build());
    }

    /**
     * Opens a directory picker with the given options and lists all
     * entries in the selected directory.
     *
     * @param options the picker options
     * @return a future that completes with the list of handles
     */
    public CompletableFuture<List<FileSystemHandle>> listDirectory(DirectoryPickerOptions options) {
        return full.showDirectoryPicker(options).thenCompose(FileSystemDirectoryHandle::entries);
    }
}

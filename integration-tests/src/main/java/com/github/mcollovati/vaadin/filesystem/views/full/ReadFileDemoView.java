package com.github.mcollovati.vaadin.filesystem.views.full;

import com.github.mcollovati.vaadin.filesystem.FileData;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.router.Route;
import java.nio.charset.StandardCharsets;

/**
 * Demo view showcasing file reading with the low-level API.
 */
@Route("full/read-file")
public class ReadFileDemoView extends AbstractDemoView {

    public ReadFileDemoView() {
        super(
                "Read File (Full API)",
                "Open a file with showOpenFilePicker(), then read its content with "
                        + "getFile(). The file data is transferred to the server as base64 "
                        + "and decoded into a FileData object containing name, size, MIME type, "
                        + "and byte content.");
    }

    @Override
    String codeSnippet() {
        return """
                fs.showOpenFilePicker().thenAccept(handles -> {
                    var handle = handles.get(0);
                    handle.getFile().thenAccept(fileData -> {
                        String name = fileData.getName();
                        long size = fileData.getSize();
                        String type = fileData.getType();
                        byte[] content = fileData.getContent();
                        InputStream is = fileData.getContentAsInputStream();
                    });
                });""";
    }

    @Override
    void addActions() {
        add(new Button("Read File Content", e -> onReadFile()));
    }

    private void onReadFile() {
        fs().showOpenFilePicker()
                .thenAccept(handles -> {
                    if (handles.isEmpty()) return;
                    var handle = handles.get(0);
                    handle.getFile().thenAccept(this::logFileData).exceptionally(this::logError);
                })
                .exceptionally(this::logError);
    }

    private void logFileData(FileData fileData) {
        var sb = new StringBuilder("Read File: ")
                .append(fileData.getName())
                .append(" (")
                .append(fileData.getType())
                .append(", ")
                .append(fileData.getSize())
                .append(" bytes)");
        if (fileData.getType().startsWith("text/") && fileData.getSize() > 0) {
            var text = new String(fileData.getContent(), StandardCharsets.UTF_8);
            var preview = text.length() > 200 ? text.substring(0, 200) + "..." : text;
            sb.append("\n  Content: ").append(preview);
        }
        appendLog(sb.toString());
    }
}

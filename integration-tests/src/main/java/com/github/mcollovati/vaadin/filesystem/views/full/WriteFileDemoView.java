package com.github.mcollovati.vaadin.filesystem.views.full;

import com.github.mcollovati.vaadin.filesystem.FileTypeFilter;
import com.github.mcollovati.vaadin.filesystem.SaveFilePickerOptions;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.router.Route;
import java.util.List;
import java.util.Map;

/**
 * Demo view showcasing file writing.
 */
@Route("full/write-file")
public class WriteFileDemoView extends AbstractDemoView {

    public WriteFileDemoView() {
        super(
                "Write File",
                "Write text or bytes to a file using saveFile(). The file picker and "
                        + "write are combined in a single call. Configure the suggested name "
                        + "and file type filters through SaveFilePickerOptions.");
    }

    @Override
    String codeSnippet() {
        return """
                var opts = SaveFilePickerOptions.builder()
                        .suggestedName("hello.txt")
                        .types(List.of(new FileTypeFilter("Text files",
                            Map.of("text/plain", List.of(".txt")))))
                        .build();

                fs.saveFile(opts, "Hello from Vaadin!");""";
    }

    @Override
    void addActions() {
        add(new Button("Write to File", e -> onWriteFile()));
    }

    private void onWriteFile() {
        var options = SaveFilePickerOptions.builder()
                .suggestedName("hello.txt")
                .types(List.of(new FileTypeFilter("Text files", Map.of("text/plain", List.of(".txt")))))
                .build();
        fs().saveFile(options, "Hello from Vaadin File System API!")
                .thenRun(() -> appendLog("Write File: wrote content to file"))
                .exceptionally(this::logError);
    }
}

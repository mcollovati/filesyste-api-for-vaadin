package com.github.mcollovati.vaadin.filesystem.views.full;

import com.github.mcollovati.vaadin.filesystem.FileData;
import com.github.mcollovati.vaadin.filesystem.FileTypeFilter;
import com.github.mcollovati.vaadin.filesystem.OpenFilePickerOptions;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;
import java.util.List;
import java.util.Map;

/**
 * Demo view showcasing file picker operations.
 */
@Route("full/file-pickers")
public class FilePickerDemoView extends AbstractDemoView {

    public FilePickerDemoView() {
        super(
                "File Pickers",
                "Open file picker dialogs using openFile() and openFiles(). Configure "
                        + "options like multiple selection and file type filters through "
                        + "builder-style option classes.");

        var status = new Span("Checking...");
        fs().isSupported().thenAccept(supported -> getUI().ifPresent(ui -> ui.access(() -> {
            if (supported) {
                status.setText("File System API is supported");
                status.getStyle().set("color", "var(--lumo-success-color)");
            } else {
                status.setText("File System API is NOT supported in this browser");
                status.getStyle().set("color", "var(--lumo-error-color)");
            }
        })));
        addContent(status);
    }

    @Override
    String codeSnippet() {
        return """
                // Single file
                fs.openFile()
                    .thenAccept(fileData -> { ... });

                // Multiple files
                var opts = OpenFilePickerOptions.builder()
                        .multiple(true).build();
                fs.openFiles(opts)
                    .thenAccept(allFileData -> { ... });

                // Filtered by type
                var opts = OpenFilePickerOptions.builder()
                        .types(List.of(new FileTypeFilter("Images",
                            Map.of("image/*", List.of(".png", ".jpg")))))
                        .build();
                fs.openFile(opts)
                    .thenAccept(fileData -> { ... });""";
    }

    @Override
    void addActions() {
        var openFile = new Button("Open File", e -> onOpenFile());
        var openMultiple = new Button("Open Multiple", e -> onOpenMultiple());
        var openImages = new Button("Open Images", e -> onOpenImages());
        add(new HorizontalLayout(openFile, openMultiple, openImages));
    }

    private void onOpenFile() {
        fs().openFile().thenAccept(data -> logFileData("Open File", data)).exceptionally(this::logError);
    }

    private void onOpenMultiple() {
        var options = OpenFilePickerOptions.builder().multiple(true).build();
        fs().openFiles(options)
                .thenAccept(allData -> {
                    for (var data : allData) {
                        logFileData("Open Multiple", data);
                    }
                })
                .exceptionally(this::logError);
    }

    private void onOpenImages() {
        var options = OpenFilePickerOptions.builder()
                .types(List.of(new FileTypeFilter("Images", Map.of("image/*", List.of(".png", ".jpg", ".gif")))))
                .build();
        fs().openFile(options)
                .thenAccept(data -> logFileData("Open Images", data))
                .exceptionally(this::logError);
    }

    private void logFileData(String action, FileData data) {
        appendLog(action + ": " + data.getName() + " (" + data.getType() + ", " + data.getSize() + " bytes)");
    }
}

package com.github.mcollovati.vaadin.filesystem.views;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.Layout;

/**
 * Main application layout with a side navigation menu for the
 * File System API demo views.
 */
@Layout
public class MainLayout extends AppLayout {

    public MainLayout() {
        var toggle = new DrawerToggle();
        var title = new H1("File System API");
        title.getStyle().set("font-size", "var(--lumo-font-size-l)").set("margin", "0");

        addToNavbar(toggle, title);
        addToDrawer(new Scroller(createSideNav()));
    }

    private SideNav createSideNav() {
        var nav = new SideNav();

        var highLevel = new SideNavItem("High-level API");
        highLevel.addItem(new SideNavItem("File Pickers", FilePickerDemoView.class));
        highLevel.addItem(new SideNavItem("Read File", ReadFileDemoView.class));
        highLevel.addItem(new SideNavItem("Write File", WriteFileDemoView.class));
        highLevel.addItem(new SideNavItem("Directory", DirectoryDemoView.class));
        highLevel.addItem(new SideNavItem("Streaming", StreamingDemoView.class));
        nav.addItem(highLevel);

        var callback = new SideNavItem("Callback API");
        callback.addItem(new SideNavItem(
                "File Pickers", com.github.mcollovati.vaadin.filesystem.views.callback.FilePickerDemoView.class));
        callback.addItem(new SideNavItem(
                "Read File", com.github.mcollovati.vaadin.filesystem.views.callback.ReadFileDemoView.class));
        callback.addItem(new SideNavItem(
                "Write File", com.github.mcollovati.vaadin.filesystem.views.callback.WriteFileDemoView.class));
        callback.addItem(new SideNavItem(
                "Directory", com.github.mcollovati.vaadin.filesystem.views.callback.DirectoryDemoView.class));
        callback.addItem(new SideNavItem(
                "Streaming", com.github.mcollovati.vaadin.filesystem.views.callback.StreamingDemoView.class));
        nav.addItem(callback);

        var full = new SideNavItem("Full API");
        full.addItem(new SideNavItem(
                "File Pickers", com.github.mcollovati.vaadin.filesystem.views.full.FilePickerDemoView.class));
        full.addItem(new SideNavItem(
                "Read File", com.github.mcollovati.vaadin.filesystem.views.full.ReadFileDemoView.class));
        full.addItem(new SideNavItem(
                "Write File", com.github.mcollovati.vaadin.filesystem.views.full.WriteFileDemoView.class));
        full.addItem(new SideNavItem(
                "Directory", com.github.mcollovati.vaadin.filesystem.views.full.DirectoryDemoView.class));
        full.addItem(new SideNavItem(
                "Streaming", com.github.mcollovati.vaadin.filesystem.views.full.StreamingDemoView.class));
        nav.addItem(full);

        return nav;
    }

    /**
     * Helper to create a styled code block for displaying source snippets.
     *
     * @param code the source code text
     * @return a styled {@link Span} wrapping the code
     */
    public static Span codeBlock(String code) {
        var span = new Span(code);
        span.getElement()
                .setAttribute(
                        "style",
                        "display:block; white-space:pre; font-family:monospace;"
                                + " font-size:var(--lumo-font-size-s); background:var(--lumo-contrast-5pct);"
                                + " padding:var(--lumo-space-m); border-radius:var(--lumo-border-radius-m);"
                                + " overflow-x:auto;");
        return span;
    }
}

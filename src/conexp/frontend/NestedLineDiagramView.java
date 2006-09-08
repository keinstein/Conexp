/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend;

import conexp.frontend.latticeeditor.NestedLineDiagramCanvas;
import conexp.frontend.latticeeditor.NestedLineDiagramDrawing;

import javax.swing.JComponent;
import javax.swing.JScrollPane;

public class NestedLineDiagramView extends JScrollPane implements View, OptionPaneProvider {
    private NestedLineDiagramCanvas canvas;
    private NestedLineDiagramDrawing nestedLineDiagramDrawing = new NestedLineDiagramDrawing();

    public NestedLineDiagramView() {
        super();
        this.canvas = new NestedLineDiagramCanvas(nestedLineDiagramDrawing);
        add(canvas);
        setViewportView(canvas);
    }

    public NestedLineDiagramDrawing getDrawing() {
        return nestedLineDiagramDrawing;
    }

    public void initialUpdate() {
        canvas.setFitToSize(true);
    }

    public JComponent getViewOptions() {
        return canvas.getViewOptions();
    }


}

/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend;

import conexp.frontend.latticeeditor.NestedLineDiagramCanvas;
import conexp.frontend.latticeeditor.NestedLineDiagramDrawing;

import javax.swing.*;

public class NestedLineDiagramView extends JScrollPane implements View, OptionPaneProvider {
    NestedLineDiagramCanvas canvas;
    NestedLineDiagramDrawing nestedLineDiagramDrawing = new NestedLineDiagramDrawing();

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

/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package canvas.util;

import util.gui.ActionWithKey;

import java.awt.event.ActionEvent;


public class ToolAction extends ActionWithKey {
    canvas.Tool tool;
    private canvas.FigureDrawingCanvas drawing;

    public ToolAction(canvas.FigureDrawingCanvas drawing, String key, String name, canvas.Tool tool) {
        super(key, name);
        this.drawing = drawing;
        this.tool = tool;
    }

    public canvas.Tool getTool() {
        return tool;
    }

    public void actionPerformed(ActionEvent e) {
        drawing.setActiveTool(tool);
    }
}

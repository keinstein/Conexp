/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package canvas.util;

import util.gui.ActionWithKey;

import java.awt.event.ActionEvent;

import canvas.Tool;
import canvas.FigureDrawingCanvas;


public class ToolAction extends ActionWithKey {
    private Tool tool;
    private FigureDrawingCanvas drawing;

    public ToolAction(FigureDrawingCanvas drawing, String key, String name, Tool tool) {
        super(key, name);
        this.drawing = drawing;
        this.tool = tool;
    }

    public Tool getTool() {
        return tool;
    }

    public void actionPerformed(ActionEvent e) {
        drawing.setActiveTool(tool);
    }
}

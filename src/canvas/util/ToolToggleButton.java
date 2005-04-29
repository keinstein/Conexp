/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package canvas.util;

import canvas.ToolEvent;
import canvas.DefaultToolListener;
import canvas.Tool;

import javax.swing.*;


public class ToolToggleButton extends JToggleButton {
    public ToolToggleButton(ToolAction toolAction) {
        super(toolAction);
        Tool tool = toolAction.getTool();
        setEnabled(tool.isEnabled());
        setSelected(tool.isActive());
        tool.addToolListener(new DefaultToolListener() {
            protected void onToolDeactivated(ToolEvent toolEvent) {
                ToolToggleButton.this.setSelected(false);
            }

            protected void onToolActivated(ToolEvent toolEvent) {
                ToolToggleButton.this.setSelected(true);
            }

            protected void onToolEnabled(ToolEvent toolEvent) {
                ToolToggleButton.this.setEnabled(true);
            }

            protected void onToolDisabled(ToolEvent toolEvent) {
                ToolToggleButton.this.setEnabled(false);
            }
        });

    }
}

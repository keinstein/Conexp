package canvas.util;

import canvas.ToolEvent;

import javax.swing.*;

/*
 * User: Serhiy Yevtushenko
 * Date: Oct 15, 2002
 * Time: 6:31:49 PM
 */
public class ToolToggleButton extends JToggleButton{
    public ToolToggleButton(ToolAction toolAction) {
        super(toolAction);
        canvas.Tool tool = toolAction.getTool();
        setEnabled(tool.isEnabled());
        setSelected(tool.isActive());
        tool.addToolListener(new canvas.DefaultToolListener(){
            protected void onToolDeactivated(canvas.ToolEvent toolEvent) {
                ToolToggleButton.this.setSelected(false);
            }

            protected void onToolActivated(canvas.ToolEvent toolEvent) {
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

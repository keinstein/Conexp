package canvas;

import util.Assert;

/*
 * User: Serhiy Yevtushenko
 * Date: Oct 14, 2002
 * Time: 10:23:11 PM
 */
public class DefaultToolListener implements ToolListener{
    public void toolStateChanged(ToolEvent toolEvent) {
        switch(toolEvent.getType()){
            case ToolEvent.TOOL_ACTIVATED:
                onToolActivated(toolEvent);
                break;
            case ToolEvent.TOOL_DEACTIVATED:
                onToolDeactivated(toolEvent);
                break;
            case ToolEvent.TOOL_ENABLED:
                onToolEnabled(toolEvent);
                break;

            case ToolEvent.TOOL_DISABLED:
                onToolDisabled(toolEvent);
                break;
            default:
                Assert.isTrue(false);
        }
    }

    protected void onToolEnabled(ToolEvent toolEvent) {
    }

    protected void onToolDisabled(ToolEvent toolEvent) {

    }

    protected void onToolDeactivated(ToolEvent toolEvent) {
    }

    protected void onToolActivated(ToolEvent toolEvent) {
    }
}

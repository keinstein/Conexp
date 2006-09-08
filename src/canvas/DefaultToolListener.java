/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package canvas;

import util.Assert;


public class DefaultToolListener implements ToolListener {
    public void toolStateChanged(ToolEvent toolEvent) {
        switch (toolEvent.getType()) {
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

/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package canvas;

import java.util.EventObject;

public class ToolEvent extends EventObject {

    public ToolEvent(Object source, int type) {
        super(source);
        this.type = type;
    }

    final int type;

    public int getType() {
        return type;
    }

    public static final int TOOL_ACTIVATED = 1;
    public static final int TOOL_DEACTIVATED = 2;
    public static final int TOOL_ENABLED = 3;
    public static final int TOOL_DISABLED = 4;

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ToolEvent)) {
            return false;
        }

        final ToolEvent that = (ToolEvent) obj;

        if (this.getSource() != that.getSource()) {
            return false;
        }
        if (type != that.type) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return type;
    }

    public static ToolEvent makeToolActivatedEvent(Tool tool) {
        return new ToolEvent(tool, TOOL_ACTIVATED);
    }

    public static ToolEvent makeToolDeactivateEvent(Tool tool) {
        return new ToolEvent(tool, TOOL_DEACTIVATED);
    }

    public static ToolEvent makeToolEnabledEvent(Tool tool) {
        return new ToolEvent(tool, TOOL_ENABLED);
    }

    public static ToolEvent makeToolDisabledEvent(Tool tool) {
        return new ToolEvent(tool, TOOL_DISABLED);
    }
}

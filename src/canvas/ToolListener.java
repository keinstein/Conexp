/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package canvas;

import java.util.EventListener;


public interface ToolListener extends EventListener {
    void toolStateChanged(ToolEvent toolEvent);
}

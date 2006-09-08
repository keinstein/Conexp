/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package canvas;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


public interface Tool extends MouseListener, MouseMotionListener, KeyListener {
    void addToolListener(ToolListener listener);

    void removeToolListener(ToolListener listener);

    boolean isActive();

    void activate();

    void deactivate();

    void setActive(boolean value);

    boolean isEnabled();

    void setEnabled(boolean value);

}

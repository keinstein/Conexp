/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package canvas;

import javax.swing.event.EventListenerList;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class DefaultTool extends MouseAdapter implements Tool {
    EventListenerList listenersList = null;

    boolean active = false;

    protected EventListenerList getListenersList() {
        if (null == listenersList) {
            listenersList = new EventListenerList();
        }
        return listenersList;
    }

    public void addToolListener(ToolListener listener) {
        getListenersList().add(ToolListener.class, listener);
    }

    public void removeToolListener(ToolListener listener) {
        getListenersList().remove(ToolListener.class, listener);
    }

    protected void fireToolStateChanged(ToolEvent event) {
        Object[] listeners = getListenersList().getListenerList();
        for (int i = listeners.length - 1; i >= 0; i -= 2) {
            ((ToolListener) listeners[i]).toolStateChanged(event);
        }

    }

    public boolean isActive() {
        return active;
    }

    public void activate() {
        active = true;
        fireToolStateChanged(ToolEvent.makeToolActivatedEvent(this));
    }

    public void deactivate() {
        active = false;
        fireToolStateChanged(ToolEvent.makeToolDeactivateEvent(this));
    }

    public void setActive(boolean value) {
        if (value) {
            activate();
        } else {
            deactivate();
        }
    }

    boolean enabled = true;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean value) {
        if (enabled != value) {
            enabled = value;
            fireToolStateChanged(enabled ? ToolEvent.makeToolEnabledEvent(this) : ToolEvent.makeToolDisabledEvent(this));
        }
    }

    public void mouseDragged(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

}

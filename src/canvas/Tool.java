package canvas;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/*
 * User: Serhiy Yevtushenko
 * Date: 14.10.2002
 * Time: 15:41:13
 */
public interface Tool extends MouseListener, MouseMotionListener, KeyListener{
    void addToolListener(ToolListener listener);
    void removeToolListener(ToolListener listener);

    boolean isActive();
    void activate();
    void deactivate();
    void setActive(boolean value);

    boolean isEnabled();
    void setEnabled(boolean value);

}

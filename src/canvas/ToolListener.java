package canvas;

import java.util.EventListener;

/*
 * User: Serhiy Yevtushenko
 * Date: 14.10.2002
 * Time: 20:27:40
 */
public interface ToolListener extends EventListener{
    void toolStateChanged(ToolEvent toolEvent);
}

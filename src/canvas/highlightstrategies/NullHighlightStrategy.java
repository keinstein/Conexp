package canvas.highlightstrategies;

import canvas.Figure;
import canvas.IHighlightStrategy;

/*
 * User: Serhiy Yevtushenko
 * Date: Oct 19, 2002
 * Time: 9:48:32 PM
 */
public class NullHighlightStrategy implements IHighlightStrategy{

    private NullHighlightStrategy() {
    }

    private static final IHighlightStrategy g_Instance = new NullHighlightStrategy();

    public static IHighlightStrategy getInstance(){
        return g_Instance;
    }

    public boolean highlightFigure(Figure figure) {
        return false;
    }

    public boolean isActive() {
        return true;
    }
}

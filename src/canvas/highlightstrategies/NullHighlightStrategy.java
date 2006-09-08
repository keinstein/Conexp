/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package canvas.highlightstrategies;

import canvas.Figure;
import canvas.IHighlightStrategy;


public class NullHighlightStrategy implements IHighlightStrategy {

    private NullHighlightStrategy() {
    }

    private static final IHighlightStrategy g_Instance = new NullHighlightStrategy();

    public static IHighlightStrategy getInstance() {
        return g_Instance;
    }

    public boolean highlightFigure(Figure figure) {
        return false;
    }

    public boolean isActive() {
        return true;
    }


    public String toString() {
        return "NullHighlightStrategy{}";
    }

    public IHighlightStrategy makeCopy() {
        return getInstance();
    }
}

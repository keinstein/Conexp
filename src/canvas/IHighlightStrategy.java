/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package canvas;

public interface IHighlightStrategy {
    boolean highlightFigure(Figure figure);

    boolean isActive();

    IHighlightStrategy makeCopy();
}

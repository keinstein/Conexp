/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package canvas.figures;

import canvas.Figure;

import java.util.Iterator;

public interface FigureIterator extends Iterator {
    Figure nextFigure();
}

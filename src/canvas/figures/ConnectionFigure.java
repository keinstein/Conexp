/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package canvas.figures;

import canvas.Figure;


public interface ConnectionFigure extends Figure {
    canvas.Figure getStartFigure();

    canvas.Figure getEndFigure();

    boolean canConnect(canvas.Figure startFigure, canvas.Figure endFigure);

    void setStartFigure(BorderCalculatingFigure start);

    void setEndFigure(BorderCalculatingFigure end);
}

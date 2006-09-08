/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package canvas.figures;

import canvas.Figure;


public interface ConnectionFigure extends Figure {
    Figure getStartFigure();

    Figure getEndFigure();

    boolean canConnect(Figure startFigure, Figure endFigure);

    void setStartFigure(BorderCalculatingFigure start);

    void setEndFigure(BorderCalculatingFigure end);
}

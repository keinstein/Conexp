/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/

package canvas;


public interface FigureListener {
    void afterFigureMove(Figure f);

    void beforeFigureMove(Figure f);
}

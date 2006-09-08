/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor;

import conexp.util.GenericStrategy;

public interface MoveStrategy extends GenericStrategy {
    void moveFigure(LatticeCanvas canvas, canvas.Figure f, double dx, double dy);
}

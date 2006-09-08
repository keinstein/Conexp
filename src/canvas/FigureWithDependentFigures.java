/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package canvas;

import java.util.Iterator;

public interface FigureWithDependentFigures extends Figure {
    void addDependend(Figure f);

    void removeAllDependend();

    void removeDependend(Figure f);

    Iterator dependendFigures();
}

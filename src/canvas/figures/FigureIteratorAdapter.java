/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package canvas.figures;

import canvas.Figure;
import util.collection.IteratorWrapperBase;

import java.util.Iterator;

public class FigureIteratorAdapter extends IteratorWrapperBase implements FigureIterator {
    public FigureIteratorAdapter(Iterator iterator) {
        super(iterator);
    }

    public Object next() {
        return innerIterator.next();
    }

    public Figure nextFigure() {
        return (Figure) innerIterator.next();
    }
}

/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package canvas.figures;

import canvas.Figure;

import java.util.Iterator;

public class FigureIteratorAdapter implements FigureIterator {
    final Iterator iterator;

    public FigureIteratorAdapter(Iterator iterator) {
        this.iterator = iterator;
    }

    public boolean hasNext() {
        return iterator.hasNext();
    }

    public Object next() {
        return iterator.next();
    }

    public void remove() {
        iterator.remove();
    }

    public Figure nextFigure() {
        return (Figure) iterator.next();
    }
}

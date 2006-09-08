/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package canvas.figures;

import canvas.BaseFigureVisitor;
import canvas.BoundsBlock;
import canvas.CanvasScheme;
import canvas.Figure;
import canvas.FigureBlock;
import canvas.FigureListener;
import util.collection.CollectionFactory;
import util.collection.ReverseListIterator;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.util.List;


public class CompositeFigure extends AbstractFigure {
    List innerFigures = CollectionFactory.createDefaultList();

    public void addFigure(Figure f) {
        innerFigures.add(f);
        onAddFigure(f);
        f.setFigureListener(fListener);
    }

    protected void onAddFigure(Figure f) {
    }

    public void setFigureListener(final FigureListener newFListener) {
        super.setFigureListener(newFListener);
        forEach(new FigureBlock() {
            public void exec(Figure f) {
                f.setFigureListener(newFListener);
            }
        });
    }

    public void removeFigureListener() {
        super.removeFigureListener();
        forEach(new FigureBlock() {
            public void exec(Figure f) {
                f.removeFigureListener();
            }
        });
    }

    protected void forEach(FigureBlock block) {
        FigureIterator it = figures();
        while (it.hasNext()) {
            Figure f = it.nextFigure();
            block.exec(f);
        }
    }

    private FigureIterator figures() {
        return new FigureIteratorAdapter(innerFigures.iterator());
    }

    private FigureIterator reverseFigures() {
        return new FigureIteratorAdapter(new ReverseListIterator(innerFigures));
    }

    public void visit(final BaseFigureVisitor visitor) {
        forEach(new FigureBlock() {
            public void exec(Figure f) {
                f.visit(visitor);
            }
        });
    }

    public void basicMoveBy(final double x, final double y) {
        forEach(new FigureBlock() {
            public void exec(Figure f) {
                f.moveBy(x, y);
            }
        });
    }

    public void draw(final Graphics g, final CanvasScheme opt) {
        forEach(new FigureBlock() {
            public void exec(Figure f) {
                f.draw(g, opt);
            }
        });
    }

    public void boundingBox(Rectangle2D rect) {
        forEach(new BoundsBlock(rect));
    }

    public boolean contains(double x, double y) {
        //here order of search doesn't matter
        FigureIterator iter = figures();
        while (iter.hasNext()) {
            if (iter.nextFigure().contains(x, y)) {
                return true;
            }
        }
        return false;
    }

    public Figure findFigureInside(double x, double y) {
        //search performed in reverse order to drawing
        FigureIterator iter = reverseFigures();
        while (iter.hasNext()) {
            Figure f = iter.nextFigure();
            if (f.contains(x, y)) {
                return f.findFigureInside(x, y);
            }
        }
        return null;
    }

    public Figure findFigureInsideExceptFor(double x, double y, Figure toExclude) {
        //search performed in reverse order to drawing
        FigureIterator iter = reverseFigures();
        while (iter.hasNext()) {
            Figure f = iter.nextFigure();
            if (f != toExclude && f.contains(x, y)) {
                Figure found = f.findFigureInsideExceptFor(x, y, toExclude);
                if (null != found) {
                    return found;
                }
            }
        }
        return null;
    }

    public void setCoords(double x, double y) {
    }
}

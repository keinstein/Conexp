/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package canvas.figures;

import canvas.BaseFigureVisitor;
import canvas.Figure;
import canvas.FigureListener;
import util.Assert;

import java.awt.geom.Point2D;


public abstract class AbstractFigure implements Figure {
    protected FigureListener fListener;

    public void removeFigureListener() {
        fListener = null;
    }

    public void setFigureListener(FigureListener newFListener) {
        fListener = newFListener;
    }

    public Figure findFigureInside(double x, double y) {
        Assert.isTrue(contains(x, y));
        return this;
    }

    public Figure findFigureInsideExceptFor(double x, double y, Figure toExclude) {
        Assert.isTrue(contains(x, y));
        return toExclude == this ? null : this;
    }

    public void visit(BaseFigureVisitor visitor) {
    }

    public void moveBy(double dx, double dy) {
        beforeMove();
        basicMoveBy(dx, dy);
        afterMove();
    }

    private void beforeMove() {
        if (null != fListener) {
            fListener.beforeFigureMove(this);
        }
    }

    private void afterMove() {
        if (null != fListener) {
            fListener.afterFigureMove(this);
        }
    }

    protected abstract void basicMoveBy(double x, double y);

    public boolean contains(double x, double y) {
        return false;
    }

    public void setCoords(Point2D coords) {
        setCoords(coords.getX(), coords.getY());
    }
}

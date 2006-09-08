/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package canvas.figures;

import util.StringUtil;

import java.awt.geom.Point2D;


public abstract class FigureWithCoords extends AbstractFigure implements ICenterPointLocatorProvidingFigure, BorderCalculatingFigure {
    protected CenterPointLocator centerPointLocator = new DefaultCenterPointLocator();

    protected FigureWithCoords(double x, double y) {
        doSetCoords(x, y);
    }

    protected FigureWithCoords() {
        this(0, 0);
    }


    public CenterPointLocator getCenterPointLocator() {
        return centerPointLocator;
    }

    public void setCenterPointLocator(CenterPointLocator centerPointLocator) {
        this.centerPointLocator = centerPointLocator;
    }

    protected void doSetCoords(double x, double y) {
        centerPointLocator.setCenterCoords(x, y);
    }

    public double getCenterX() {
        return centerPointLocator.getCenterX();
    }

    public double getCenterY() {
        return centerPointLocator.getCenterY();
    }

    public Point2D getCenter() {
        return new Point2D.Double(getCenterX(), getCenterY());
    }

    public void setCoords(double x, double y) {
        moveBy(x - getCenterX(), y - getCenterY());
//        doSetCoords(x, y);
/*        Assert.isTrue(Math.abs(this.x-x)<0.001);
        Assert.isTrue(Math.abs(this.y-y)<0.001); */
    }

    protected void basicMoveBy(double dx, double dy) {
        doSetCoords(getCenterX() + dx, getCenterY() + dy);
    }

    public String toString() {
        StringBuffer sb = new StringBuffer(40);
        sb.append(StringUtil.extractClassName(getClass().toString()));
        sb.append("[");
        sb.append(getContentDescription());
        sb.append("];");
        return sb.toString();
    }

    protected String getContentDescription() {
        return "x=" + getCenterX() + "; y=" + getCenterY();
    }
}

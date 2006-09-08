/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package canvas.figures;

import canvas.CanvasColorScheme;
import canvas.CanvasScheme;
import canvas.Figure;
import canvas.IHighlightStrategy;
import util.Assert;
import util.gui.GraphicObjectsFactory;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;


public class LineFigure extends AbstractFigure implements ConnectionFigure {
    protected BorderCalculatingFigure startFigure;
    protected BorderCalculatingFigure endFigure;

    public LineFigure(BorderCalculatingFigure startFigure, BorderCalculatingFigure endFigure) {
        this.startFigure = startFigure;
        this.endFigure = endFigure;
    }

    ColorTransformer colorTransformer = DefaultColorTransformer.getInstance();

    public void setColorTransformer(ColorTransformer colorTransformer) {
        this.colorTransformer = colorTransformer;
    }

    boolean selectable = false;

    public boolean isSelectable() {
        return selectable;
    }

    public void setSelectable(boolean selectable) {
        this.selectable = selectable;
    }

    public Figure getStartFigure() {
        return startFigure;
    }

    public Figure getEndFigure() {
        return endFigure;
    }

    public void setStartFigure(BorderCalculatingFigure start) {
        Assert.isTrue(start != null);
        this.startFigure = start;
    }

    public void setEndFigure(BorderCalculatingFigure end) {
        Assert.isTrue(end != null);
        this.endFigure = end;
    }

    public boolean canConnect(Figure startFigure, Figure endFigure) {
        return startFigure != endFigure;
    }

    public boolean contains(double x, double y) {
        if (!selectable) {
            return false;
        }
        Rectangle2D rect = new Rectangle2D.Double();
        boundingBox(rect);
        if (!rect.contains(x, y)) {
            return false;
        }


        Point2D start = getStartPoint();
        Point2D end = getEndPoint();

        return FigureUtils.pointNearLine(
                (int) start.getX(),
                (int) start.getY(),
                (int) end.getX(),
                (int) end.getY(),
                (int) x,
                (int) y,
                4
        );
    }

    protected void basicMoveBy(double dx, double dy) {
    }

    public void boundingBox(Rectangle2D rect) {
        startFigure.boundingBox(rect);
        Rectangle2D temp = new Rectangle2D.Double();
        endFigure.boundingBox(temp);
        rect.add(temp);
    }

    protected boolean isValidLine(Point2D startPoint, Point2D endPoint) {
        if (startPoint.equals(endPoint)) {
            return false;
        }
        if (endFigure.contains(startPoint.getX(), startPoint.getY())) {
            return false;
        }
        if (startFigure.contains(endPoint.getX(), endPoint.getY())) {
            return false;
        }
        return true;
    }

    protected Point2D getStartPoint() {
        Point2D startPoint = GraphicObjectsFactory.makePoint2D();
        startFigure.borderAt(endFigure.getCenter(), startPoint);
        return startPoint;
    }

    protected Point2D getEndPoint() {
        Point2D endPoint = GraphicObjectsFactory.makePoint2D();
        endFigure.borderAt(startFigure.getCenter(), endPoint);
        return endPoint;
    }

    protected BasicStroke getLineStroke(float thickness) {
        return new BasicStroke(thickness);
    }

    protected float getLineThickness(CanvasScheme opt) {
        return 1.0f;
    }

    protected Color getLineColor(CanvasScheme opt) {
        final IHighlightStrategy highlightStrategy = opt.getHighlightStrategy();
        CanvasColorScheme colorScheme = opt.getColorScheme();
        return colorTransformer.getColor(colorScheme.getHighlightColor(), colorScheme.getEdgeColor(), shouldHighlight(highlightStrategy), highlightStrategy.isActive());
    }

    protected boolean shouldHighlight(IHighlightStrategy highlightStrategy) {
        return highlightStrategy.highlightFigure(this);
    }

    public void setCoords(double x, double y) {
    }

    public Line2D getLine() {
        return new Line2D.Double(getStartPoint(), getEndPoint());
    }

    public boolean containsFigure(Figure f) {
        if (startFigure == f) {
            return true;
        }
        if (endFigure == f) {
            return true;
        }
        return false;
    }

    public void draw(Graphics g, CanvasScheme opt) {
        Graphics2D g2D = (Graphics2D) g;
        Point2D startPoint = getStartPoint();
        Point2D endPoint = getEndPoint();

        if (!isValidLine(startPoint, endPoint)) {
            return;
        }
        Stroke oldStroke = g2D.getStroke();
        g2D.setColor(getLineColor(opt));
        float thickness = getLineThickness(opt);
        if (thickness <= 0.01) {
            return;
        }

        try {
            g2D.setStroke(getLineStroke(thickness));
            g2D.draw(new Line2D.Double(startPoint, endPoint));
        } finally {
            g2D.setStroke(oldStroke);
        }

    }
}

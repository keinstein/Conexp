package canvas.figures;

import canvas.CanvasColorScheme;
import canvas.CanvasScheme;
import canvas.IHighlightStrategy;
import util.Assert;
import util.gui.GraphicObjectsFactory;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * Author: Serhiy Yevtushenko
 * Date: Dec 2, 2002
 * Time: 4:09:57 PM
 */
public class LineFigure extends AbstractFigure implements ConnectionFigure {
    protected BorderCalculatingFigure startFigure;
    protected BorderCalculatingFigure endFigure;

    public LineFigure(BorderCalculatingFigure startFigure, BorderCalculatingFigure endFigure) {
        this.startFigure = startFigure;
        this.endFigure = endFigure;
    }

    ColorTransformer colorTransformer = DefaultColorTransformer.getInstance();
    boolean selectable = false;

    public void setColorTransformer(ColorTransformer colorTransformer) {
        this.colorTransformer = colorTransformer;
    }

    public void setSelectable(boolean selectable) {
        this.selectable = selectable;
    }

    public canvas.Figure getStartFigure() {
        return startFigure;
    }

    public canvas.Figure getEndFigure() {
        return endFigure;
    }

    public void setStartFigure(BorderCalculatingFigure start) {
        Assert.isTrue(start!=null);
        this.startFigure = start;
    }

    public void setEndFigure(BorderCalculatingFigure end) {
        Assert.isTrue(end!=null);
        this.endFigure = end;
    }

    public boolean canConnect(canvas.Figure startFigure, canvas.Figure endFigure) {
        return startFigure!=endFigure;
    }

    public boolean contains(double x, double y) {
        if(!selectable){
            return false;
        }
        Rectangle2D rect = new Rectangle2D.Double();
        boundingBox(rect);
        if(!rect.contains(x, y)){
            return false;
        }


        Point2D start = getStartPoint();
        Point2D end = getEndPoint();

        return FigureUtils.pointNearLine(
                (int)start.getX(),
                (int)start.getY(),
                (int)end.getX(),
                (int)end.getY(),
                (int)x,
                (int)y,
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
        return (!endFigure.contains(startPoint.getX(), startPoint.getY()) &&
                        !startFigure.contains(endPoint.getX(), endPoint.getY()));
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

    public void draw(java.awt.Graphics g, CanvasScheme opt) {
        java.awt.Graphics2D g2D = (java.awt.Graphics2D) g;
        Point2D startPoint = getStartPoint();
        Point2D endPoint = getEndPoint();

        if (!isValidLine(startPoint, endPoint)) {
            return;
        }
        g2D.setColor(getLineColor(opt));
        float thickness = getLineThickness(opt);
        if (thickness <= 0.01) {
            return;
        }

        Stroke oldStroke = g2D.getStroke();
        g2D.setStroke(getLineStroke(thickness));
        g2D.draw(new Line2D.Double(startPoint, endPoint));
        g2D.setStroke(oldStroke);
    }
}

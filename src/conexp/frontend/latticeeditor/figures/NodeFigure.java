/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor.figures;

import canvas.CanvasScheme;
import canvas.Selectable;
import canvas.figures.FigureUtils;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class NodeFigure extends AbstractLineDiagramFigure implements Selectable {
    private int radius = 10;
    protected int id = -1;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }


    public void boundingBox(Rectangle2D rect) {
        rect.setFrame(getFigureEllipse().getBounds2D());
    }

    public void borderAt(Point2D outPoint, Point2D result) {
        FigureUtils.calcEllipseBorder(getRadius(),
                getCenterX(), getCenterY(),
                outPoint, result);
    }

    public void draw(Graphics g, CanvasScheme opt) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.setColor(opt.getColorScheme().getNodeBorderColor());
        Ellipse2D ellipse = getFigureEllipse();
        g2D.draw(ellipse);

        String tmp = Integer.toString(id);
        double w = g2D.getFontMetrics().stringWidth(tmp);
        double h = g2D.getFontMetrics().getHeight();
        g2D.drawString(tmp, (float) (getCenterX() - w / 2), (float) (getCenterY() + h / 4));
    }


    public boolean contains(double x, double y) {
        return getFigureEllipse().contains(x, y);
    }


    protected Ellipse2D getFigureEllipse() {
        return new Ellipse2D.Double(getCenterX() - getRadius(), getCenterY() - getRadius(), 2 * getRadius(), 2 * getRadius());
    }

    protected int getRadius() {
        return radius;
    }

    protected void setRadius(int radius) {
        this.radius = radius;
    }

    public String toString() {
        return super.toString() + "[id=" + id + ']';
    }

    boolean selected;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

}

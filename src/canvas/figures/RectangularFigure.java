/*
 * User: sergey
 * Date: Jan 24, 2002
 * Time: 3:25:52 AM
 */
package canvas.figures;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public abstract class RectangularFigure extends FigureWithCoords implements BorderCalculatingFigure {
    private double width;
    private double height;

    public RectangularFigure(double x, double y, double w, double h) {
        super(x, y);
        this.setWidth(w);
        this.setHeight(h);
    }

    public RectangularFigure(Rectangle2D rect) {
        this(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
    }


    public RectangularFigure() {
        this(0, 0, 0, 0);
    }


    public void borderAt(Point2D outPoint, Point2D result) {
        double dx = getCenterX() - outPoint.getX();
        double dy = getCenterY() - outPoint.getY();

        if (Math.abs(dx) * getHeight() <= Math.abs(dy) * getWidth()) {
            double dh = (dy > 0 ? -getHeight() / 2 : getHeight() / 2);
            result.setLocation(getCenterX() + (dy != 0 ? (dx * dh) / dy : 0),
                    getCenterY() + dh);
        } else {

            double dw = (dx > 0 ? -getWidth() / 2: getWidth() / 2);

            result.setLocation(getCenterX() + dw,
                    getCenterY() + (dx != 0 ? (dy * dw) / dx : 0));
        }
    }

    public void boundingBox(Rectangle2D rect) {
        rect.setFrame(getLeftX(), getTopY(), getWidth(), getHeight());
    }

    protected double getLeftX() {
        return (getCenterX() - getWidth() / 2);
    }

    protected double getTopY() {
        return getCenterY() - getHeight() / 2;
    }

    protected double getRightX() {
        return getCenterX() + getWidth() / 2;
    }

    protected double getBottomY() {
        return (getCenterY() + getHeight() / 2);
    }

    public boolean contains(double X, double Y) {
        if (X < getLeftX()) {
            return false;
        }
        if (X >= getRightX()) {
            return false;
        }
        if (Y < getTopY()) {
            return false;
        }
        if (Y >= getBottomY()) {
            return false;
        }
        return true;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setHeight(double height) {
        this.height = height;
    }


}

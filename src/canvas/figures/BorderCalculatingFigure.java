/*
 * User: sergey
 * Date: Jan 25, 2002
 * Time: 9:58:45 PM
 */
package canvas.figures;

import canvas.Figure;

import java.awt.geom.Point2D;

public interface BorderCalculatingFigure extends Figure {
    void borderAt(Point2D outPoint, Point2D result);

    Point2D getCenter();
}

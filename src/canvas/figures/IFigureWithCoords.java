/**
 * Author: Serhiy Yevtushenko
 * Date: Dec 2, 2002
 * Time: 5:10:16 PM
 */
package canvas.figures;

import canvas.Figure;

import java.awt.geom.Point2D;

public interface IFigureWithCoords extends Figure{
    double getCenterX();

    double getCenterY();

    Point2D getCenter();

    void setCoords(Point2D coords);
}

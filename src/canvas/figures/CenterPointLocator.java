/*
 * Created by IntelliJ IDEA.
 * User: Serhiy Yevtushenko
 * Date: Jun 9, 2002
 * Time: 4:43:16 AM
 * To change template for new interface use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package canvas.figures;

public interface CenterPointLocator {
    double getCenterX();
    double getCenterY();
    void setCenterCoords(double x, double y);
}

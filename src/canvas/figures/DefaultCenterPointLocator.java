/*
 * Created by IntelliJ IDEA.
 * User: Serhiy Yevtushenko
 * Date: Jun 9, 2002
 * Time: 4:44:13 AM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package canvas.figures;

public class DefaultCenterPointLocator implements CenterPointLocator{
    double x;
    double y;

    public double getCenterX() {
        return x;
    }

    public double getCenterY() {
        return y;
    }

    public void setCenterCoords(double x, double y) {
        this.x = x;
        this.y = y;
    }
}

/*
 * Created by IntelliJ IDEA.
 * User: sergey
 * Date: Jan 15, 2002
 * Time: 8:45:39 PM
 * To change template for new interface use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package conexp.frontend.latticeeditor;

import conexp.util.GenericStrategy;

public interface MoveStrategy extends GenericStrategy {
    void moveFigure(LatticeCanvas canvas, canvas.Figure f, double dx, double dy);
}

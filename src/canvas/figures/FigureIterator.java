/*
 * Created by IntelliJ IDEA.
 * User: sergey
 * Date: Jan 25, 2002
 * Time: 9:10:57 PM
 * To change template for new interface use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package canvas.figures;

import canvas.Figure;

import java.util.Iterator;

public interface FigureIterator extends Iterator {
    Figure nextFigure();
}

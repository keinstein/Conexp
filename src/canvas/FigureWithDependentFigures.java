/*
 * User: Serhiy Yevtushenko
 * Date: Aug 16, 2002
 * Time: 4:22:23 PM
 */
package canvas;

import java.util.Iterator;

public interface FigureWithDependentFigures extends Figure{
    void addDependend(Figure f);

    void removeAllDependend();

    void removeDependend(Figure f);

    Iterator dependendFigures();
}

/*
 * Created by IntelliJ IDEA.
 * User: sergey
 * Date: Jan 24, 2002
 * Time: 1:29:42 AM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package conexp.frontend.latticeeditor;

import conexp.frontend.latticeeditor.figures.AbstractConceptCorrespondingFigure;
import conexp.frontend.latticeeditor.figures.DefaultFigureVisitor;

import java.awt.geom.Point2D;

public abstract class ConceptFigureCoordinatesChangingFigureVisitor extends DefaultFigureVisitor {

    protected abstract Point2D getCoordsForFigure(AbstractConceptCorrespondingFigure f);

    public void visitConceptCorrespondingFigure(AbstractConceptCorrespondingFigure f) {
        f.setCoords(getCoordsForFigure(f));
    }
}

/*
 * Created by IntelliJ IDEA.
 * User: sergey
 * Date: Jan 24, 2002
 * Time: 1:27:40 AM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package conexp.frontend.latticeeditor;

import conexp.core.layout.ConceptCoordinateMapper;
import conexp.frontend.latticeeditor.figures.AbstractConceptCorrespondingFigure;

import java.awt.geom.Point2D;

public class CoordinateMapperFigureVisitor extends ConceptFigureCoordinatesChangingFigureVisitor {
    private final ConceptCoordinateMapper mapper;

    public CoordinateMapperFigureVisitor(ConceptCoordinateMapper mapper) {
        this.mapper = mapper;
    }

    Point2D coords = new Point2D.Double();

    protected Point2D getCoordsForFigure(AbstractConceptCorrespondingFigure f) {
        mapper.setCoordsForConcept(f.getConcept(), coords);
        return coords;
    }

}

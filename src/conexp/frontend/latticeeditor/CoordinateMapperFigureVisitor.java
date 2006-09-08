/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



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

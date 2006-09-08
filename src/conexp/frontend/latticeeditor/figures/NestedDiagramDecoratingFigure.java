/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor.figures;

import conexp.core.LatticeElement;
import conexp.frontend.latticeeditor.ConceptQuery;
import conexp.frontend.latticeeditor.FigureDimensionCalcStrategy;
import conexp.frontend.latticeeditor.LatticeCanvasScheme;

import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

public class NestedDiagramDecoratingFigure extends AbstractConceptCorrespondingFigure {

    AbstractConceptCorrespondingFigure innerFigure;
    boolean isTop;

    ConceptQuery outerConceptQuery;

    public NestedDiagramDecoratingFigure(AbstractConceptCorrespondingFigure innerFigure, ConceptQuery outerConceptQuery, boolean isTop) {
        this.innerFigure = innerFigure;
        this.outerConceptQuery = outerConceptQuery;
        this.isTop = isTop;
    }

    protected void drawInterior(Graphics2D g2D, LatticeCanvasScheme opt) {

    }

    public Ellipse2D getFigureEllipse(FigureDimensionCalcStrategy dimensionCalcStrategy) {
        double nodeRadius = dimensionCalcStrategy.calcNodeRadius(getConceptQuery());
        Ellipse2D oval = new Ellipse2D.Double(getCenterX() - nodeRadius, getCenterY() - nodeRadius, nodeRadius * 2, nodeRadius * 2);
        return oval;

/*
        CenterPointLocator oldCenterLocator = innerFigure.getCenterPointLocator();
        try {
            innerFigure.setCenterPointLocator(getCenterPointLocator());
            return innerFigure.getFigureEllipse(dimensionCalcStrategy);
        } finally {
            innerFigure.setCenterPointLocator(oldCenterLocator);
        }
*/
    }

    public void borderAt(Point2D outPoint, Point2D result) {
        doCalcBorderAt(getDimensionCalcStrategy(), outPoint, result);
    }

    private void doCalcBorderAt(FigureDimensionCalcStrategy dimensionCalcStrategy, Point2D outPoint, Point2D result) {
        Ellipse2D ellipse = getFigureEllipse(dimensionCalcStrategy);
        double radiusX = ellipse.getWidth() / 2;
        double radiusY = ellipse.getHeight() / 2;

        double dx = outPoint.getX() - getCenterX();
        double dy = outPoint.getY() - getCenterY();
        double len = Math.sqrt(dx * dx + dy * dy);
        dx = radiusX * dx / len;
        dy = radiusY * dy / len;
        result.setLocation(getCenterX() + dx, getCenterY() + dy);
    }

    public LatticeElement getConcept() {
        return innerFigure.getConcept();
    }

    ConceptQuery cachedConceptQuery;

    public ConceptQuery getConceptQuery() {
        if (null == cachedConceptQuery) {
            cachedConceptQuery = innerFigure.getConceptQuery().makeCombinedQuery(outerConceptQuery, isTop, true);
            //TODO: analyse, how it should be in general case
        }
        return cachedConceptQuery;
    }
}

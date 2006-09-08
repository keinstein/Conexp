/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor.figures.tests;

import canvas.Figure;
import canvas.tests.FigureTest;
import conexp.core.ConceptFactory;
import conexp.core.tests.SetBuilder;
import conexp.frontend.latticeeditor.ConceptQuery;
import conexp.frontend.latticeeditor.FigureDimensionCalcStrategy;
import conexp.frontend.latticeeditor.FigureDimensionCalcStrategyProvider;
import conexp.frontend.latticeeditor.figures.ConceptFigure;
import conexp.frontend.latticeeditor.noderadiusstrategy.AbstractNodeRadiusCalcStrategy;
import conexp.frontend.latticeeditor.queries.ConceptNodeQuery;

import java.awt.geom.Point2D;

public class ConceptFigureTest extends FigureTest {

    private static final int RADIUS = 5;

    protected Figure makeFigure() {
        final ConceptFigure conceptFigure = new ConceptFigure(new ConceptNodeQuery(SetBuilder.makeContext(new int[0][0]), ConceptFactory.makeEmptyLatticeElement(),
                SetBuilder.makeSet(new int[0])));
        conceptFigure.setFigureDimensionCalcStrategyProvider(new FigureDimensionCalcStrategyProvider() {
            FigureDimensionCalcStrategy figureDimensionCalcStrategy = makeFigureDimensionCalcStrategy(RADIUS);

            public FigureDimensionCalcStrategy getFigureDimensionCalcStrategy() {
                return figureDimensionCalcStrategy;
            }
        });
        return conceptFigure;
    }

    public void testVisitConceptFigure() {
        MockFigureVisitor visitor = new MockFigureVisitor();
        visitor.setExpectedVisits(1);
        f.visit(visitor);
        visitor.verify();
    }

    public void testBorderAt() {

        final int centerX = 0;
        final int centerY = 10;
        ConceptFigure cf = (ConceptFigure) f;

        cf.setCoords(centerX, centerY);
        Point2D outPoint = new Point2D.Double(centerX, centerY - 2 * RADIUS);
        Point2D result = new Point2D.Double();
        cf.borderAt(outPoint, result);
        assertEquals(new Point2D.Double(centerX, centerY - RADIUS), result);

        outPoint.setLocation(centerX, centerY + 2 * RADIUS);
        cf.borderAt(outPoint, result);
        assertEquals(new Point2D.Double(centerX, centerY + RADIUS), result);

        outPoint.setLocation(centerX + 2 * RADIUS, centerY);
        cf.borderAt(outPoint, result);
        assertEquals(new Point2D.Double(centerX + RADIUS, centerY), result);

        outPoint.setLocation(centerX - 2 * RADIUS, centerY);
        cf.borderAt(outPoint, result);
        assertEquals(new Point2D.Double(centerX - RADIUS, centerY), result);
    }

    private static FigureDimensionCalcStrategy makeFigureDimensionCalcStrategy(final int nodeRadius) {
        return new AbstractNodeRadiusCalcStrategy(null) {
            public int calcNodeRadius(ConceptQuery query) {
                return nodeRadius;
            }
        };
    }
}

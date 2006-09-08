/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor.tests;

import canvas.BaseFigureVisitor;
import conexp.core.ConceptsCollection;
import conexp.core.Context;
import conexp.core.FCAEngineRegistry;
import conexp.core.Lattice;
import conexp.core.tests.SetBuilder;
import conexp.frontend.latticeeditor.ConceptSetDrawing;
import conexp.frontend.latticeeditor.LatticeDrawing;
import conexp.frontend.latticeeditor.NestedLineDiagramDrawing;
import conexp.frontend.latticeeditor.figures.AbstractConceptCorrespondingFigure;
import conexp.frontend.latticeeditor.figures.DefaultFigureVisitor;
import conexp.frontend.latticeeditor.figures.NestedDiagramNodeFigure;
import util.collection.CollectionFactory;

import java.awt.Rectangle;
import java.awt.geom.Point2D;

public class NestedLineDiagramDrawingTest extends ConceptSetDrawingTest {
    private Lattice outer;
    private Lattice inner;
    private NestedLineDiagramDrawing drawing;

    protected void setUp() {
        Context cxt = SetBuilder.makeContext(new int[][]{{0, 1},
                {1, 0}});

        outer = SetBuilder.makeLatticeWithContextAndFeatureMask(cxt, new int[]{1, 0}, new int[]{1, 1});
        LatticeDrawing outerDrawing = new LatticeDrawing();
        outerDrawing.setLattice(outer);

        inner = SetBuilder.makeLatticeWithContextAndFeatureMask(cxt, new int[]{0, 1}, new int[]{1, 1});
        LatticeDrawing innerDrawing = new LatticeDrawing();
        innerDrawing.setLattice(inner);
        innerDrawing.getFigureForConcept(inner.getOne()).setCoords(0, 0);
        innerDrawing.getFigureForConcept(inner.getZero()).setCoords(0, 20);

        assertEquals(new Rectangle(-12, -12, 24, 44), innerDrawing.getUserBoundsRect());


        outerDrawing.getFigureForConcept(outer.getOne()).setCoords(0, 0);
        outerDrawing.getFigureForConcept(outer.getZero()).setCoords(0, 20);
        assertEquals(new Rectangle(-12, -12, 24, 44), innerDrawing.getUserBoundsRect());

        //it can be also the set
        ConceptsCollection concepts = FCAEngineRegistry.buildLattice(cxt);

        drawing = new NestedLineDiagramDrawing(outerDrawing, innerDrawing, concepts);
    }

    protected ConceptSetDrawing getDrawing() {
        return drawing;
    }

    public void testMappingOfOuterLineDiagram() {
        final NestedDiagramNodeFigure figureForOne = (NestedDiagramNodeFigure) drawing.getFigureForConcept(outer.getOne());
        assertEquals(14.11 / 2.0, figureForOne.getCenterX(), 0.01);
    }

    public void testFindFigureInside() {
        final NestedDiagramNodeFigure figureForOne = (NestedDiagramNodeFigure) drawing.getFigureForConcept(outer.getOne());
        final AbstractConceptCorrespondingFigure figureForOneInnerOneFigure = figureForOne.getFigureForConcept(inner.getOne());
        Point2D innerPoint = figureForOneInnerOneFigure.getCenter();

        assertTrue(figureForOneInnerOneFigure.contains(innerPoint.getX(), innerPoint.getY()));
        assertTrue(figureForOne.contains(innerPoint.getX(), innerPoint.getY()));
        assertEquals(figureForOneInnerOneFigure, drawing.findFigureInReverseOrder(innerPoint.getX(), innerPoint.getY()));
    }

    public void testGatherInnerQuerys() {

        Context cxt = SetBuilder.makeContext(new int[][]{{0, 1, 1, 1},
                {1, 0, 1, 1},
                {1, 1, 0, 1},
                {1, 1, 1, 0}});

        outer = SetBuilder.makeLatticeWithContextAndFeatureMask(cxt, new int[]{1, 1, 0, 0}, new int[]{1, 1, 1, 1});
        LatticeDrawing outerDrawing = new LatticeDrawing();
        outerDrawing.setLattice(outer);

        inner = SetBuilder.makeLatticeWithContextAndFeatureMask(cxt, new int[]{0, 0, 1, 1}, new int[]{1, 1, 1, 1});
        LatticeDrawing innerDrawing = new LatticeDrawing();
        innerDrawing.setLattice(inner);
        //it can be also the set
        ConceptsCollection concepts = FCAEngineRegistry.buildLattice(cxt);

        drawing = new NestedLineDiagramDrawing(outerDrawing, innerDrawing, concepts);

        final java.util.Set innerQueries = CollectionFactory.createDefaultSet();
        BaseFigureVisitor visitor = new DefaultFigureVisitor() {
            public void visitConceptCorrespondingFigure(AbstractConceptCorrespondingFigure f) {
                innerQueries.add(f.getIntentQuery());
            }
        };

        drawing.visitFigures(visitor);
        assertEquals(16, innerQueries.size());
    }


}

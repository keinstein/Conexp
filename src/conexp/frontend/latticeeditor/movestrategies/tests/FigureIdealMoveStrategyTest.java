package conexp.frontend.latticeeditor.movestrategies.tests;

import junit.framework.TestCase;
import conexp.core.Lattice;
import conexp.core.layout.layeredlayout.tests.TestDataHolder;
import conexp.core.layout.layeredlayout.tests.MapBasedConceptCoordinateMapper;
import conexp.core.layout.ConceptCoordinateMapper;
import conexp.core.layout.LayouterProvider;
import conexp.core.tests.SetBuilder;
import conexp.frontend.latticeeditor.*;
import conexp.frontend.latticeeditor.figures.ConceptFigure;
import conexp.frontend.latticeeditor.figures.AbstractConceptCorrespondingFigure;
import conexp.frontend.latticeeditor.movestrategies.FigureIdealMoveStrategy;
import conexp.frontend.LatticeDrawingProvider;
import conexp.frontend.components.LatticeComponent;
import util.testing.TestUtil;

/*
 * This program is a part of the Darmstadt JSM Implementation.
 *
 * You can redistribute it (modify it, compile it, decompile it, whatever)
 * AMONG THE JSM COMMUNITY. If you plan to use this program outside the
 * community, please notify V.K.Finn (finn@viniti.ru) and the authors.
 *
 * Authors: Peter Grigoriev and Serhiy Yevtushenko
 * E-mail: {peter, sergey}@intellektik.informatik.tu-darmstadt.de
 * 
 * Date: 27/6/2003
 * Time: 13:39:58
 */

public class FigureIdealMoveStrategyTest extends TestCase {
    public void testCalculationOfMoveDistance() {
        LatticeComponent component = new LatticeComponent(SetBuilder.makeContext(TestDataHolder.FULL_RELATION_NOMINAL_2));
        component.calculateLattice();
        Lattice lattice = component.getLattice();
        ConceptCoordinateMapper mapper = MapBasedConceptCoordinateMapper.buildMapperForLattice(
                lattice, TestDataHolder.FULL_RELATION_NOMINAL_2,
                TestDataHolder.ASYMMETRIC_LAYOUT_NOMINAL_2
        );
        LatticeDrawing drawing = component.getDrawing();
        drawing.setCoordinatesFromMapper(mapper);
        LatticePainterPanel panel = new LatticePainterPanel(component);
        panel.initialUpdate();
        try {
            panel.getEditableDrawingParams().setNodeMaxRadius(10);
        } catch (java.beans.PropertyVetoException e) {
            TestUtil.reportUnexpectedException(e);
        }
        ConceptFigure figure = (ConceptFigure)panel.getFigureForConcept(lattice.findConceptWithIntent(SetBuilder.makeSet(new int[]{1, 0})));
        assertEquals(150, figure.getCenterY(), TestDataHolder.PRECISION);
        FigureIdealMoveStrategy strategy = new FigureIdealMoveStrategy();
        double constraint = strategy.constraintMinimalUpMoveSizeForIdeal(panel,figure, -200);
        assertEquals(-80, constraint, TestDataHolder.PRECISION);
    }
}

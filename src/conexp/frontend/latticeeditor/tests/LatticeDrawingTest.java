package conexp.frontend.latticeeditor.tests;

import conexp.core.tests.SetBuilder;
import conexp.frontend.latticeeditor.LatticeDrawing;
import conexp.frontend.latticeeditor.labelingstrategies.LabelingStrategiesKeys;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


public class LatticeDrawingTest extends TestCase {
    public static Test suite() {
        return new TestSuite(LatticeDrawingTest.class);
    }

    public void testLatticeDrawing() {
        LatticeDrawing drawing = new LatticeDrawing();
        drawing.setLattice(null);
    }

    public void testChangeOfAttributeAndObjectsModes() {
        LatticeDrawing drawing = new LatticeDrawing();
        drawing.setLattice(SetBuilder.makeLatticeWithContext(new int[][]{{1}}));
        drawing.setAttributeLabelingStrategyKey(LabelingStrategiesKeys.NO_ATTRIBS_LABELING_STRATEGY);
        assertEquals(false, drawing.hasLabelsForAttributes());
        drawing.setAttributeLabelingStrategyKey(LabelingStrategiesKeys.ALL_ATTRIBS_LABELING_STRATEGY_KEY);
        assertEquals(true, drawing.hasLabelsForAttributes());
    }

}
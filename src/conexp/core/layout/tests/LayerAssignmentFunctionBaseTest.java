/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.core.layout.tests;

import conexp.core.ItemSet;
import conexp.core.Lattice;
import conexp.core.LatticeElement;
import conexp.core.layout.ILayerAssignmentFunction;
import conexp.core.tests.SetBuilder;
import junit.framework.TestCase;
import util.collection.CollectionFactory;

import java.util.Map;


public abstract class LayerAssignmentFunctionBaseTest extends TestCase {
    /**
     * Main requirement for layer assignment function - preservation of lattice order
     * that means, that if x <= y => layer(x)<=layer(y)
     */
    public void testForChain() {
        int[][] threeElementChainContext = new int[][]{
                {0, 0, 1},
                {0, 1, 1},
                {1, 1, 1}
        };
        checkLayerFunctionForLatticeFromContext(threeElementChainContext);
    }

    private void checkLayerFunctionForLatticeFromContext(int[][] context) {
        Lattice lattice = SetBuilder.makeLattice(context);
        ILayerAssignmentFunction layerFunction = makeLayerAssignmentFunction();
        Map latticeElementToLayerMap = checkCompletenessOfLayerFunctionCalculation(layerFunction, lattice);
        checkCorrectnessOfLayerFunction(lattice, latticeElementToLayerMap);
    }

    private static Map checkCompletenessOfLayerFunctionCalculation(ILayerAssignmentFunction layerFunction, Lattice lattice) {
        final Map latticeElementToLayerMap = CollectionFactory.createDefaultMap();
        layerFunction.calculateLayersForLattice(lattice, new ILayerAssignmentFunction.ILayerAssignmentFunctionCallback() {
            public void layerForLatticeElement(LatticeElement latticeElement, int layer) {
                Object previousValue = latticeElementToLayerMap.put(latticeElement, new Integer(layer));
                assertNull("for each element of lattice layer can be assigned only one time", previousValue);
            }
        });
        assertEquals("Layer should be assigned for each element of lattice", lattice.conceptsCount(), latticeElementToLayerMap.size());
        return latticeElementToLayerMap;
    }

    private static void checkCorrectnessOfLayerFunction(Lattice lattice, final Map latticeElementToLayerMap) {
        int conceptCount = lattice.conceptsCount();
        for (int i = 0; i < conceptCount; i++) {
            LatticeElement one = lattice.elementAt(i);
            int layerForOne = ((Integer) latticeElementToLayerMap.get(one)).intValue();
            for (int j = i + 1; j < conceptCount; j++) {
                LatticeElement second = lattice.elementAt(j);
                int layerForSecond = ((Integer) latticeElementToLayerMap.get(second)).intValue();
                int compareResult = one.compare(second);

                switch (compareResult) {
                    case ItemSet.GREATER:
                        assertTrue("For greater element layer should be greater", layerForOne > layerForSecond);
                        break;
                    case ItemSet.LESS:
                        assertTrue("For lesser element layer should be lesser", layerForOne < layerForSecond);
                        break;
                    case ItemSet.NOT_COMPARABLE:
                        assertTrue("No statements can be made", true);
                        break;
                    default:
                        fail("Should not get here");
                }
            }
        }
    }

    protected abstract ILayerAssignmentFunction makeLayerAssignmentFunction();
}

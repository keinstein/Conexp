package conexp.core.tests;

import conexp.core.LatticeElement;
import junit.framework.TestCase;

/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 */

public class LatticeElementTest extends TestCase {
    public void testAddRemoveSuccessor() throws Exception {
        LatticeElement parent = SetBuilder.makeLatticeElement(new int[]{1, 1}, new int[]{0, 0});
        LatticeElement child = SetBuilder.makeLatticeElement(new int[]{0, 1}, new int[]{1, 0});
        parent.addSucc(child);

        assertEquals(1, parent.getSuccCount());
        assertEquals(0, parent.getPredCount());

        assertEquals(0, child.getSuccCount());
        assertEquals(1, child.getPredCount());

        parent.removeSucc(child);
        assertEquals(0, parent.getSuccCount());
        assertEquals(0, parent.getPredCount());
        assertEquals(0, child.getSuccCount());
        assertEquals(0, child.getPredCount());
    }

    public void testAddRemovePredecessor(){
        LatticeElement parent = SetBuilder.makeLatticeElement(new int[]{1, 1}, new int[]{0, 0});
        LatticeElement child = SetBuilder.makeLatticeElement(new int[]{0, 1}, new int[]{1, 0});
        child.addPred(parent);

        assertEquals(1, parent.getSuccCount());
        assertEquals(0, parent.getPredCount());

        assertEquals(0, child.getSuccCount());
        assertEquals(1, child.getPredCount());

        child.removePred(parent);
        assertEquals(0, parent.getSuccCount());
        assertEquals(0, parent.getPredCount());
        assertEquals(0, child.getSuccCount());
        assertEquals(0, child.getPredCount());

    }

}
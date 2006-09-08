/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.tests;

import conexp.core.ConceptFactory;
import conexp.core.Edge;
import conexp.core.LatticeElement;
import junit.framework.TestCase;


public class EdgeTest extends TestCase {
    private Edge e;
    private LatticeElement start;
    private LatticeElement end;

    protected void setUp() {
        start = ConceptFactory.makeEmptyLatticeElement();
        end = ConceptFactory.makeEmptyLatticeElement();
        e = new Edge(start, end);
    }

    /**
     * Insert the method's description here.
     * Creation date: (01.12.00 3:11:37)
     */
    public void testContains() {
        assertTrue(e.contains(start));
        assertTrue(e.contains(end));
        assertEquals(false, e.contains(ConceptFactory.makeEmptyLatticeElement()));
    }
}

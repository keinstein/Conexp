package conexp.core.tests;

import conexp.core.ConceptFactory;
import conexp.core.Edge;
import conexp.core.LatticeElement;
import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Insert the type's description here.
 * Creation date: (01.12.00 3:06:54)
 * @author
 */
public class EdgeTest extends junit.framework.TestCase {
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
     * Creation date: (01.12.00 3:07:48)
     * @return junit.framework.Test
     */
    public static Test suite() {
        return new TestSuite(EdgeTest.class);
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
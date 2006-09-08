/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.core.tests;

import conexp.core.ConceptsCollection;
import junit.framework.TestCase;
import util.testing.TestUtil;



public class ConceptsCollectionTest extends TestCase {
    public static void testEquals() {
        TestUtil.testEqualsAndHashCode(new ConceptsCollection(), new ConceptsCollection());
    }

    public static void testEqualsAsSets() {
        ConceptsCollection one = new ConceptsCollection();
        ConceptsCollection two = new ConceptsCollection();
        assertTrue(one.equalsAsSets(two));
        two = SetBuilder.makeConceptSet(new int[][]{{0, 1}, {1, 0}});
        assertFalse(one.equalsAsSets(two));
    }
}

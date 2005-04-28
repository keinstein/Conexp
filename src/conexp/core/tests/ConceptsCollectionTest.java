package conexp.core.tests;

import conexp.core.ConceptsCollection;
import junit.framework.TestCase;
import util.testing.TestUtil;

/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 * Date: 5/8/2003
 * Time: 14:39:59
 */

public class ConceptsCollectionTest extends TestCase {
    public static void testEquals() {
        TestUtil.testEqualsAndHashCode(new ConceptsCollection(), new ConceptsCollection());
    }

    public static void testEqualsAsSets(){
        ConceptsCollection one = new ConceptsCollection();
        ConceptsCollection two = new ConceptsCollection();
        assertTrue(one.equalsAsSets(two));
        two = SetBuilder.makeConceptSet(new int[][]{{0, 1},{1, 0}});
        assertFalse(one.equalsAsSets(two));
    }
}

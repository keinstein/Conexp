package conexp.core.tests;

import junit.framework.TestCase;
import util.testing.TestUtil;
import conexp.core.ConceptsCollection;

/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 * Date: 5/8/2003
 * Time: 14:39:59
 */

public class ConceptsCollectionTest extends TestCase {
    public void testEquals() {
        TestUtil.testEqualsAndHashCode(new ConceptsCollection(), new ConceptsCollection());
    }

    public void testEqualsAsSets(){
        ConceptsCollection one = new ConceptsCollection();
        ConceptsCollection two = new ConceptsCollection();
        assertTrue(one.equalsAsSets(two));
        two = SetBuilder.makeConceptSet(new int[][]{{0, 1},{1, 0}});
        assertFalse(one.equalsAsSets(two));
    }
}

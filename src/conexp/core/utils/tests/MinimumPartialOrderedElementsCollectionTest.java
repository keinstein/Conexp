/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.core.utils.tests;

import conexp.core.IPartiallyOrdered;
import conexp.core.ModifiableSet;
import conexp.core.tests.SetBuilder;
import conexp.core.utils.MinimumPartialOrderedElementsCollection;
import junit.framework.TestCase;


public class MinimumPartialOrderedElementsCollectionTest extends TestCase {
    public static void testAdditionOfMinimumPartialOrderedElements() {
        MinimumPartialOrderedElementsCollection collection = new MinimumPartialOrderedElementsCollection();
        assertTrue(collection.isEmpty());
        IPartiallyOrdered one = SetBuilder.makeSet(new int[]{0, 1, 1});
        collection.add(one);

        assertEquals(1, collection.getSize());
        assertFalse(collection.isEmpty());
        assertTrue(collection.contains(one));

        ModifiableSet two = SetBuilder.makeSet(new int[]{1, 0, 0});
        collection.add(two);
        assertEquals(2, collection.getSize());
        assertTrue(collection.contains(two));

        collection.add(SetBuilder.makeSet(new int[]{0, 1, 1}));
        assertEquals(2, collection.getSize());

        collection.add(SetBuilder.makeSet(new int[]{0, 0, 1}));
        assertEquals("collection contain's only minimal elements", 2, collection.getSize());
        assertFalse(collection.contains(one));

        collection.add(SetBuilder.makeSet(new int[]{0, 1, 1}));
        assertEquals(2, collection.getSize());
    }
}

/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.tests;

import conexp.core.AttributeIterator;
import conexp.core.ExtendedContextEditingInterface;
import conexp.core.Set;
import junit.framework.TestCase;


public class AttributeIteratorTest extends TestCase {


    public static void testIterator() {
        ExtendedContextEditingInterface cxt = SetBuilder.makeContext(new int[][]{{0, 1, 1},
                {1, 1, 0}});
        Set attributes = SetBuilder.makeSet(new int[]{0, 0, 0});
        AttributeIterator iter = new AttributeIterator(cxt, attributes);
        assertEquals(false, iter.hasNext());
        attributes = SetBuilder.makeSet(new int[]{1, 0, 0});
        iter = new AttributeIterator(cxt, attributes);
        assertEquals(true, iter.hasNext());
        assertEquals(cxt.getAttribute(0), iter.next());
        assertEquals(false, iter.hasNext());

        attributes = SetBuilder.makeSet(new int[]{1, 1, 1});
        iter = new AttributeIterator(cxt, attributes);

        int i = 0;
        while (iter.hasNext()) {
            iter.next();
            i++;
        }
        assertEquals(3, i);


    }


}

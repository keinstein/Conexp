/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.components.tests;

import conexp.core.Context;
import conexp.core.ExtendedContextEditingInterface;
import conexp.core.tests.SetBuilder;
import conexp.frontend.EntitiesMask;
import conexp.frontend.components.ContextAttributeMask;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import util.testing.SimpleMockPropertyChangeListener;


public class ContextAttributeMaskTest extends TestCase {
    private Context cxt;
    private EntitiesMask mask;

    protected void setUp() throws Exception {
        cxt = SetBuilder.makeContext(new int[][]{{1, 0, 1}});
        mask = new ContextAttributeMask(cxt);
    }

    public void testAttributeSelection() {
        assertEquals(3, mask.getCount());
        assertEquals(3, selectedItemsInMask(mask));
        mask.setSelected(2, false);
        assertEquals(false, mask.isSelected(2));
        mask.setSelected(2, true);
        assertEquals(true, mask.isSelected(2));
    }

    public void testContextSynchronization() {
        assertEquals(3, mask.getCount());
        assertEquals(3, selectedItemsInMask(mask));
        SimpleMockPropertyChangeListener listener = new SimpleMockPropertyChangeListener(EntitiesMask.ENTITIES_SELECTION_CHANGED);
        mask.addPropertyChangeListener(listener);

        listener.setExpected(1);

        mask.setSelected(1, false);
        listener.verify();

        listener.setExpected(0);
        mask.setSelected(1, false);
        listener.verify();

        assertEquals(2, selectedItemsInMask(mask));
        cxt.removeAttribute(1);
        assertEquals(2, mask.getCount());
        assertEquals(2, selectedItemsInMask(mask));
        cxt.increaseAttributes(2);
        assertEquals(4, mask.getCount());
        assertEquals(2, selectedItemsInMask(mask));
    }

    public void testSynchronyzationWhenContextTransposed() {
        assertEquals(3, mask.getCount());
        cxt.increaseObjects(3);
        assertEquals(4, cxt.getObjectCount());
        SimpleMockPropertyChangeListener listener = new SimpleMockPropertyChangeListener(EntitiesMask.ENTITIES_COUNT_CHANGED);
        mask.addPropertyChangeListener(listener);

        listener.setExpected(1);

        cxt.transpose();

        listener.verify();
        mask.removePropertyChangeListener(listener);
        assertEquals(4, mask.getCount());
        //smoke test
        mask.isSelected(3);
        cxt.transpose();
        assertEquals(3, mask.getCount());

    }


    public void testGetAttributeName() {
        final String FIRST_NAME = "TestName";
        final String SECOND_NAME = "SecondTestName";
        cxt.getAttribute(0).setName(FIRST_NAME);
        assertEquals(FIRST_NAME, mask.getName(0));
        SimpleMockPropertyChangeListener listener = new SimpleMockPropertyChangeListener(EntitiesMask.ENTITIES_NAMES_CHANGED);
        listener.setExpected(1);
        mask.addPropertyChangeListener(listener);
        cxt.getAttribute(0).setName(SECOND_NAME);
        assertEquals(SECOND_NAME, mask.getName(0));
        listener.verify();
    }

    public void testCreateSelectedAttributesSet() {
        mask.setSelected(1, false);
        assertEquals(3, mask.getCount());
        assertEquals(SetBuilder.makeSet(new int[]{1, 0, 1}), getContextAttributeMask().toSet());
        cxt.removeAttribute(0);
        assertEquals(SetBuilder.makeSet(new int[]{0, 1}), getContextAttributeMask().toSet());
    }

    private ContextAttributeMask getContextAttributeMask() {
        return ((ContextAttributeMask) mask);
    }


    static int selectedItemsInMask(EntitiesMask mask) {
        int ret = 0;
        for (int j = mask.getCount(); --j >= 0;) {
            if (mask.isSelected(j)) {
                ret++;
            }
        }
        return ret;
    }

    public void testAttributeCountChangeEvent() {
        SimpleMockPropertyChangeListener listener = new SimpleMockPropertyChangeListener(EntitiesMask.ENTITIES_COUNT_CHANGED);
        mask.addPropertyChangeListener(listener);
        listener.setExpected(1);
        cxt.removeAttribute(2);
        listener.verify();

        listener.setExpected(2);
        cxt.increaseAttributes(2);
        listener.verify();
    }

    public void testEquals() {
        EntitiesMask secondMask = new ContextAttributeMask(cxt);
        assertEquals(mask, secondMask);
        assertEquals(false, mask.equals(new Object()));
        assertEquals(false, mask.equals(null));

        assertEquals(3, mask.getCount());
        ExtendedContextEditingInterface cxt2 = SetBuilder.makeContext(new int[][]{{0, 1}});
        EntitiesMask thirdMask = new ContextAttributeMask(cxt2);
        assertEquals(false, mask.equals(thirdMask));
        secondMask.setSelected(0, false);
        assertEquals(false, mask.equals(secondMask));

        //comparison on context is not done, because context are not part of interface (AT least for now)
    }

    public void testHashCode() {
        EntitiesMask secondMask = new ContextAttributeMask(cxt);
        assertEquals(mask.hashCode(), secondMask.hashCode());
    }


}

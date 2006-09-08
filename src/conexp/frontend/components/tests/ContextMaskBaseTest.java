/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.frontend.components.tests;

import conexp.core.Context;
import conexp.core.ExtendedContextEditingInterface;
import conexp.core.tests.SetBuilder;
import conexp.frontend.EntitiesMask;
import conexp.frontend.SetProvidingEntitiesMask;
import junit.framework.TestCase;
import util.testing.SimpleMockPropertyChangeListener;
import util.testing.TestUtil;



public abstract class ContextMaskBaseTest extends TestCase {
    protected Context cxt;
    protected SetProvidingEntitiesMask mask;

    private static int selectedItemsInMask(EntitiesMask mask) {
        int ret = 0;
        for (int j = mask.getCount(); --j >= 0;) {
            if (mask.isSelected(j)) {
                ret++;
            }
        }
        return ret;
    }

    protected abstract void removeEntity(int index);

    protected abstract void increaseEntities(int incrAttr);

    public void testEntitiesCountChangeEvent() {
        SimpleMockPropertyChangeListener listener = new SimpleMockPropertyChangeListener(EntitiesMask.ENTITIES_COUNT_CHANGED);
        mask.addPropertyChangeListener(listener);
        listener.setExpected(1);
        removeEntity(2);
        listener.verify();

        listener.setExpected(2);
        increaseEntities(2);
        listener.verify();
    }

    protected abstract void changeFirstName(String newName);

    public void testCreateSelectedEntitiesSet() {
        mask.setSelected(1, false);
        assertEquals(3, mask.getCount());
        assertEquals(SetBuilder.makeSet(new int[]{1, 0, 1}), mask.toSet());
        final int index = 0;
        removeEntity(index);
        assertEquals(SetBuilder.makeSet(new int[]{0, 1}), mask.toSet());
    }

    public void testGetName() {
        final String FIRST_NAME = "TestName";
        final String SECOND_NAME = "SecondTestName";
        changeFirstName(FIRST_NAME);
        assertEquals(FIRST_NAME, mask.getName(0));
        SimpleMockPropertyChangeListener listener = new SimpleMockPropertyChangeListener(EntitiesMask.ENTITIES_NAMES_CHANGED);
        listener.setExpected(1);
        mask.addPropertyChangeListener(listener);
        changeFirstName(SECOND_NAME);
        assertEquals(SECOND_NAME, mask.getName(0));
        listener.verify();
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
        removeEntity(1);
        assertEquals(2, mask.getCount());
        assertEquals(2, selectedItemsInMask(mask));
        increaseEntities(2);
        assertEquals(4, mask.getCount());
        assertEquals(2, selectedItemsInMask(mask));
    }

    public void testSelection() {
        assertEquals(3, mask.getCount());
        assertEquals(3, selectedItemsInMask(mask));
        mask.setSelected(2, false);
        assertEquals(false, mask.isSelected(2));
        mask.setSelected(2, true);
        assertEquals(true, mask.isSelected(2));
    }

    public void testSetup() {
        assertEquals(3, mask.getCount());
        assertEquals(1, getOtherEntitiesCount());
    }

    protected abstract int getOtherEntitiesCount();

    protected abstract SetProvidingEntitiesMask makeMask(ExtendedContextEditingInterface cxt);

    public void testEquals() {
        EntitiesMask secondMask = makeMask(cxt);
        assertEquals(mask, secondMask);
        assertEquals(false, mask.equals(new Object()));
        assertEquals(false, mask.equals(null));
        assertEquals(3, mask.getCount());

        ExtendedContextEditingInterface cxt2 = makeContenxtWithNotEqualMask();
        EntitiesMask thirdMask = makeMask(cxt2);
        assertEquals(false, mask.equals(thirdMask));
        secondMask.setSelected(0, false);
        assertEquals(false, mask.equals(secondMask));

        //comparison on context is not done, because context are not part of interface (AT least for now)
    }

    protected abstract Context makeContenxtWithNotEqualMask();

    public void testHashCode() {
        EntitiesMask secondMask = makeMask(cxt);
        assertEquals(mask.hashCode(), secondMask.hashCode());
    }

    public void testSynchronyzationWhenContextTransposed() {
        assertEquals(3, mask.getCount());
        increaseOtherEntities(3);
        assertEquals(4, getOtherEntitiesCount());
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

    protected abstract void increaseOtherEntities(int increment);

    public void testEqualsAndHashCode() {
        SetProvidingEntitiesMask first = makeInstance();
        SetProvidingEntitiesMask second = makeInstance();
        TestUtil.testEqualsAndHashCode(first, second);

    }

    protected abstract SetProvidingEntitiesMask makeInstance();
}

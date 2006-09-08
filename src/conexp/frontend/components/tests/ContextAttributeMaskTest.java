/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.components.tests;

import conexp.core.Context;
import conexp.core.ExtendedContextEditingInterface;
import conexp.core.tests.SetBuilder;
import conexp.frontend.SetProvidingEntitiesMask;
import conexp.frontend.components.ContextAttributeMask;


public class ContextAttributeMaskTest extends ContextMaskBaseTest {

    protected void setUp() throws Exception {
        cxt = SetBuilder.makeContext(new int[][]{{1, 0, 1}});
        mask = makeInstance();
    }

    protected SetProvidingEntitiesMask makeMask(ExtendedContextEditingInterface cxt) {
        return new ContextAttributeMask(cxt);
    }

    protected int getOtherEntitiesCount() {
        return cxt.getObjectCount();
    }


    protected void changeFirstName(String newName) {
        cxt.getAttribute(0).setName(newName);
    }

    protected void removeEntity(final int index) {
        cxt.removeAttribute(index);
    }


    protected void increaseEntities(final int incrAttr) {
        cxt.increaseAttributes(incrAttr);
    }

    protected Context makeContenxtWithNotEqualMask() {
        return SetBuilder.makeContext(new int[][]{{0, 1}});
    }

    protected void increaseOtherEntities(final int increment) {
        cxt.increaseObjects(increment);
    }

    private ContextAttributeMask getMask() {
        return (ContextAttributeMask) mask;
    }


    public void testMakeCopy() {
        SetProvidingEntitiesMask other = getMask().makeCopy();
        assertEquals(mask, other);
        other.setSelected(0, false);
        assertFalse(mask.equals(other));
    }

    protected SetProvidingEntitiesMask makeInstance() {
        return makeMask(cxt);
    }

    public void testCleanUp() {
        int contextListenerCount = cxt.getContextListenersCount();
        getMask().cleanUp();
        assertEquals(contextListenerCount - 1, cxt.getContextListenersCount());
    }
}

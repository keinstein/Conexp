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
import conexp.frontend.SetProvidingEntitiesMask;
import conexp.frontend.components.ContextAttributeMask;
import conexp.frontend.components.BasicMultiSelectionEntityMaskImplementation;
import conexp.frontend.components.ContextObjectMask;
import junit.framework.TestCase;
import util.testing.SimpleMockPropertyChangeListener;


public class ContextObjectMaskTest extends ContextMaskBaseTest {

    protected void setUp() throws Exception {
        cxt = SetBuilder.makeContext(new int[][]{{1},
                                                 {0},
                                                 {1}});
        mask = makeMask(cxt);
    }

    protected SetProvidingEntitiesMask makeMask(ExtendedContextEditingInterface cxt) {
        return new ContextObjectMask(cxt);
    }

    protected int getOtherEntitiesCount() {
        return cxt.getAttributeCount();
    }


    protected void changeFirstName(String newName) {
        cxt.getObject(0).setName(newName);
    }

    protected void removeEntity(final int index) {
        cxt.removeObject(index);
    }

    protected void increaseEntities(final int incrAttr) {
        cxt.increaseObjects(incrAttr);
    }

    protected Context makeContenxtWithNotEqualMask() {
        return SetBuilder.makeContext(new int[][]{{0},
                                                  {1}});
    }

    protected void increaseOtherEntities(final int increment) {
        cxt.increaseAttributes(increment);
    }


}

/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.tests;

import conexp.core.AttributeInformationSupplierUtil;
import conexp.core.BinaryRelation;
import conexp.core.Context;
import conexp.core.ContextChangeEvent;
import conexp.core.ContextEditingInterface;
import conexp.core.ContextEditingInterfaceWithArrowRelations;
import conexp.core.ContextEntity;
import conexp.core.ExtendedContextEditingInterface;
import conexp.core.FCAEngineRegistry;
import junit.framework.TestCase;

import java.beans.PropertyChangeEvent;

public class ContextTest extends TestCase {
    private Context cxt;


    public void testAvailabilityOfArrowRelationInterfaceForDisplay() {
        assertTrue(cxt instanceof ContextEditingInterfaceWithArrowRelations);
    }

    protected void setUp() {
        cxt = SetBuilder.makeContext(new int[][]{{0, 0}, {0, 0}});
    }

    interface ContextStructureModification {
        void modifyContext(Context cxt);
    }

    public void testAddColChange() {
        ContextStructureModification modification = new ContextStructureModification() {
            public void modifyContext(Context cxt) {
                cxt.increaseAttributes(2);
            }
        };

        cxt = SetBuilder.makeContext(new int[][]{{0, 1, 0}, {0, 1, 0}, {0, 1, 0}});
        int expectedNumberOfCalls = 1;

        expectStructureChangedCall(cxt, expectedNumberOfCalls, modification);
    }

    public void testAddRowChange() {
        ContextStructureModification modification = new ContextStructureModification() {
            public void modifyContext(Context cxt) {
                cxt.increaseObjects(2);
            }
        };

        cxt = SetBuilder.makeContext(new int[][]{{0, 1, 0}, {0, 1, 0}, {0, 1, 0}});
        int expectedNumberOfCalls = 1;

        expectStructureChangedCall(cxt, expectedNumberOfCalls, modification);
    }

    public void testTransposeChange() {
        ContextStructureModification modification = new ContextStructureModification() {
            public void modifyContext(Context cxt) {
                cxt.transpose();
            }
        };

        cxt = SetBuilder.makeContext(new int[][]{{0, 1, 0},
                {0, 1, 0},
                {0, 1, 0}});
        int expectedNumberOfCalls = 1;

        expectStructureChangedCall(cxt, expectedNumberOfCalls, modification);
        expectTransposedCall(cxt, expectedNumberOfCalls, modification);
    }

    public void testTransposeChangingTypesOfObjectsAndAttributes() {
        cxt = SetBuilder.makeContext(new int[][]{{0, 1, 0},
                {0, 1, 0}});
        checkObjectAndAttributesIntegrity(cxt);
        cxt.transpose();
        checkObjectAndAttributesIntegrity(cxt);
    }

    private static void checkObjectAndAttributesIntegrity(Context cxt) {
        for (int i = 0; i < cxt.getObjectCount(); i++) {
            assertEquals(true, cxt.getObject(i).isObject());
        }
        for (int i = 0; i < cxt.getAttributeCount(); i++) {
            assertEquals(false, cxt.getAttribute(i).isObject());
        }
    }


    private static void expectTransposedCall(Context cxt, int expectedNumberOfCalls, ContextStructureModification modification) {
        MockContextListener mock = new MockContextListener() {
            public void contextTransposed() {
                incCounter();
            }
        };
        doTestExpectectationOnModification(cxt, mock, expectedNumberOfCalls, modification);

    }

    public void testClarifyObjectsChange() {
        ContextStructureModification modification = new ContextStructureModification() {
            public void modifyContext(Context cxt) {
                cxt.clarifyObjects();
            }
        };

        cxt = SetBuilder.makeContext(new int[][]{{0, 1, 0}, {0, 1, 0}, {0, 1, 0}});
        int expectedNumberOfCalls = 1;

        expectStructureChangedCall(cxt, expectedNumberOfCalls, modification);

    }

    public void testClarifyAttributesChange() {
        ContextStructureModification modification = new ContextStructureModification() {
            public void modifyContext(Context cxt) {
                cxt.clarifyAttributes();
            }
        };

        cxt = SetBuilder.makeContext(new int[][]{{0, 1, 0, 1}, {0, 1, 0, 1}, {0, 1, 0, 1}});
        int expectedNumberOfCalls = 1;

        expectStructureChangedCall(cxt, expectedNumberOfCalls, modification);

    }


    public void testReduceObjectsChange() {
        ContextStructureModification modification = new ContextStructureModification() {
            public void modifyContext(Context cxt) {
                cxt.setArrowCalculator(FCAEngineRegistry.makeArrowCalculator());
                cxt.reduceObjects();
            }
        };

        cxt = SetBuilder.makeContext(new int[][]{
                {1, 1, 0, 0},
                {0, 1, 0, 0},
                {0, 1, 0, 1},
                {0, 1, 0, 0},
                {0, 1, 0, 1},
                {0, 0, 0, 0}});
        int expectedNumberOfCalls = 1;

        expectStructureChangedCall(cxt, expectedNumberOfCalls, modification);

    }


    public void testReduceAttributesChange() {
        ContextStructureModification modification = new ContextStructureModification() {
            public void modifyContext(Context cxt) {
                cxt.setArrowCalculator(FCAEngineRegistry.makeArrowCalculator());
                cxt.reduceAttributes();
            }
        };

        cxt = SetBuilder.makeContext(new int[][]{{0, 1, 0, 1}, {0, 1, 0, 1}, {0, 1, 0, 1}});
        int expectedNumberOfCalls = 1;

        expectStructureChangedCall(cxt, expectedNumberOfCalls, modification);

    }


    private static void expectStructureChangedCall(Context cxt, int expectedNumberOfCalls, ContextStructureModification modification) {
        MockContextListener mock = new MockContextListener() {
            public void contextStructureChanged() {
                incCounter();
            }
        };
        doTestExpectectationOnModification(cxt, mock, expectedNumberOfCalls, modification);
    }

    private static void doTestExpectectationOnModification(Context cxt, MockContextListener mock, int expectedNumberOfCalls, ContextStructureModification modification) {
        cxt.addContextListener(mock);
        mock.setExpectedCalls(expectedNumberOfCalls);
        modification.modifyContext(cxt);
        mock.verify();
        cxt.removeContextListener(mock);
    }

    public void testStructureChangeInContextListener() {
        MockContextListener mock = new MockContextListener() {
            public void contextStructureChanged() {
                incCounter();
            }
        };

        mock.setExpectedCalls(1);
        cxt.addContextListener(mock);
        cxt.setDimension(3, 2);
        mock.verify();
        assertEquals(3, cxt.getObjectCount());
        assertEquals(2, cxt.getAttributeCount());

        mock.setExpectedCalls(1);
        cxt.removeObject(1);
        mock.verify();
        assertEquals(2, cxt.getObjectCount());
        assertEquals(2, cxt.getAttributeCount());


        mock.setExpectedCalls(1);
        cxt.increaseObjects(1);
        mock.verify();
        assertEquals(3, cxt.getObjectCount());
        assertEquals(2, cxt.getAttributeCount());

        mock.setExpectedCalls(1);
        cxt.setDimension(2, 2);
        mock.verify();
        assertEquals(2, cxt.getObjectCount());
        assertEquals(2, cxt.getAttributeCount());


        mock.setExpectedCalls(1);
        cxt.increaseAttributes(1);
        mock.verify();
        assertEquals(2, cxt.getObjectCount());
        assertEquals(3, cxt.getAttributeCount());

        mock.setExpectedCalls(1);
        cxt.removeAttribute(1);
        mock.verify();
        assertEquals(2, cxt.getObjectCount());
        assertEquals(2, cxt.getAttributeCount());
    }

    public void testRelationChangeInContextListener() {
        MockContextListener mock = new MockContextListener() {
            public void relationChanged() {
                counter.inc();
            }
        };

        mock.setExpectedCalls(1);
        cxt.addContextListener(mock);
        cxt.setRelationAt(0, 0, true);
        mock.verify();
        assertEquals(true, cxt.getRelationAt(0, 0));

        mock.setExpectedCalls(0);
        cxt.setRelationAt(0, 0, true);
        mock.verify();

        mock.setExpectedCalls(0);
        cxt.removeContextListener(mock);
        cxt.setRelationAt(0, 0, false);
        mock.verify();
        assertEquals(false, cxt.getRelationAt(0, 0));
    }

    public void testObjectNameChangeInContextListener() {
        MockContextListener mockObjectListener = makeObjectChangeListener();
        MockContextListener mockAttributeListener = makeAttributeChangeListener();


        cxt.addContextListener(mockObjectListener);
        cxt.addContextListener(mockAttributeListener);

        mockObjectListener.setExpectedCalls(1);
        mockAttributeListener.setExpectedCalls(0);
        final String newObjectName = "New OName";
        assertEquals(false, newObjectName.equals(cxt.getObject(0).getName()));
        cxt.getObject(0).setName(newObjectName);
        assertEquals(newObjectName, cxt.getObject(0).getName());
        mockObjectListener.verify();
        mockAttributeListener.verify();

        mockObjectListener.setExpectedCalls(0);
        mockAttributeListener.setExpectedCalls(1);
        final String newAttributeName = "New AName";
        assertEquals(false, newAttributeName.equals(cxt.getAttribute(0).getName()));
        cxt.getAttribute(0).setName(newAttributeName);
        assertEquals(newAttributeName, cxt.getAttribute(0).getName());
        mockObjectListener.verify();
        mockAttributeListener.verify();

        mockObjectListener.setExpectedCalls(0);
        mockAttributeListener.setExpectedCalls(0);
        ContextEntity obj2 = cxt.getObject(1);
        final String newSecondObjectName = "New SOName";
        assertEquals(false, newSecondObjectName.equals(obj2.getName()));
        cxt.removeObject(1);
        obj2.setName(newSecondObjectName);
        assertEquals(newSecondObjectName, obj2.getName());
        mockObjectListener.verify();
        mockAttributeListener.verify();

        mockObjectListener.setExpectedCalls(0);
        mockAttributeListener.setExpectedCalls(0);
        ContextEntity attr2 = cxt.getAttribute(1);
        final String newSecondAttributeName = "New SAName";
        assertEquals(false, newSecondAttributeName.equals(obj2.getName()));
        cxt.removeAttribute(1);
        attr2.setName(newSecondAttributeName);
        assertEquals(newSecondAttributeName, attr2.getName());
        mockObjectListener.verify();
        mockAttributeListener.verify();

    }

    private static MockContextListener makeObjectChangeListener() {
        return new MockContextListener() {
            public void objectNameChanged(PropertyChangeEvent evt) {
                counter.inc();
            }

        };
    }

    private static MockContextListener makeAttributeChangeListener() {
        return new MockContextListener() {
            public void attributeNameChanged(PropertyChangeEvent evt) {
                counter.inc();
            }
        };
    }

    public void testDescribeSet() {
        cxt.getAttribute(0).setName("one");
        cxt.getAttribute(1).setName("two");

        final String emptySetDescr = "{}";
        final String separator = " , ";
        doTestDescribeSet(new int[]{1, 0}, "one", separator, emptySetDescr);
        doTestDescribeSet(new int[]{0, 0}, emptySetDescr, separator, emptySetDescr);
        doTestDescribeSet(new int[]{1, 1}, "one , two", separator, emptySetDescr);

    }

    private void doTestDescribeSet(int[] set, String expString, String separator, String emptySetDescr) {
        String res = AttributeInformationSupplierUtil.describeSet(cxt, SetBuilder.makeSet(set), separator, emptySetDescr);
        assertEquals(expString, res);
    }

    public void testEquals() {

        final int[][] relation = new int[][]{{1, 0},
                {0, 1}};
        ExtendedContextEditingInterface cxt1 = SetBuilder.makeContext(relation);
        ExtendedContextEditingInterface cxt2 = SetBuilder.makeContext(relation);

        assertEquals(cxt1, cxt2);

        assertEquals(false, cxt1.equals(new Object()));

        assertEquals(false, cxt1.equals(null));

        cxt1.setRelationAt(0, 1, true);

        assertEquals(false, cxt1.equals(cxt2));

        cxt1.setRelationAt(0, 1, false);
        assertEquals(cxt1, cxt2);

        String oldName = cxt1.getAttribute(0).getName();
        cxt1.getAttribute(0).setName("Not a default name for attribute");
        assertEquals(false, cxt1.equals(cxt2));

        cxt1.getAttribute(0).setName(oldName);
        assertEquals(cxt1, cxt2);

        cxt1.getObject(0).setName("Not a default name for object");
        assertEquals(false, cxt1.equals(cxt2));

    }

    public void testAddAttribute() {
        cxt = new Context(0, 0);

        ContextStructureModification modification = new ContextStructureModification() {
            public void modifyContext(Context cxt) {
                cxt.addAttribute(ContextEntity.createContextAttribute("One"));
            }
        };

        expectStructureChangedCall(cxt, 1, modification);

        assertEquals(1, cxt.getAttributeCount());

        MockContextListener listener = makeAttributeChangeListener();
        cxt.addContextListener(listener);
        listener.setExpectedCalls(1);
        cxt.getAttribute(0).setName("Other name");
        listener.verify();
    }

    public void testAddObject() {
        cxt = new Context(0, 0);
        ContextStructureModification modification = new ContextStructureModification() {
            public void modifyContext(Context cxt) {
                cxt.addObject(ContextEntity.createContextObject("New object"));
            }
        };

        expectStructureChangedCall(cxt, 1, modification);

        assertEquals(1, cxt.getObjectCount());

        MockContextListener listener = makeObjectChangeListener();
        cxt.addContextListener(listener);
        listener.setExpectedCalls(1);
        cxt.getObject(0).setName("Other object name");
        listener.verify();
    }

    private static MockContextListener makeAttributeInsertionListener() {
        return new MockAttributeChangeContextListener(ContextChangeEvent.ATTRIBUTE_ADDED);
    }


    private static MockContextListener makeAttributeRemovalListener() {
        return new MockAttributeChangeContextListener(ContextChangeEvent.ATTRIBUTE_REMOVED);
    }

    private static MockContextListener makeObjectInsertionListener() {
        return new MockObjectChangeContextListener(ContextChangeEvent.OBJECT_ADDED);
    }

    private static MockContextListener makeObjectRemovalListener() {
        return new MockObjectChangeContextListener(ContextChangeEvent.OBJECT_REMOVED);
    }


    public void testAttributeAddRemoveNotification() {
        cxt = new Context(2, 3);

        MockContextListener insertionListener = makeAttributeInsertionListener();
        cxt.addContextListener(insertionListener);

        MockContextListener removalListener = makeAttributeRemovalListener();
        cxt.addContextListener(removalListener);

        insertionListener.setExpectedCalls(2);
        cxt.increaseAttributes(2);
        insertionListener.verify();

        insertionListener.setExpectedCalls(1);
        cxt.addAttribute(ContextEntity.createContextAttribute("New Attribute"));
        insertionListener.verify();

        removalListener.setExpectedCalls(1);
        cxt.removeAttribute(0);
        removalListener.verify();

        assertEquals(5, cxt.getAttributeCount());
        removalListener.setExpectedCalls(2);
        cxt.setDimension(1, 3);
        removalListener.verify();

        insertionListener.setExpectedCalls(3);
        cxt.setDimension(2, 6);
        insertionListener.verify();
    }

    public void testObjectAddRemoveNotification() {
        cxt = new Context(2, 3);

        MockContextListener insertionListener = makeObjectInsertionListener();
        cxt.addContextListener(insertionListener);

        MockContextListener removalListener = makeObjectRemovalListener();
        cxt.addContextListener(removalListener);

        insertionListener.setExpectedCalls(2);
        cxt.increaseObjects(2);
        insertionListener.verify();

        insertionListener.setExpectedCalls(1);
        cxt.addObject(ContextEntity.createContextObject("New Object"));
        insertionListener.verify();

        removalListener.setExpectedCalls(1);
        cxt.removeObject(0);
        removalListener.verify();

        assertEquals(4, cxt.getObjectCount());
        removalListener.setExpectedCalls(3);
        cxt.setDimension(1, 3);
        removalListener.verify();

        insertionListener.setExpectedCalls(3);
        cxt.setDimension(4, 3);
        insertionListener.verify();
    }


    public void testArrowRelationUpdateOnAttributeRemoval() {
        cxt = SetBuilder.makeContext(new int[][]{{1, 0, 1}});
        BinaryRelation oldUpArrowRelation = cxt.getUpArrow();
        BinaryRelation oldDownArrownRelation = cxt.getDownArrow();
        assertEquals(3, oldUpArrowRelation.getColCount());
        assertEquals(3, oldDownArrownRelation.getColCount());
        cxt.removeAttribute(1);
        assertEquals(2, cxt.getUpArrow().getColCount());
        assertEquals(2, cxt.getDownArrow().getColCount());
    }

    public void testUpDownArrowUpdateOnRelationChange() {
        cxt = SetBuilder.makeContext(new int[][]{{1, 0},
                {1, 1}});
        assertEquals(SetBuilder.makeRelation(new int[][]{{0, 1},
                {0, 0}}), cxt.getUpArrow());
        assertEquals(SetBuilder.makeRelation(new int[][]{{0, 1},
                {0, 0}}), cxt.getDownArrow());

        cxt.setRelationAt(0, 1, true);
        assertEquals(SetBuilder.makeRelation(new int[][]{{0, 0},
                {0, 0}}), cxt.getUpArrow());

        assertEquals(SetBuilder.makeRelation(new int[][]{{0, 0},
                {0, 0}}), cxt.getDownArrow());
    }

    public void testArrowRelationUpdateOnDimensionChange() {
        checkRelationSizes(cxt.getUpArrow(), 2, 2);
        checkRelationSizes(cxt.getDownArrow(), 2, 2);

        cxt.setDimension(3, 4);
        checkRelationSizes(cxt.getUpArrow(), 3, 4);
        checkRelationSizes(cxt.getDownArrow(), 3, 4);
    }

    public void testArrowRelationUpdateOnObjectRemoval() {

        final BinaryRelation upArrow = cxt.getUpArrow();
        checkRelationSizes(upArrow, 2, 2);
        checkRelationSizes(cxt.getDownArrow(), 2, 2);
        cxt.removeObject(1);
        checkRelationSizes(cxt.getUpArrow(), 1, 2);
        assertSame(upArrow, cxt.getUpArrow());
        checkRelationSizes(cxt.getDownArrow(), 1, 2);

    }

    private static void checkRelationSizes(final BinaryRelation binaryRelation, final int expRowCount, final int expColCount) {
        assertEquals(expColCount, binaryRelation.getColCount());
        assertEquals(expRowCount, binaryRelation.getRowCount());
    }

    public void testArrowRelationCalculation() {

        cxt = SetBuilder.makeContext(new int[][]{{0, 0, 0},
                {0, 0, 0}});
        assertEquals(SetBuilder.makeRelation(new int[][]{{1, 1, 1},
                {1, 1, 1}}), cxt.getUpArrow());

        cxt.setRelationAt(0, 0, true);
        assertEquals(SetBuilder.makeRelation(new int[][]{{0, 1, 1},
                {1, 0, 0}}), cxt.getUpArrow());

        cxt.setRelationAt(0, 1, true);
        assertEquals(SetBuilder.makeRelation(new int[][]{{0, 0, 1},
                {1, 1, 0}}), cxt.getUpArrow());

        cxt.setRelationAt(1, 0, true);
        assertEquals(SetBuilder.makeRelation(new int[][]{{0, 0, 1},
                {0, 1, 0}}), cxt.getUpArrow());
    }


    public void testCopy() {
        final int[][] CONTEXT_DATA = new int[][]{{0, 0, 1},
                {1, 0, 0}};
        cxt = SetBuilder.makeContext(CONTEXT_DATA);
        ContextEditingInterface other = cxt.makeCopy();
        assertEquals(SetBuilder.makeContext(CONTEXT_DATA), other);
        assertEquals(cxt, other);

        assertNotNull(cxt.getArrowCalculator());
        Context otherCxt = (Context) other;
        assertNotNull(otherCxt.getArrowCalculator());

        checkArrowCalculatorsIndependent(otherCxt);
    }

    private void checkArrowCalculatorsIndependent(Context otherCxt) {
        assertFalse(cxt.hasUpArrow(1, 1));
        otherCxt.reduceAttributes();
        cxt.setRelationAt(1, 2, true);
        cxt.setRelationAt(1, 2, false);
        assertFalse(cxt.hasUpArrow(1, 1));
    }

    public void testChangeOfTypeOnTranspose() {
        final int[][] CONTEXT_DATA = new int[][]{{0, 0, 1},
                {1, 0, 0}};
        cxt = SetBuilder.makeContext(CONTEXT_DATA);
        cxt.transpose();
        for (int i = 0; i < cxt.getObjectCount(); i++) {
            assertTrue(cxt.getObject(i).isObject());
        }
        for (int i = 0; i < cxt.getAttributeCount(); i++) {
            assertFalse(cxt.getAttribute(i).isObject());
        }
    }

    private static void expectRelationChangedCall(Context cxt, int expectedNumberOfCalls, ContextStructureModification modification) {
        MockContextListener mock = new MockContextListener() {
            public void relationChanged() {
                incCounter();
            }
        };
        doTestExpectectationOnModification(cxt, mock, expectedNumberOfCalls, modification);
    }

    public void testFireOfRelationChangeOnAddObjectWithNameAndIntent() {
        ContextStructureModification modification = new ContextStructureModification() {
            public void modifyContext(Context cxt) {
                cxt.addObjectWithNameAndIntent("Fourth", SetBuilder.makeSet(new int[]{1, 1, 1}));
            }
        };

        cxt = SetBuilder.makeContext(new int[][]{{0, 1, 0}, {0, 1, 0}, {0, 1, 0}});
        expectRelationChangedCall(cxt, 1, modification);
        expectStructureChangedCall(cxt, 1, modification);
    }

    public void testExpectStructureChangeOnCopyFrom() {
        ContextStructureModification modification = new ContextStructureModification() {
            public void modifyContext(Context cxt) {
                cxt.copyFrom(SetBuilder.makeContext(new int[][]{{0, 1, 0},
                        {1, 0, 0},
                        {0, 1, 0}}));
            }
        };

        cxt = SetBuilder.makeContext(new int[][]{{0, 1, 0}, {0, 1, 0}, {0, 1, 0}});
        expectRelationChangedCall(cxt, 1, modification);

    }

    public void testUpdateOfArrowCalculatorOnCopyFrom() {
        cxt = SetBuilder.makeContext(new int[][]{{0}});
        assertTrue(cxt.hasUpArrow(0, 0));
        Context otherCxt = SetBuilder.makeContext(new int[][]{{1}});
        assertFalse(otherCxt.hasUpArrow(0, 0));
        cxt.copyFrom(otherCxt);
        assertFalse(cxt.hasUpArrow(0, 0));
    }

    public void testUpdateOfArrowCalculatorOnTranspose() {
        cxt = SetBuilder.makeContext(new int[][]{{1, 0},
                {1, 0}});
        assertTrue(cxt.hasUpArrow(0, 1));
        cxt.transpose();
        assertFalse(cxt.hasUpArrow(0, 1));
    }

    public void testDownArrow() {
        cxt = SetBuilder.makeContext(new int[0][0]);
        assertEquals(0, cxt.getAttributeCount());
        assertEquals(0, cxt.getObjectCount());

        assertEquals(0, cxt.getDownArrow().getColCount());
        assertEquals(0, cxt.getDownArrow().getRowCount());

    }

    public void testReduceAttributesAndObjects() {
        cxt = SetBuilder.makeContext(new int[][]{{1}});
        cxt.reduceAttributes();
        assertEquals(0, cxt.getAttributeCount());
        assertEquals(0, cxt.getRelation().getColCount());
        assertEquals(1, cxt.getRelation().getRowCount());

        cxt.reduceObjects();
        assertEquals(0, cxt.getObjectCount());
    }

    public void testIndexOfAttribute() {
        cxt = SetBuilder.makeContextWithAttributeNames(new String[]{"One", "Two"}, new int[][]{
                {1, 0}
        });
        assertEquals(-1, cxt.indexOfAttribute("Three"));
        assertEquals(1, cxt.indexOfAttribute("Two"));

    }

    private static class MockAttributeChangeContextListener extends MockContextListener {
        private final int EVENT_TYPE;

        public MockAttributeChangeContextListener(int EVENT_TYPE) {
            this.EVENT_TYPE = EVENT_TYPE;
        }

        public void attributeChanged(ContextChangeEvent changeEvent) {
            if (changeEvent.getType() == EVENT_TYPE) {
                counter.inc();
            }
        }
    }

    private static class MockObjectChangeContextListener extends MockContextListener {
        private final int EVENT_TYPE;

        public MockObjectChangeContextListener(int EVENT_TYPE) {
            this.EVENT_TYPE = EVENT_TYPE;
        }

        public void objectChanged(ContextChangeEvent changeEvent) {
            if (changeEvent.getType() == EVENT_TYPE) {
                counter.inc();
            }
        }
    }
}

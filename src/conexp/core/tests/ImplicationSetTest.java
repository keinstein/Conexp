/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.tests;

import conexp.core.AttributeInformationSupplier;
import conexp.core.Context;
import conexp.core.Implication;
import conexp.core.ImplicationSet;
import conexp.core.ModifiableSet;
import junit.framework.TestCase;

import java.util.Iterator;


public class ImplicationSetTest extends TestCase {
    private ImplicationSet impSet;
    private Implication imp;

    protected void setUp() {
        impSet = new ImplicationSet(new Context(4, 8));

        imp = makeImplication(new int[]{1, 0, 0, 0}, new int[]{0, 1, 0, 0}, 5);
        impSet.addDependency(imp);

        imp = makeImplication(new int[]{0, 1, 0, 0}, new int[]{0, 0, 1, 0}, 10);
        impSet.addDependency(imp);
    }

    public void testMakeDuquenneGuigues() {
        final MockAttributeInformationSupplier attrInfo = new MockAttributeInformationSupplier(4);
        impSet = ImplicationsBuilder.makeImplicationSet(attrInfo, new int[][][]{
                {{1, 0, 0, 0}, {0, 1, 0, 0}},
                {{0, 1, 0, 0}, {0, 0, 1, 0}}
        });
        impSet.makeDuquenneGuigues();
        assertEquals(2, impSet.getSize());

        imp = makeImplication(new int[]{1, 0, 0, 0}, new int[]{0, 0, 1, 0});
        impSet.addDependency(imp);
        impSet.makeDuquenneGuigues();
        assertEquals(false, impSet.dependencies().contains(imp));

        imp = makeImplication(new int[]{0, 1, 0, 0}, new int[]{0, 0, 0, 1});
        impSet.addDependency(imp);
        impSet.makeDuquenneGuigues();
        assertEquals(impSet, ImplicationsBuilder.makeImplicationSet(attrInfo,
                new int[][][]{
                        {{1, 0, 0, 0}, {0, 1, 1, 1}},
                        {{0, 1, 0, 0}, {0, 0, 1, 1}},
                }));
    }

    public static void testMakeDuquenneGuigues2() {
        MockAttributeInformationSupplier supplier = new MockAttributeInformationSupplier(2);
        ImplicationSet implications = ImplicationsBuilder.makeImplicationSet(supplier, new int[][][]{
                {{0, 0}, {0, 1}},
                {{0, 1}, {1, 0}}
        });
        implications.makeDuquenneGuigues();
        ImplicationSet expected = ImplicationsBuilder.makeImplicationSet(supplier, new int[][][]{
                {{0, 0}, {1, 1}}
        });
        assertEquals(expected, implications);
    }


    public void testIsDerived() {
        assertTrue(impSet.isDerived(imp));
        assertEquals(SetBuilder.makeSet(new int[]{0, 1, 0, 0}), imp.getPremise());

        imp = makeImplication(new int[]{1, 0, 0, 0}, new int[]{0, 0, 1, 0});
        assertEquals(true, impSet.isDerived(imp));
        assertEquals(SetBuilder.makeSet(new int[]{1, 0, 0, 0}), imp.getPremise());

        imp = makeImplication(new int[]{0, 0, 1, 0}, new int[]{1, 0, 0, 0});
        assertEquals(false, impSet.isDerived(imp));
        assertEquals(SetBuilder.makeSet(new int[]{0, 0, 1, 0}), imp.getPremise());
    }

    public void testMakeDisjoint() {
        impSet.addDependency(makeImplication(new int[]{0, 1, 0, 0}, new int[]{0, 0, 1, 0}));
        impSet.makeDisjoint();
        Iterator it = impSet.dependencies().iterator();
        while (it.hasNext()) {
            assertTrue(((Implication) it.next()).isDisjoint());
        } // end of while ()
    }

    public void testNonRedundant() {
        impSet.makeNonRedundant();
        assertEquals(2, impSet.getSize());

        imp = makeImplication(new int[]{1, 0, 0, 0}, new int[]{0, 0, 1, 0});
        impSet.addDependency(imp);
        impSet.makeNonRedundant();
        assertEquals(false, impSet.dependencies().contains(imp));
    }

    private static Implication makeImplication(int[] premise, int[] conclusion, int count) {
        //used instead of SetBuilder.makeImplication due to the reason, that we are testing this class, and class in Set builder can change
        return new Implication(SetBuilder.makeSet(premise), SetBuilder.makeSet(conclusion), count);
    }

    private static Implication makeImplication(int[] premise, int[] conclusion) {
        return makeImplication(premise, conclusion, 0);
    }

    public void testSetClosure() {
        doTestSetClosure(new int[]{0, 1, 0, 0}, new int[]{0, 1, 1, 0});
        doTestSetClosure(new int[]{1, 1, 0, 0}, new int[]{1, 1, 1, 0});
        doTestSetClosure(new int[]{0, 0, 0, 1}, new int[]{0, 0, 0, 1});
    }

    private void doTestSetClosure(int[] setToClose, int[] expectedResultOfClosure) {
        ModifiableSet toClose = SetBuilder.makeSet(setToClose);
        impSet.setClosure(toClose);
        assertEquals(SetBuilder.makeSet(expectedResultOfClosure), toClose);
    }

    /**
     * Insert the method's description here.
     * Creation date: (23.11.00 0:05:19)
     */
    public void testSupportSort() {
        assertEquals(5, impSet.getImplication(0).getObjectCount());
        assertEquals(10, impSet.getImplication(1).getObjectCount());
        //checking sorting
        impSet.sort(ImplicationSet.supportDescComparator);
        assertEquals(10, impSet.getImplication(0).getObjectCount());
        assertEquals(5, impSet.getImplication(1).getObjectCount());

        //checking stabilityToDesctruction of sorting
        impSet.sort(ImplicationSet.supportDescComparator);
        assertEquals(10, impSet.getImplication(0).getObjectCount());
        assertEquals(5, impSet.getImplication(1).getObjectCount());
    }

    public static void testEqualToIsomorphism() {
        AttributeInformationSupplier supplier = new MockAttributeInformationSupplier(4);
        ImplicationSet implicationSet1 = new ImplicationSet(supplier);
        ImplicationSet implicationSet2 = new ImplicationSet(supplier);
        assertEquals(true, implicationSet1.equalsToIsomorphism(implicationSet2));

        implicationSet1 = ImplicationsBuilder.makeImplicationSet(supplier, new int[][][]{{{1, 0, 0, 0}, {0, 1, 0, 0}}});
        implicationSet2 = ImplicationsBuilder.makeImplicationSet(supplier, new int[][][]{{{1, 0, 0, 0}, {0, 1, 0, 0}}});
        assertEquals(true, implicationSet1.equalsToIsomorphism(implicationSet2));

        //unequal instances
        implicationSet1 = ImplicationsBuilder.makeImplicationSet(supplier, new int[][][]{{{1, 0, 0, 0}, {0, 1, 0, 0}}});
        implicationSet2 = ImplicationsBuilder.makeImplicationSet(supplier, new int[][][]{{{1, 0, 0, 0}, {0, 1, 1, 0}}});
        assertEquals(false, implicationSet1.equalsToIsomorphism(implicationSet2));


        implicationSet1 = ImplicationsBuilder.makeImplicationSet(supplier, new int[][][]{{{1, 0, 0, 0}, {0, 1, 0, 0}},
                {{0, 1, 0, 0}, {0, 0, 1, 1}}});
        implicationSet2 = ImplicationsBuilder.makeImplicationSet(supplier, new int[][][]{{{0, 1, 0, 0}, {0, 0, 1, 1}},
                {{1, 0, 0, 0}, {0, 1, 0, 0}}});
        assertEquals(true, implicationSet1.equalsToIsomorphism(implicationSet2));

        implicationSet1 = ImplicationsBuilder.makeImplicationSet(supplier, new int[][][]{{{1, 0, 0, 0}, {0, 1, 0, 0}},
                {{0, 1, 0, 0}, {0, 0, 1, 1}},
                {{0, 1, 0, 0}, {0, 0, 1, 1}}});

        implicationSet2 = ImplicationsBuilder.makeImplicationSet(supplier, new int[][][]{{{0, 1, 0, 0}, {0, 0, 1, 1}},
                {{1, 0, 0, 0}, {0, 1, 0, 0}},
                {{0, 0, 1, 0}, {0, 0, 0, 1}}});
        assertEquals(false, implicationSet1.equalsToIsomorphism(implicationSet2));
    }

    public static void testEquals() {
        MockAttributeInformationSupplier supplier = new MockAttributeInformationSupplier(3);
        ImplicationSet one = new ImplicationSet(supplier);
        ImplicationSet two = new ImplicationSet(supplier);
        assertEquals(one, two);
        assertEquals(false, one.equals(null));
        assertEquals(false, one.equals(new Object()));


        one.addImplication(makeImplication(new int[]{0, 0, 0}, new int[]{1, 1, 1}));
        assertEquals(false, one.equals(two));

    }

    public void testMakeCompatibleDependencySet() {
        assertTrue(impSet.makeCompatibleDependencySet() instanceof ImplicationSet);
    }

}

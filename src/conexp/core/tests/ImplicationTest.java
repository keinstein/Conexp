/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.tests;

import conexp.core.Implication;


public class ImplicationTest extends DependencyTest {

    private Implication impOverlap;
    private Implication impDisjoint;


    protected void setUp() {
        impDisjoint = ImplicationsBuilder.makeImplication(new int[]{1, 0, 0, 0}, new int[]{0, 1, 0, 0}, 10);
        impOverlap = ImplicationsBuilder.makeImplication(new int[]{1, 0, 0, 0}, new int[]{1, 1, 0, 0}, 10);
    }

    public void testEquals() {
        assertTrue("Self equals failure", impOverlap.equals(impOverlap));
        assertEquals("Equality to zero failure", false, impOverlap.equals(null));
        assertEquals("Equality to other type failure", false, impOverlap.equals(new Object()));
        assertTrue("Clone equals failure", impOverlap.equals(impOverlap.clone()));
        assertEquals("Not equals failure", false, impOverlap.equals(impDisjoint));
    }

    public void testIsDisjoint() {
        assertEquals(false, impOverlap.isDisjoint());
        assertTrue(impDisjoint.isDisjoint());
    }

    public void testMakeDisjoint() {
        impOverlap.makeDisjoint();
        assertEquals(impDisjoint, impOverlap);
        Implication tmp = (Implication) impDisjoint.clone();
        impDisjoint.makeDisjoint();
        assertEquals(tmp, impDisjoint);
    }

    public void testInvariants() {
        checkInvariants(impDisjoint);
        checkInvariants(impOverlap);
    }

    public void testIsRespectedBySet() {
        //implication is respected by set,if from premise \subseteq set => conclusion \subseteq set

        //test when set is not subset of premise
        assertTrue(impDisjoint.isRespectedBySet(SetBuilder.makeSet(new int[]{0, 0, 0, 0})));
        // normal case
        assertTrue(impDisjoint.isRespectedBySet(SetBuilder.makeSet(new int[]{1, 1, 0, 0})));
        //bigger case
        assertTrue(impDisjoint.isRespectedBySet(SetBuilder.makeSet(new int[]{1, 1, 1, 1})));

        assertEquals(false, impDisjoint.isRespectedBySet(SetBuilder.makeSet(new int[]{1, 0, 0, 0})));

        //test of implication for all objects

        Implication common = ImplicationsBuilder.makeImplication(new int[]{0, 0, 0, 0}, new int[]{1, 1, 0, 0}, 10);

        assertEquals(false, common.isRespectedBySet(SetBuilder.makeSet(new int[]{0, 0, 0, 0})));
        assertEquals(true, common.isRespectedBySet(SetBuilder.makeSet(new int[]{1, 1, 0, 1})));

    }
}

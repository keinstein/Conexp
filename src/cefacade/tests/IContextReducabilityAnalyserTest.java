/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package cefacade.tests;

import cefacade.CEFacadeFactory;
import cefacade.IContextReducabilityAnalyser;
import cefacade.IEntityReducibilityInfo;
import cefacade.ISimpleContext;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import util.IntArrayWrapper;

import java.util.Collection;


public class IContextReducabilityAnalyserTest extends TestCase {
    private static final Class THIS = IContextReducabilityAnalyserTest.class;

    public static Test suite() {
        return new TestSuite(THIS);
    }

    public void testContextAnalyserDecompositionInEquivalentObjectsAndAttributes() {
        ISimpleContext context = CEFacadeFactory.makeContext(3, 3);

        IContextReducabilityAnalyser contextAnalyser = CEFacadeFactory.makeContextAnalyser(context);
        IEntityReducibilityInfo objectReducabilityInfo = contextAnalyser.getObjectReducabilityInfo(0);
        assertTrue(objectReducabilityInfo.isIrreducible());
        assertEquals(IntArrayWrapper.toList(new int[]{0, 1, 2}), objectReducabilityInfo.getClassOfEquivalence());

        IEntityReducibilityInfo attributeReducibilityInfo = contextAnalyser.getAttributeReducabilityInfo(0);
        assertEquals(IntArrayWrapper.toList(new int[]{0, 1, 2}), attributeReducibilityInfo.getClassOfEquivalence());

        context.setRelationAt(0, 0, true);

        assertTrue(contextAnalyser.getObjectReducabilityInfo(0).isIrreducible());
        assertEquals(IntArrayWrapper.toList(new int[]{0}), contextAnalyser.getObjectReducabilityInfo(0).getClassOfEquivalence());

        assertTrue(contextAnalyser.getAttributeReducabilityInfo(0).isIrreducible());
        assertEquals(IntArrayWrapper.toList(new int[]{0}), contextAnalyser.getAttributeReducabilityInfo(0).getClassOfEquivalence());
    }


    public static void testRecalcalationOnAdditionOfObjectOrAttribute() {
        ISimpleContext context = CEFacadeFactory.makeContext(3, 3);

        IContextReducabilityAnalyser contextAnalyser = CEFacadeFactory.makeContextAnalyser(context);
        IEntityReducibilityInfo objectReducabilityInfo = contextAnalyser.getObjectReducabilityInfo(0);
        assertTrue(objectReducabilityInfo.isIrreducible());
        assertEquals(IntArrayWrapper.toList(new int[]{0, 1, 2}), objectReducabilityInfo.getClassOfEquivalence());

        IEntityReducibilityInfo attributeReducibilityInfo = contextAnalyser.getAttributeReducabilityInfo(0);
        assertEquals(IntArrayWrapper.toList(new int[]{0, 1, 2}), attributeReducibilityInfo.getClassOfEquivalence());

        context.setDimension(4, 3);
        assertEquals(IntArrayWrapper.toList(new int[]{0, 1, 2, 3}), contextAnalyser.getObjectReducabilityInfo(0).getClassOfEquivalence());
        assertEquals(IntArrayWrapper.toList(new int[]{0, 1, 2}), contextAnalyser.getAttributeReducabilityInfo(0).getClassOfEquivalence());

        context.setDimension(4, 4);
        assertEquals(IntArrayWrapper.toList(new int[]{0, 1, 2, 3}), contextAnalyser.getAttributeReducabilityInfo(0).getClassOfEquivalence());

    }

    public static void testCorrectDefinitionOfIrreducibilityInformation() {
        ISimpleContext context = ObjectMother.buildContext(new int[][]{
                {1, 1, 0},
                {0, 1, 0},
                {0, 1, 1}
        });

        IContextReducabilityAnalyser contextAnalyser = CEFacadeFactory.makeContextAnalyser(context);
        assertTrue(contextAnalyser.getObjectReducabilityInfo(0).isIrreducible());
        assertFalse(contextAnalyser.getObjectReducabilityInfo(1).isIrreducible());

        Collection reducingObjectClasses = contextAnalyser.getObjectReducabilityInfo(1).getReducingClasses();
        assertTrue(reducingObjectClasses.contains(contextAnalyser.getObjectReducabilityInfo(0)));
        assertTrue(reducingObjectClasses.contains(contextAnalyser.getObjectReducabilityInfo(2)));

        assertTrue(contextAnalyser.getAttributeReducabilityInfo(0).isIrreducible());
        assertFalse(contextAnalyser.getAttributeReducabilityInfo(1).isIrreducible());
        assertTrue(contextAnalyser.getAttributeReducabilityInfo(2).isIrreducible());
    }

}

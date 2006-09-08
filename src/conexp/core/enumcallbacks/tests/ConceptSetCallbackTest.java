/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.enumcallbacks.tests;

import conexp.core.ConceptsCollection;
import conexp.core.ModifiableSet;
import conexp.core.enumcallbacks.ConceptSetCallback;
import conexp.core.tests.SetBuilder;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


public class ConceptSetCallbackTest extends TestCase {
    private static final Class THIS = ConceptSetCallbackTest.class;

    public static Test suite() {
        return new TestSuite(THIS);
    }


    public void testMakeConcept() {
        ConceptsCollection col = new ConceptsCollection();

        ConceptSetCallback callback = new ConceptSetCallback(col);

        ModifiableSet objects = SetBuilder.makeSet(new int[]{0, 0, 1});
        ModifiableSet attribs = SetBuilder.makeSet(new int[]{1, 1, 1, 1});

        callback.addConcept(objects, attribs);

        objects.copy(SetBuilder.makeSet(new int[]{0, 1, 0}));
        attribs.copy(SetBuilder.makeSet(new int[]{0, 0, 0, 0}));
        callback.addConcept(objects, attribs);

        assertTrue(!col.conceptAt(0).getAttribs().equals(col.conceptAt(1).getAttribs()));
        assertTrue(!col.conceptAt(0).getObjects().equals(col.conceptAt(1).getObjects()));
    }
}

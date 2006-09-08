/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.tests;

import com.mockobjects.ExpectationCounter;
import conexp.core.ContextEntity;
import conexp.core.DefaultContextObjectListener;
import junit.framework.TestCase;

import java.beans.PropertyChangeEvent;


public class ContextEntityTest extends TestCase {
    public static void testContextEntityNameListener() {
        ContextEntity attribute = ContextEntity.createContextAttribute("One");
        final ExpectationCounter counter = new ExpectationCounter("Expected calls");
        DefaultContextObjectListener mockListener = new DefaultContextObjectListener() {
            public void nameChanged(PropertyChangeEvent evt) {
                counter.inc();
            }
        };
        counter.setExpected(1);
        attribute.setContextEntityListener(mockListener);
        attribute.setName("Two");
        counter.verify();
        counter.setExpected(0);
        attribute.setName("Two");
        counter.verify();
    }

    public static void testEquals() {
        final String firstName = "One";
        final String secondName = "Two";
        ContextEntity attr1 = ContextEntity.createContextAttribute(firstName);
        ContextEntity attr2 = ContextEntity.createContextAttribute(firstName);
        assertEquals(attr1, attr2);

        assertEquals(false, attr1.equals(new Object()));

        assertEquals(false, attr1.equals(null));

        attr2.setName(secondName);
        assertEquals(false, attr1.equals(attr2));

        ContextEntity obj = ContextEntity.createContextObject(firstName);
        assertEquals(false, attr1.equals(obj));


        attr2 = ContextEntity.createContextAttribute(new String(firstName));
        //new String(firstName )specially performed in order that equality (not sameness) was tested
        assertEquals(attr1, attr2);
    }

}

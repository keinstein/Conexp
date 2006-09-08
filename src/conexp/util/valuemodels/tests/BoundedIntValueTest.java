/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.util.valuemodels.tests;

import conexp.util.valuemodels.BoundedIntValue;
import junit.framework.TestCase;

import java.beans.PropertyVetoException;

public class BoundedIntValueTest extends TestCase {

    private static void doTestCreationWithIllegelParams(String propName, int value, int minValue, int maxValue) {
        try {
            new BoundedIntValue(propName, value, minValue, maxValue);
            fail("should throw IllegalArgument");
        } catch (IllegalArgumentException e) {

        }
    }

    public static void testCreateWithIllegalValue() {
        //0 is used for also testing that check is performed when value is equal to
        //"default value" of value

        doTestCreationWithIllegelParams("TestValue", 0, 1, 2);
    }


    public static void testIllegalCreateWithEmptyName() {
        doTestCreationWithIllegelParams(null, 0, -1, 1);
    }

    public static void testIllegalCreateWithWrongRange() {
        doTestCreationWithIllegelParams("TestValue", 0, 1, 0);
    }

    public static void testNormalCreateAndSetValue() throws java.beans.PropertyVetoException {
        BoundedIntValue val = new BoundedIntValue("testValue", 0, -1, 1);
        val.setValue(-1);
        assertEquals(-1, val.getValue());
        val.setValue(1);
        assertEquals(1, val.getValue());
        val.setValue(0);
        assertEquals(0, val.getValue());
        try {
            val.setValue(2);
            fail("should'nt allow setting value bigger then max");
        } catch (java.beans.PropertyVetoException e) {
        }
        assertEquals(0, val.getValue());
        try {
            val.setValue(-2);
            fail("should'nt allow setting value bigger then max");
        } catch (java.beans.PropertyVetoException e) {
        }
        assertEquals(0, val.getValue());

    }

    public static void testMaxCharLength() {
        BoundedIntValue val = new BoundedIntValue("testValue", 0, -1, 1);
        assertEquals(2, val.maxCharsLength());
        val = new BoundedIntValue("testValue", 0, 0, 10);
        assertEquals(2, val.maxCharsLength());
        val = new BoundedIntValue("testValue", 0, -10, 10);
        assertEquals(3, val.maxCharsLength());
        val = new BoundedIntValue("testValue", 0, -10, 1000);
        assertEquals(4, val.maxCharsLength());
    }

    public static void testGetAnsSetValueIndex() {
        BoundedIntValue val = new BoundedIntValue("testValue", 5, 1, 10);

        assertEquals(4, val.getIndexOfValue());
        try {
            val.setIndexOfValue(0);
        } catch (PropertyVetoException ex) {
            fail("Should set value");
        }
        assertEquals(1, val.getValue());
    }

    public static void testMakeStringDescription() {
        BoundedIntValue val = new BoundedIntValue("testValue", 1, 0, 2);
        String[] res = val.makeStringArrayOfValueDescription();
        assertEquals(3, res.length);
        assertEquals("0", res[0]);
        assertEquals("1", res[1]);
        assertEquals("2", res[2]);
    }

}

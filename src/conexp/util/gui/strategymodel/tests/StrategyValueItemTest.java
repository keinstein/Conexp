/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.util.gui.strategymodel.tests;

import conexp.util.GenericStrategy;
import conexp.util.gui.strategymodel.GrowingStrategyModel;
import conexp.util.gui.strategymodel.StrategyValueItem;
import junit.framework.TestCase;
import util.testing.TestUtil;

public class StrategyValueItemTest extends TestCase {
    private GrowingStrategyModel model;

    static class MockGenericStrategy implements GenericStrategy {
    }

    ;

    private StrategyValueItem valueItem;
    private String FIRST_KEY = "one";
    private String SECOND_KEY = "two";

    protected void setUp() throws Exception {
        model = new GrowingStrategyModel();


        model.addStrategy(FIRST_KEY, "first test strategy", new MockGenericStrategy());
        model.addStrategy(SECOND_KEY, "second test entry", new MockGenericStrategy());

        valueItem = new StrategyValueItem("valueItem",
                model, null);

    }

    public void testGetStrategyKey() {
        valueItem.setValue(0);
        assertEquals(0, valueItem.getValue());
        assertEquals(FIRST_KEY, valueItem.getStrategyKey());
    }

    public void testFindStrategyByKey() {
        assertEquals(1, valueItem.findStrategyByKey(SECOND_KEY));
    }

    public void testSetValue() {

        assertEquals(0, valueItem.getValue());
        valueItem.setValue(-1);
        assertEquals(0, valueItem.getValue());
        valueItem.setValue(2);
        assertEquals(0, valueItem.getValue());

        valueItem.setValue(1);
        assertEquals(1, valueItem.getValue());
    }

    public void testSetValueByKey() {
        assertEquals(0, valueItem.getValue());
        assertTrue(valueItem.setValueByKey(SECOND_KEY));
        assertEquals(1, valueItem.getValue());

        assertEquals(false, valueItem.setValueByKey("Not a key"));
        assertEquals(1, valueItem.getValue());
    }

    public void testEquals() {
        StrategyValueItem other = new StrategyValueItem(valueItem.getPropertyName(),
                model, null);
        TestUtil.testEqualsAndHashCode(valueItem, other);
    }
}

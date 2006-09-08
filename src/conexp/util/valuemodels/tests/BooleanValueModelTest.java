/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.util.valuemodels.tests;



import conexp.util.valuemodels.BooleanValueModel;
import junit.framework.TestCase;
import util.testing.TestUtil;

public class BooleanValueModelTest extends TestCase {

    public static void testEqualsAndHashCode() {
        BooleanValueModel one = new BooleanValueModel("TEST", false);
        BooleanValueModel two = new BooleanValueModel("TEST", false);

        BooleanValueModel three = new BooleanValueModel("TEST", true);
        BooleanValueModel four = new BooleanValueModel("CHECK", false);
        TestUtil.testEqualsAndHashCode(one, two);
        TestUtil.testNotEquals(one, three);
        TestUtil.testNotEquals(one, four);
        TestUtil.testNotEquals(three, four);


    }
}

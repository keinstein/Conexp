/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.experimenter.setdecorator.tests;

import conexp.experimenter.setdecorator.OperationCodes;
import conexp.experimenter.setdecorator.OperationCountHolder;
import conexp.experimenter.setdecorator.OperationStatistic;
import junit.framework.TestCase;
import util.testing.TestUtil;



public class OperationStatisticTest extends TestCase {
    public static void testMakeCopy() {
        OperationCountHolder statistic = new OperationStatistic();
        statistic.register(OperationCodes.IN);
        assertEquals(1, statistic.getOperationCount(OperationCodes.IN));
        OperationCountHolder copy = statistic.makeCopy();
        assertEquals(copy, statistic);
        statistic.register(OperationCodes.IN);
        TestUtil.testNotEquals(copy, statistic);
        assertEquals(1, copy.getOperationCount(OperationCodes.IN));
        assertEquals(2, statistic.getOperationCount(OperationCodes.IN));
    }

    public static void testClear() {
        OperationStatistic statistic = new OperationStatistic();
        statistic.register(OperationCodes.IN);
        assertEquals(1, statistic.getOperationCount(OperationCodes.IN));
        statistic.clear();
        assertEquals(0, statistic.getOperationCount(OperationCodes.IN));
    }
}

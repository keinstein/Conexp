package conexp.experimenter.setdecorator.tests;

import junit.framework.TestCase;
import conexp.experimenter.setdecorator.OperationStatistic;
import conexp.experimenter.setdecorator.OperationCodes;
import util.testing.TestUtil;

/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 * Date: 26/8/2003
 * Time: 17:26:20
 */

public class OperationStatisticTest extends TestCase {
    public void testMakeCopy() {
        OperationStatistic statistic = new OperationStatistic();
        statistic.register(OperationCodes.IN);
        assertEquals(1, statistic.getOperationCount(OperationCodes.IN));
        OperationStatistic copy = statistic.makeCopy();
        assertEquals(copy, statistic);
        statistic.register(OperationCodes.IN);
        TestUtil.testNotEquals(copy, statistic);
        assertEquals(1, copy.getOperationCount(OperationCodes.IN));
        assertEquals(2, statistic.getOperationCount(OperationCodes.IN));
    }

    public void testClear() {
       OperationStatistic statistic =new OperationStatistic();
        statistic.register(OperationCodes.IN);
        assertEquals(1, statistic.getOperationCount(OperationCodes.IN));
        statistic.clear();
        assertEquals(0, statistic.getOperationCount(OperationCodes.IN));
    }
}

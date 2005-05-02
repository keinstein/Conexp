package conexp.frontend.latticeeditor.movestrategies.tests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 * Date: 14/7/2003
 * Time: 22:11:04
 */

public class AllTests extends TestCase {

    public static Test suite() {
        TestSuite ret = new TestSuite();
        ret.addTestSuite(OneFigureMoveStrategyTest.class);
        ret.addTestSuite(FigureIdealMoveStrategyTest.class);
        return ret;
    }
}

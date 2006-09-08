/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.tests;

import com.mockobjects.ExpectationCounter;
import conexp.core.DefaultContextListener;


public class MockContextListener extends DefaultContextListener {
    protected ExpectationCounter counter = new ExpectationCounter("Expected number of calls");

    /**
     * Insert the method's description here.
     * Creation date: (23.12.00 13:38:05)
     */
    public void setExpectedCalls(int cnt) {
        counter.setExpected(cnt);
    }

    /**
     * Insert the method's description here.
     * Creation date: (23.12.00 13:40:06)
     */
    public void verify() {
        counter.verify();
    }

    public void incCounter() {
        counter.inc();
    }
}

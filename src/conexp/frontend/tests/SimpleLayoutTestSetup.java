/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.frontend.tests;

import conexp.frontend.ILatticeComponentFactory;
import conexp.frontend.LatticeComponentFactory;
import junit.extensions.TestSetup;
import junit.framework.TestSuite;


public class SimpleLayoutTestSetup extends TestSetup {
    public SimpleLayoutTestSetup(TestSuite suite) {
        super(suite);
    }

    ILatticeComponentFactory oldValue;

    protected void setUp() throws Exception {
        oldValue = LatticeComponentFactory.getInstance();
        LatticeComponentFactory.configureTest();
    }

    protected void tearDown() throws Exception {
        LatticeComponentFactory.setOurInstance(oldValue);
    }
}

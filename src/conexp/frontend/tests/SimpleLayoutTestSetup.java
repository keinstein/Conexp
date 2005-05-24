package conexp.frontend.tests;

import junit.extensions.TestSetup;
import junit.framework.TestSuite;
import conexp.frontend.LatticeComponentFactory;
import conexp.frontend.ILatticeComponentFactory;

/**
 * Created by IntelliJ IDEA.
 * User: sergey
 * Date: 20/5/2005
 * Time: 2:26:12
 * To change this template use Options | File Templates.
 */
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

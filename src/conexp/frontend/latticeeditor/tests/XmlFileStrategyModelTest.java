package conexp.frontend.latticeeditor.tests;

import conexp.frontend.latticeeditor.XMLFileStrategyModel;
import conexp.util.exceptions.ConfigFatalError;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * JUnit test case for XmlFileStrategyModelTest
 */

public class XmlFileStrategyModelTest extends TestCase {
    private static final Class THIS = XmlFileStrategyModelTest.class;

    protected void compareExpectedAndActualArrays(Object[][] exp, Object[][] act) {
        assertEquals(exp.length, act.length);
        for (int i = exp.length; --i >= 0;) {
            assertEquals(exp[i].length, act[i].length);
            for (int j = exp[i].length; --j >= 0;) {
                if (exp[i][j] != null) {
                    assertTrue(exp[i][j].equals(act[i][j]));
                } else {
                    assertNull(act[i][j]);
                }
            }
        }
    }


    /**
     * Insert the method's description here.
     * Creation date: (15.06.01 21:20:54)
     */
    protected static void expectClassCreationError(String testFileName) {
        try {
            new XMLFileStrategyModel(testFileName, true);
            fail();
        } catch (ConfigFatalError expected) {
            assertTrue(true);
        }
    }


    /**
     * Insert the method's description here.
     * Creation date: (15.06.01 21:20:54)
     */
    protected static void expectConfigurationError(String testFileName) {
        try {
            new XMLFileStrategyModel(testFileName, false);
            fail();
        } catch (ConfigFatalError expected) {
            assertTrue(true);
        }
    }


    public static Test suite() {
        return new TestSuite(THIS);
    }


    public void testLoadBadXmlModel() {
        expectConfigurationError("conexp/frontend/resources/tests/xmlBadXmlTest.xml");
    }


    public void testLoadEmptyModel() {
        expectConfigurationError("conexp/frontend/resources/tests/xmlEmptyModelTest.xml");
    }


    public void testLoadModelWithBadClassNameModel() {
        expectClassCreationError("conexp/frontend/resources/tests/xmlConfigTest.xml");
    }


    public void testLoadNoClassNameModel() {
        expectConfigurationError("conexp/frontend/resources/tests/xmlBadConfig2Test.xml");
    }


    public void testLoadNoLabelModel() {
        expectConfigurationError("conexp/frontend/resources/tests/xmlBadConfig1Test.xml");
    }


    public void testLoadNonExistingModel() {
        expectConfigurationError("conexp/frontend/resources/tests/xmlNotExists.xml");
    }


    public void testLoadNormalModel() {
        XMLFileStrategyModel model = new XMLFileStrategyModel("conexp/frontend/resources/tests/xmlConfigTest.xml", false);
        compareExpectedAndActualArrays(new String[][]{{"one", "one.test"}, {"two", "two.test"}}, model.getCreateInfo());
    }
}
/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor.tests;

import conexp.frontend.latticeeditor.XMLFileStrategyModel;
import conexp.tests.TestPathResolver;
import conexp.util.exceptions.ConfigFatalError;
import junit.framework.TestCase;
import util.testing.TestUtil;


public class XmlFileStrategyModelTest extends TestCase {

    private void compareExpectedAndActualArrays(Object[][] exp, Object[][] act) {
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
    private static void expectClassCreationError(String testFileName) {
        try {
            makeTestXMLModel(testFileName, true);
            fail();
        } catch (ConfigFatalError expected) {
            assertTrue(true);
        }
    }

    private static XMLFileStrategyModel makeTestXMLModel(String testFileName, boolean doCreateStrategies) {
        return new XMLFileStrategyModel(TestPathResolver.getTestPath(testFileName), doCreateStrategies);
    }


    /**
     * Insert the method's description here.
     * Creation date: (15.06.01 21:20:54)
     */
    private static void expectConfigurationError(String testFileName) {
        try {
            makeTestXMLModel(testFileName, false);
            fail();
        } catch (ConfigFatalError expected) {
            assertTrue(true);
        }
    }


    public static void testLoadBadXmlModel() {
        expectConfigurationError("conexp/frontend/resources/tests/xmlBadXmlTest.xml");
    }


    public static void testLoadEmptyModel() {
        expectConfigurationError("conexp/frontend/resources/tests/xmlEmptyModelTest.xml");
    }


    public static void testLoadModelWithBadClassNameModel() {
        expectClassCreationError("conexp/frontend/resources/tests/xmlConfigTest.xml");
    }


    public static void testLoadNoClassNameModel() {
        expectConfigurationError("conexp/frontend/resources/tests/xmlBadConfig2Test.xml");
    }


    public static void testLoadNoLabelModel() {
        expectConfigurationError("conexp/frontend/resources/tests/xmlBadConfig1Test.xml");
    }


    public static void testLoadNonExistingModel() {
        expectConfigurationError("conexp/frontend/resources/tests/xmlNotExists.xml");
    }


    public void testLoadNormalModel() {
        XMLFileStrategyModel model = makeTestXMLModel("conexp/frontend/resources/tests/xmlConfigTest.xml", false);
        compareExpectedAndActualArrays(new String[][]{{"one", "test", "one.test"}, {"two", "test", "two.test"}}, model.getCreateInfo());
    }

    public static void testEquals() {
        String modelPath = TestPathResolver.getProductionPath("conexp/frontend/resources/LayoutStrategyModel.xml");
        XMLFileStrategyModel first = new XMLFileStrategyModel(modelPath, true);
        XMLFileStrategyModel second = new XMLFileStrategyModel(modelPath, true);
        TestUtil.testEqualsAndHashCode(first, second);

    }
}

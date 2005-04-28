/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.latticeeditor.tests;

import conexp.frontend.latticeeditor.XMLFileStrategyModel;
import conexp.util.exceptions.ConfigFatalError;
import junit.framework.TestCase;
import util.testing.TestUtil;


public class XmlFileStrategyModelTest extends TestCase {

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
        XMLFileStrategyModel model = new XMLFileStrategyModel("conexp/frontend/resources/tests/xmlConfigTest.xml", false);
        compareExpectedAndActualArrays(new String[][]{{"one", "test", "one.test"}, {"two", "test", "two.test"}}, model.getCreateInfo());
    }

    public static void testEquals(){
        XMLFileStrategyModel first = new XMLFileStrategyModel("conexp/frontend/resources/LayoutStrategyModel.xml", true);
        XMLFileStrategyModel second = new XMLFileStrategyModel("conexp/frontend/resources/LayoutStrategyModel.xml", true);
        TestUtil.testEqualsAndHashCode(first, second);

    }
}

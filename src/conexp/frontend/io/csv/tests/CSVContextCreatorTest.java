/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.io.csv.tests;


import conexp.core.ExtendedContextEditingInterface;
import conexp.core.tests.SetBuilder;
import conexp.frontend.DocumentLoader;
import conexp.frontend.io.DefaultDataFormatErrorHandler;
import conexp.frontend.io.csv.CSVContextLoader;
import junit.framework.TestCase;
import util.DataFormatException;
import util.StringUtil;
import util.testing.TestUtil;

import java.io.IOException;
import java.io.StringReader;

public class CSVContextCreatorTest extends TestCase {

    private static DocumentLoader getLoader() {
        return new CSVContextLoader();
    }


    public static void testReadingWithEmptyLine() {
        String[] data = {";A1;A2",
                "O1;z;a",
                "",
                "O2;1;1"};
        ExtendedContextEditingInterface expectedContext = SetBuilder.makeContext(new String[]{"O1", "O2"}, new String[]{"A1", "A2"}, new int[][]
                {{0, 0},
                        {1, 1}});

        doTestReading(data, expectedContext);

        String[] data2 = {";A1;A2",
                "O1;z;a",
                "                                             ",
                "O2;1;1"};
        doTestReading(data2, expectedContext);
    }

    public static void testReadingWithEmptyLastattribute() {
        String[] data = {";A1;A2",
                "",
                "O1;1;",
                "O2;0;1"};
        ExtendedContextEditingInterface expectedContext = SetBuilder.makeContext(new String[]{"O1", "O2"}, new String[]{"A1", "A2"}, new int[][]
                {{1, 0},
                        {0, 1}});

        doTestReading(data, expectedContext);
    }


    public static void testReadingWithComments() {
        String[] data = {";A1;A2",
                "O1;1;0",
                "%aaaaa;klklöklkök;äkkklölk",
                "O2;0;1"};
        ExtendedContextEditingInterface expectedContext = SetBuilder.makeContext(new String[]{"O1", "O2"}, new String[]{"A1", "A2"}, new int[][]
                {{1, 0},
                        {0, 1}});

        doTestReading(data, expectedContext);
    }


    public static void testReadingWithComma() {
        String[] data = {",A1,A2",
                "O1,1,0",
                "%aaaaa,klklöklkök,äkkklölk",
                "O2,0,1"};
        ExtendedContextEditingInterface expectedContext = SetBuilder.makeContext(new String[]{"O1", "O2"}, new String[]{"A1", "A2"}, new int[][]
                {{1, 0},
                        {0, 1}});

        doTestReading(data, expectedContext);
    }

    public static void testReadingOfEmptyString() {
        String[] data = {""};
        StringReader reader = new StringReader(buildString(data));
        try {
            getLoader().loadDocument(reader,
                    DefaultDataFormatErrorHandler.getInstance()
            ).getContext();
        } catch (IOException e) {
            TestUtil.reportUnexpectedException(e);
        } catch (DataFormatException e) {
            //it's ok
        }
    }


    //Taken from ConImpTest
    private static void doTestReading(String[] data, ExtendedContextEditingInterface expectedContext) {
        StringReader reader = new StringReader(buildString(data));
        try {
            ExtendedContextEditingInterface cxt = getLoader().loadDocument(reader, DefaultDataFormatErrorHandler.getInstance()).getContext();
            assertEquals(expectedContext, cxt);
        } catch (Exception e) {
            TestUtil.reportUnexpectedException(e);
        }
    }

    private static String buildString(String[] strings) {
        return StringUtil.join(strings, "\n");
    }

}

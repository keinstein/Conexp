/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.io.tests;

import conexp.core.ExtendedContextEditingInterface;
import conexp.core.tests.SetBuilder;
import conexp.frontend.io.ConImpContextLoader;
import conexp.frontend.io.ContextReader;
import junit.framework.TestCase;
import util.StringUtil;
import util.testing.TestUtil;

import java.io.StringReader;


public class ConImpContextReaderTest extends TestCase {
    public static void testReading() {
        String[] data = {
                "B",
                "Test cxt",
                "2",
                "2",
                "O1",
                "O2",
                "A1",
                "A2",
                "..",
                "xx",
        };

        ExtendedContextEditingInterface expectedContext = SetBuilder.makeContext(new String[]{"O1", "O2"}, new String[]{"A1", "A2"}, new int[][]
                {{0, 0},
                        {1, 1}});

        doTestReading(data, expectedContext);
    }

    public static void testReadingWithEmptyLineAfterDimensions() {
        String[] data = {
                "B",
                "Test cxt",
                "2",
                "2",
                " ",
                "O1",
                "O2",
                "A1",
                "A2",
                "..",
                "xx",
        };

        ExtendedContextEditingInterface expectedContext = SetBuilder.makeContext(new String[]{"O1", "O2"}, new String[]{"A1", "A2"}, new int[][]
                {{0, 0},
                        {1, 1}});

        doTestReading(data, expectedContext);
    }


    private static void doTestReading(String[] data, ExtendedContextEditingInterface expectedContext) {
        StringReader reader = new StringReader(buildString(data));
        ContextReader loader = new ConImpContextLoader();
        try {
            ExtendedContextEditingInterface cxt = loader.parseContext(reader);
            assertEquals(expectedContext, cxt);
        } catch (Exception e) {
            TestUtil.reportUnexpectedException(e);
        }
    }

    private static String buildString(String[] strings) {
        return StringUtil.join(strings, "\n");
    }
}

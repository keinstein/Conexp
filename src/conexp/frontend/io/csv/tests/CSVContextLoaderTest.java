/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.io.csv.tests;


import java.io.StringReader;

import junit.framework.TestCase;
import util.StringUtil;
import util.testing.TestUtil;
import conexp.core.ExtendedContextEditingInterface;
import conexp.core.tests.SetBuilder;
import conexp.frontend.io.ContextCreator;
import conexp.frontend.io.csv.CSVContextCreator;

public class CSVContextLoaderTest extends TestCase {

	protected ContextCreator getCreator(){
		return new CSVContextCreator();
	}
	
	public void testReadingWithEmptyLine() {
		String[] data = {";A1;A2",
								"O1;z;a",
								""	,
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

	public void testReadingWithEmptyLastattribute() {
			String[] data = {";A1;A2",
									""	,
									"O1;1;",
									"O2;0;1"};
			ExtendedContextEditingInterface expectedContext = SetBuilder.makeContext(new String[]{"O1", "O2"}, new String[]{"A1", "A2"}, new int[][]
			{{1, 0},
			 {0, 1}});

			doTestReading(data, expectedContext);
	}


	public void testReadingWithComments() {
			String[] data = {";A1;A2",
									"O1;1;0",
									"%aaaaa;klklöklkök;äkkklölk",
									"O2;0;1"};
			ExtendedContextEditingInterface expectedContext = SetBuilder.makeContext(new String[]{"O1", "O2"}, new String[]{"A1", "A2"}, new int[][]
			{{1, 0},
			 {0, 1}});

			doTestReading(data, expectedContext);
	}


	//Taken from ConImpTest
	private void doTestReading(String[] data, ExtendedContextEditingInterface expectedContext) {
		StringReader reader = new StringReader(buildString(data));
		try {
			ExtendedContextEditingInterface cxt = getCreator().createContext(reader);
			assertEquals(expectedContext, cxt);
		} catch (Exception e) {
			TestUtil.reportUnexpectedException(e);
		}
	}

	public static String buildString(String[] strings) {
		return StringUtil.join(strings, "\n");
	}
	
}

/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.io.csv.tests;


import java.io.StringReader;

import util.StringUtil;
import util.testing.TestUtil;

import conexp.core.Context;
import conexp.core.ExtendedContextEditingInterface;
import conexp.core.tests.SetBuilder;
import conexp.frontend.Document;
import conexp.frontend.DocumentLoader;
import conexp.frontend.io.ContextCreator;
import conexp.frontend.io.DefaultDataFormatErrorHandler;
import conexp.frontend.io.csv.CSVContextCreator;
import conexp.frontend.io.csv.CSVContextLoader;

import junit.framework.TestCase;

public class CSVContextCreatorTest extends TestCase {

	final static String simpleattributesstring = ";attr1;attr2;attr3;attr4;attr5";
	final static  int attributecount = 5;
	final static String[] objectstrings = {
		"obj1;1;0;1;1;",
		"obj2;1;0;1;1;",
		"obj3;0;0;1;1;1",
		"obj4;0;0;0;1;",
	};
	
	CSVContextCreatorTest(){
		 
	}
	
	protected void setUp(){
		
	}

	protected DocumentLoader getLoader(){
		return new CSVContextLoader();
	}

	private String contextstring = null; 
	
	private String getContextString(){
		if ( contextstring == null ){
			contextstring = simpleattributesstring;
			for (int i = 0; i < objectstrings.length ; i ++ ){
				contextstring ="\n"+objectstrings[i];
			}
		}		
		return contextstring;
	}

	public void testSetAttributes(){
		setUp();
		String contextstring  = getContextString();
		
	}

	public void testReadingWithEmptyLine() {
		String[] data = {";A1,A2",
								"O1;z;a",
								""	,
								"O2;1;1"};
		ExtendedContextEditingInterface expectedContext = SetBuilder.makeContext(new String[]{"O1", "O2"}, new String[]{"A1", "A2"}, new int[][]
		{{0, 0},
		 {1, 1}});
		 
		doTestReading(data, expectedContext);
		
		String[] data2 = {";A1,A2",
					"O1;z;a",
					"                                             ",
					"O2;1;1"};
		doTestReading(data2, expectedContext);
	}

	public void testReadingWithEmptyLastattribute() {
			String[] data = {";A1,A2",
									""	,
									"O1;1;",
									"O2;0;1"};
			ExtendedContextEditingInterface expectedContext = SetBuilder.makeContext(new String[]{"O1", "O2"}, new String[]{"A1", "A2"}, new int[][]
			{{1, 0},
			 {0, 1}});

			doTestReading(data, expectedContext);
	}


	public void testReadingWithComments() {
			String[] data = {";A1,A2",
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
			ExtendedContextEditingInterface cxt = getLoader().loadDocument(reader,DefaultDataFormatErrorHandler.getInstance() ).getContext();
			assertEquals(expectedContext, cxt);
		} catch (Exception e) {
			TestUtil.reportUnexpectedException(e);
		}
	}

	public static String buildString(String[] strings) {
		return StringUtil.join(strings, "\n");
	}
	
}

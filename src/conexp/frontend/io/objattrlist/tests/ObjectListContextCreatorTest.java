/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.io.objattrlist.tests;


import conexp.core.ExtendedContextEditingInterface;
import conexp.core.tests.SetBuilder;
import conexp.frontend.DocumentLoader;
import conexp.frontend.io.DefaultDataFormatErrorHandler;
import conexp.frontend.io.objattrlist.ObjectListContextCreator;
import junit.framework.TestCase;
import util.StringUtil;
import util.testing.TestUtil;

import java.io.StringReader;

public class ObjectListContextCreatorTest extends TestCase {

	final static String simpleattributesstring = ";attr1;attr2;attr3;attr4;attr5";
	final static  int attributecount = 5;
	final static String[] objectstrings = {
		"obj1;1;0;1;1;",
		"obj2;1;0;1;1;",
		"obj3;0;0;1;1;1",
		"obj4;0;0;0;1;",
	};
	
	public ObjectListContextCreatorTest(){
		 
	}
	
	protected void setUp(){
		
	}

	protected DocumentLoader getLoader(){
		return new ObjectListContextCreator();
	}

	public void testReadingWithEmptyLine() {
		String[] data = {"O1:",		
									""	,
								"O2:A1;A2"
								};
		ExtendedContextEditingInterface expectedContext = SetBuilder.makeContext(new String[]{"O1", "O2"}, new String[]{"A1", "A2"}, new int[][]
		{{0, 0},
		 {1, 1}});
		 
		doTestReading(data, expectedContext);
		String[] data2 = {	"O1:",											
									"                                             ",
									"O2:A1;A2"};
		
		doTestReading(data2, expectedContext);
	}

	public void testReadingWithComments() {
			String[] data = {
									"O1:A1",
									"%aaaaa;klklöklkök;äkkklölk",
									"O2:A2"};
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

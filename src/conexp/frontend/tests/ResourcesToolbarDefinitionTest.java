/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.tests;

import conexp.frontend.util.ToolBuilder;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import util.StringUtil;

import javax.swing.*;
import java.util.ResourceBundle;

public class ResourcesToolbarDefinitionTest extends TestCase {
    private static final Class THIS = ResourcesToolbarDefinitionTest.class;

    public static Test suite() {
        return new TestSuite(THIS);
    }

    public static void testToolbarDefinitionInResources(ResourceBundle resources, ActionMap actionMap) {
        ToolBuilder toolbuilder = new ToolBuilder(resources, actionMap) {
            //----------------------------------------------
            //----------------------------------------------
            protected AbstractButton createToolbarButton(String command, Icon icon, boolean isToggle, String tooltip, Action listener) {
/*                if(null==icon){
                    fail("Icon for command "+command+" is empty ");
                }*/ //for a while don't know, how to point out to test resources in destination directory
                if (StringUtil.isEmpty(tooltip)) {
                    fail("Tooltip for command " + command + "is not defined");
                }
                if (null == listener) {
                    fail("No listener for command " + command);
                }
                return super.createToolbarButton(command, icon, isToggle, tooltip, listener);
            }
        };
        toolbuilder.createToolBar(JToolBar.VERTICAL);
    }

}

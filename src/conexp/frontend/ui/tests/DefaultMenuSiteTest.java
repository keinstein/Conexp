/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.ui.tests;

import conexp.frontend.ui.DefaultMenuSite;
import junit.framework.TestCase;

import javax.swing.*;
import java.awt.*;

public class DefaultMenuSiteTest extends TestCase {

    static class TestFrame extends JFrame {
        public TestFrame() throws HeadlessException {
            super();
            setVisible(false);
        }
    }

    public static void testDefaultMenuSite() {
        JFrame frame = new TestFrame();
        DefaultMenuSite site = new DefaultMenuSite(frame);
        Object one = new Object();
        site.addMenu(one, new JMenu("Files"));
        final JMenuBar menuBar = frame.getJMenuBar();
        assertEquals("Menu should be added to menu bar", 1, menuBar.getMenuCount());
        final JMenu helpMenu = new JMenu("Help");
        site.addHelpMenu(one, helpMenu);
        assertEquals("Menu should be added to menu bar", 2, menuBar.getMenuCount());
        assertSame("Help menu should be the last", helpMenu, menuBar.getMenu(1));
        site.addMenu(one, new JMenu("Edit"));
        assertEquals("Menu should be added to menu bar", 3, frame.getJMenuBar().getMenuCount());
        assertSame("Help menu should be the last", helpMenu, frame.getJMenuBar().getMenu(2));


    }

}

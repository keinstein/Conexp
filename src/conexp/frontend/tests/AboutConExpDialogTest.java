/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.frontend.tests;



import conexp.frontend.AboutConExpDialog;
import junit.framework.TestCase;

public class AboutConExpDialogTest extends TestCase {
    public static void testLicenseUrl() {
        AboutConExpDialog aboutConExpDialog = new AboutConExpDialog(null, "title", true);
        assertNotNull(aboutConExpDialog.getLicenseUrl());
    }
}

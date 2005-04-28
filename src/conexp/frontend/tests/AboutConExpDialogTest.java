package conexp.frontend.tests;

/**
 * User: sergey
 * Date: 14/4/2005
 * Time: 0:26:50
 */

import junit.framework.*;
import conexp.frontend.AboutConExpDialog;

public class AboutConExpDialogTest extends TestCase {
    public static void testLicenseUrl(){
        AboutConExpDialog aboutConExpDialog= new AboutConExpDialog(null, "title", true);
        assertNotNull(aboutConExpDialog.getLicenseUrl());
    }
}
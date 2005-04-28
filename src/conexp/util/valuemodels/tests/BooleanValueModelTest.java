package conexp.util.valuemodels.tests;

/**
 * User: sergey
 * Date: 21/4/2005
 * Time: 14:17:39
 */

import junit.framework.*;
import conexp.util.valuemodels.BooleanValueModel;
import util.testing.TestUtil;

public class BooleanValueModelTest extends TestCase {

    public static void testEqualsAndHashCode(){
        BooleanValueModel one=new BooleanValueModel("TEST", false);
        BooleanValueModel two=new BooleanValueModel("TEST", false);
        
        BooleanValueModel three = new BooleanValueModel("TEST", true);
        BooleanValueModel four= new BooleanValueModel("CHECK", false);
        TestUtil.testEqualsAndHashCode(one, two);
        TestUtil.testNotEquals(one, three);
        TestUtil.testNotEquals(one, four);
        TestUtil.testNotEquals(three, four);


    }
}
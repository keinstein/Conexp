package conexp.util.valuemodels.tests;

/**
 * Created by IntelliJ IDEA.
 * User: sergey
 * Date: 16/4/2005
 * Time: 2:09:19
 * To change this template use File | Settings | File Templates.
 */

import conexp.util.valuemodels.IntValueModel;
import conexp.util.valuemodels.VetoableValueModelDecorator;
import junit.framework.TestCase;
import util.testing.TestUtil;

public class VetoableValueModelDecoratorTest extends TestCase {

    public static void testEquals() {
        IntValueModel one = new IntValueModel("prop", 2);
        VetoableValueModelDecorator first = new VetoableValueModelDecorator(one);
        VetoableValueModelDecorator second = new VetoableValueModelDecorator(one);
        TestUtil.testEqualsAndHashCode(first, second);

        IntValueModel two = new IntValueModel("prop", 2);
        VetoableValueModelDecorator third = new VetoableValueModelDecorator(two);

        TestUtil.testEqualsAndHashCode(first, third);
    }
}
/*
 * Created by IntelliJ IDEA.
 * User: Serhiy Yevtushenko
 * Date: Jul 28, 2002
 * Time: 12:55:17 AM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package conexp.core;

import util.Assert;

public class NullAttributeInformationSupplier implements AttributeInformationSupplier{
    public ContextEntity getAttribute(int index) {
        Assert.isTrue(false, "shouldn't be called, when attribute count is 0");
        return null;
    }

    public int getAttributeCount() {
        return 0;
    }

}

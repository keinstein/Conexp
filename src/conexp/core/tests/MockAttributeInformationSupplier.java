/*
 * Date: Dec 2, 2001
 * Time: 11:55:30
 */
package conexp.core.tests;

import conexp.core.AttributeInformationSupplier;
import conexp.core.ContextEntity;

public class MockAttributeInformationSupplier implements AttributeInformationSupplier {
    final int attrNo;

    public MockAttributeInformationSupplier(int attrNo) {
        this.attrNo = attrNo;
    }

    public ContextEntity getAttribute(int index) {
        return ContextEntity.createContextAttribute(Integer.toString(attrNo));
    }

    public int getAttributeCount() {
        return attrNo;
    }
}

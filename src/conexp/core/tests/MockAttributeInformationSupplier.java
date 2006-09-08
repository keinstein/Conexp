/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.tests;

import conexp.core.AttributeInformationSupplier;
import conexp.core.ContextEntity;

public class MockAttributeInformationSupplier implements AttributeInformationSupplier {
    private final int attrNo;

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

/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core;

import util.Assert;

public class NullAttributeInformationSupplier implements AttributeInformationSupplier {
    public ContextEntity getAttribute(int index) {
        Assert.isTrue(false, "shouldn't be called, when attribute count is 0");
        return null;
    }

    public int getAttributeCount() {
        return 0;
    }

}

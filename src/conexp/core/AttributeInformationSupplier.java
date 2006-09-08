/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core;

public interface AttributeInformationSupplier {
    ContextEntity getAttribute(int index);

    int getAttributeCount();
}

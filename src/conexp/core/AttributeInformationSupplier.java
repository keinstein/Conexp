/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.core;

public interface AttributeInformationSupplier {
    public ContextEntity getAttribute(int index);

    public int getAttributeCount();
}

/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.attrexplorationimpl;

import conexp.core.*;

public abstract class AbstractAttributeExplorationError implements AttributeExplorationError {
    protected AttributeInformationSupplier attrInfo;
    protected String objectName;
    protected final String separator = " , ";
    protected final String emptySetDescriptor = "";

    public AbstractAttributeExplorationError(AttributeInformationSupplier attrInfo, String objectName) {
        this.attrInfo = attrInfo;
        this.objectName = objectName;
    }

    protected String describeSet(Set set) {
        StringBuffer temp = new StringBuffer();
        AttributeInformationSupplierUtil.describeSet(temp, attrInfo, set, separator, emptySetDescriptor);
        return temp.toString();
    }


    public abstract String formatMessage(LocalizedMessageSupplier supplier);
}

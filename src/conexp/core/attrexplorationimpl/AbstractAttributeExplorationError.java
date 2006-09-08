/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.attrexplorationimpl;

import conexp.core.AttributeExplorationError;
import conexp.core.AttributeInformationSupplier;
import conexp.core.AttributeInformationSupplierUtil;
import conexp.core.LocalizedMessageSupplier;
import conexp.core.Set;

public abstract class AbstractAttributeExplorationError implements AttributeExplorationError {
    protected AttributeInformationSupplier attrInfo;
    protected String objectName;
    protected static final String separator = " , ";
    protected static final String emptySetDescriptor = "";

    protected AbstractAttributeExplorationError(AttributeInformationSupplier attrInfo, String objectName) {
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

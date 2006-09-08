/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.attrexplorationimpl;

import conexp.core.AttributeInformationSupplier;
import conexp.core.LocalizedMessageSupplier;
import conexp.core.Set;

import java.text.MessageFormat;

public class BadCounterExampleError extends AbstractAttributeExplorationError {

    Set conclusion;

    public BadCounterExampleError(AttributeInformationSupplier attrInfo, String objectName, Set conclusion) {
        super(attrInfo, objectName);
        this.conclusion = conclusion;
    }

    public String formatMessage(LocalizedMessageSupplier supplier) {
        return MessageFormat.format(supplier.getMessage("AttributeExplorer.Error.CounterExampleContainWholeConclusion"), new Object[]{objectName, describeSet(conclusion)});
    }
}

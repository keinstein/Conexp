/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.attrexplorationimpl;

import conexp.core.AttributeInformationSupplier;
import conexp.core.ContextFactoryRegistry;
import conexp.core.LocalizedMessageSupplier;
import conexp.core.ModifiableSet;
import conexp.core.Set;
import util.Assert;

import java.text.MessageFormat;

public class CounterExampleDoesntContainPremiseError extends AbstractAttributeExplorationError {
    Set contrExample;
    Set premise;

    public CounterExampleDoesntContainPremiseError(AttributeInformationSupplier attrInfo, String objectName, Set premise, Set contrExample) {
        super(attrInfo, objectName);
        this.premise = premise;
        this.contrExample = contrExample;
    }

    public String formatMessage(LocalizedMessageSupplier supplier) {
        ModifiableSet difference = ContextFactoryRegistry.createSet(premise.size());
        difference.copy(premise);
        difference.andNot(contrExample);
        Assert.isTrue(!difference.isEmpty());

        return MessageFormat.format(supplier.getMessage("AttributeExplorer.Error.CounterExampleDoesntContainWholePremise"), new Object[]{objectName, describeSet(difference)});
    }

}

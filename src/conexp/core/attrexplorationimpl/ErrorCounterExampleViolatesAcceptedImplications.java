/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.attrexplorationimpl;

import conexp.core.AttributeInformationSupplier;
import conexp.core.Implication;
import conexp.core.ImplicationSet;
import conexp.core.LocalizedMessageSupplier;
import conexp.core.Set;

import java.text.MessageFormat;

public class ErrorCounterExampleViolatesAcceptedImplications extends AbstractAttributeExplorationError {
    ImplicationSet violatedImplications;

    public ErrorCounterExampleViolatesAcceptedImplications(AttributeInformationSupplier attrInfo, String objectName, Set counterExample, ImplicationSet violatedImplications) {
        super(attrInfo, objectName);
        this.violatedImplications = violatedImplications;
    }

    public String formatMessage(LocalizedMessageSupplier supplier) {
        return MessageFormat.format(supplier.getMessage("AttributeExplorer.Error.CounterExampleContradictEarlierAcceptedImplications"), new Object[]{objectName, descriptionOfViolatedImplications()});
    }

    private String descriptionOfViolatedImplications() {
        final StringBuffer temp = new StringBuffer();
        violatedImplications.forEach(new ImplicationSet.ImplicationProcessor() {
            public void processImplication(Implication implication) {
                temp.append('\n');
                temp.append(describeSet(implication.getPremise()));
                temp.append("=>");
                temp.append(describeSet(implication.getConclusion()));
            }
        });
        return temp.toString();
    }
}

/*
 * Created by IntelliJ IDEA.
 * User: sergey
 * Date: Dec 12, 2001
 * Time: 10:51:09 PM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package conexp.core.attrexplorationimpl;

import conexp.core.*;

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
                temp.append("\n");
                temp.append(describeSet(implication.getPremise()));
                temp.append("=>");
                temp.append(describeSet(implication.getConclusion()));
            }
        });
        return temp.toString();
    }
}

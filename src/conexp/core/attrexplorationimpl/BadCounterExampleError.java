/*
 * Created by IntelliJ IDEA.
 * User: sergey
 * Date: Dec 12, 2001
 * Time: 10:32:42 PM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
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

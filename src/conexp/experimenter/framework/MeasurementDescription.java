/*
 * Created by IntelliJ IDEA.
 * User: sergey
 * Date: Oct 20, 2001
 * Time: 5:22:25 PM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package conexp.experimenter.framework;

import conexp.core.compareutils.IComparatorFactory;

public class MeasurementDescription implements IMeasurementDescription {
    final String name;
    final boolean validating;
    IComparatorFactory comparatorFactory;

    public MeasurementDescription(String name, boolean validating) {
        this.name = name;
        this.validating = validating;
    }

    public boolean isValidating() {
        return validating;
    }

    public String getName() {
        return name;
    }

    public IComparatorFactory getComparatorFactory() {
        return comparatorFactory;
    }

    public void setComparatorFactory(IComparatorFactory comparatorFactory) {
        this.comparatorFactory = comparatorFactory;
    }
}

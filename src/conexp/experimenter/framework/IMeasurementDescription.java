/*
 * Created by IntelliJ IDEA.
 * User: sergey
 * Date: Oct 20, 2001
 * Time: 5:19:12 PM
 * To change template for new interface use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package conexp.experimenter.framework;

import conexp.core.compareutils.IComparatorFactory;


public interface IMeasurementDescription {
    String getName();

    boolean isValidating();

    IComparatorFactory getComparatorFactory();
}

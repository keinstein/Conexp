/*
 * Created by IntelliJ IDEA.
 * User: sergey
 * Date: Nov 19, 2001
 * Time: 10:44:00 PM
 * To change template for new interface use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package conexp.frontend;

import conexp.core.DependencySet;
import conexp.util.gui.paramseditor.ParamsProvider;
import util.PropertyChangeSupplier;

public interface DependencySetSupplier extends PropertyChangeSupplier, ParamsProvider {

    public final static String RULE_SET_PROPERTY = "RULE_SET";
    public final static String RULE_SET_CLEARED = "RULE_SET_CLEARED";

    DependencySet getDependencySet();

    void clearDependencySet();
}

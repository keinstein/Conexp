/*
 * Created by IntelliJ IDEA.
 * User: sergey
 * Date: Oct 15, 2001
 * Time: 11:44:22 PM
 * To change template for new interface use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package conexp.util.valuemodels;

import java.beans.VetoableChangeListener;
import java.beans.VetoableChangeSupport;

public interface IVetoableValueModel extends IValueModel {
    /**
     * The addVetoableChangeListener method was generated to support the vetoPropertyChange field.
     */
    void addVetoableChangeListener(VetoableChangeListener listener);

    /**
     * The removeVetoableChangeListener method was generated to support the vetoPropertyChange field.
     */
    void removeVetoableChangeListener(VetoableChangeListener listener);

    /**
     * Insert the method's description here.
     * Creation date: (02.02.01 21:58:41)
     * @param newVetoPropertyChange java.beans.VetoableChangeSupport
     */
    void setVetoPropertyChange(VetoableChangeSupport newVetoPropertyChange);
}

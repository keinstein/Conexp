/*
 * Created by IntelliJ IDEA.
 * User: sergey
 * Date: Oct 16, 2001
 * Time: 12:12:05 AM
 * To change template for new interface use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package conexp.util.valuemodels;

public interface IIntValueModel extends IVetoableValueModel {
    /**
     * Insert the method's description here.
     * Creation date: (02.02.01 0:18:53)
     * @return int
     */
    int getValue();

    /**
     * Insert the method's description here.
     * Creation date: (02.02.01 0:18:53)
     * @param newValue int
     */
    void setValue(int newValue) throws java.beans.PropertyVetoException;
}

/*
 * Created by IntelliJ IDEA.
 * User: sergey
 * Date: Oct 20, 2001
 * Time: 5:25:27 PM
 * To change template for new interface use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package conexp.experimenter.framework;

import java.util.Iterator;

public interface IMeasurementProtocol {
    public boolean hasMeasurementWithName(String name);

    public Iterator measurementsIterator();

    public Iterator validatingMeasurementIterator();
}

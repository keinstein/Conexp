/*
 * Date: Nov 22, 2001
 * Time: 10:52:06 PM
 */
package conexp.frontend;

import conexp.frontend.latticeeditor.LatticeDrawing;
import util.PropertyChangeSupplier;

public interface LatticeDrawingProvider extends PropertyChangeSupplier {
    LatticeDrawing getDrawing();
}

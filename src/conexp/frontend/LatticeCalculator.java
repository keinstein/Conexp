/*
 * Created by IntelliJ IDEA.
 * User: sergey
 * Date: Nov 5, 2001
 * Time: 8:40:29 PM
 * To change template for new interface use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package conexp.frontend;

public interface LatticeCalculator {
    final static String LATTICE_DRAWING_CHANGED = "LATTICE_DRAWING_CHANGED";
    final static String LATTICE_CLEARED = "LATTICE_CLEARED";

    public void clearLattice();

    public void calculateLattice();
}

/*
 * Created by IntelliJ IDEA.
 * User: sergey
 * Date: Dec 8, 2001
 * Time: 10:53:20 PM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package conexp.core.compareutils;

import conexp.core.Implication;
import conexp.core.ImplicationSet;

public class ImplicationSetCompareSet implements ICompareSet {
    final ImplicationSet implicationSet;

    public ImplicationSetCompareSet(ImplicationSet implicationSet) {
        this.implicationSet = implicationSet;
    }

    public KeyValuePair get(int index) {
        Implication impl = implicationSet.getImplication(index);
        return new KeyValuePair(impl, impl);
    }

    public int getSize() {
        return implicationSet.getSize();
    }
}

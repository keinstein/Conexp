/*
 * Created by IntelliJ IDEA.
 * User: Serhiy Yevtushenko
 * Date: Jul 28, 2002
 * Time: 12:53:42 AM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package conexp.core.associations;

import conexp.core.Context;
import conexp.core.ExtendedContextEditingInterface;
import conexp.core.Set;

public class NullFrequentSetSupportSupplier implements FrequentSetSupportSupplier{
    ExtendedContextEditingInterface cxt = new Context(0,0);

    public int supportForSet(Set attribs) {
        return 0;
    }

    public int supportForClosedSet(Set attribs) {
        return 0;
    }

    public int getTotalObjectCount() {
        return 0;
    }

    public ExtendedContextEditingInterface getDataSet()
    {
        return cxt;
    }

}

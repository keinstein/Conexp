package conexp.core.associations;

import conexp.core.ExtendedContextEditingInterface;
import conexp.core.Set;

/**
 * Insert the type's description here.
 * Creation date: (09.06.01 20:39:53)
 * @author Serhiy Yevtushenko
 */
public interface FrequentSetSupportSupplier {
    int supportForSet(Set attribs);
    int supportForClosedSet(Set attribs);
    int getTotalObjectCount();
    //todo: move to general notion of rule  (as consisiting from hypothesis as some expressions) and
    //general notion of dataset (as collection of objects, according to which hypothesis can be tested)
    ExtendedContextEditingInterface getDataSet(); //for now
}
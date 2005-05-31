package conexp.frontend.components;

import conexp.frontend.ContextDocument;

/**
 * User: sergey
 * Date: 23/5/2005
 * Time: 16:19:56
 */
public interface ILatticeComponentDuplicator {
    LatticeComponent duplicate(LatticeComponent toCopy);

    LatticeComponent duplicateContent(LatticeComponent toCopy, LatticeComponent copy);
}

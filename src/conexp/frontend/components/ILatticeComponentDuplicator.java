/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.frontend.components;



public interface ILatticeComponentDuplicator {
    LatticeComponent duplicate(LatticeComponent toCopy);

    LatticeComponent duplicateContent(LatticeComponent toCopy, LatticeComponent copy);
}

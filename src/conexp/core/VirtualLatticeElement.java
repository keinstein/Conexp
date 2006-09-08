/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core;


public class VirtualLatticeElement extends LatticeElement {
    /**
     * VirtualLatticeElement constructor comment.
     */
    VirtualLatticeElement() {
        super(ContextFactoryRegistry.createSet(0), ContextFactoryRegistry.createSet(0));
    }

//----------------------------------------------

    public boolean isVirtual() {
        return true;
    }
}

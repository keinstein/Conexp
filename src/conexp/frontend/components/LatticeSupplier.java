/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.frontend.components;

import conexp.frontend.LatticeDrawingProvider;
import conexp.frontend.SetProvidingEntitiesMask;


public interface LatticeSupplier extends LatticeDrawingProvider {
    SetProvidingEntitiesMask getAttributeMask();

    SetProvidingEntitiesMask getObjectMask();

    void cleanUp();
}

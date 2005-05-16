package conexp.frontend.components;

import conexp.frontend.LatticeDrawingProvider;
import conexp.frontend.SetProvidingEntitiesMask;

/**
 * User: sergey
 * Date: 14/5/2005
 * Time: 14:07:53
 */
public interface LatticeSupplier extends LatticeDrawingProvider {
    SetProvidingEntitiesMask getAttributeMask();
    SetProvidingEntitiesMask getObjectMask();
    void cleanUp();
}

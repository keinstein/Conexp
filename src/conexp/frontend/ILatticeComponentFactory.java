/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.frontend;

import conexp.core.Context;
import conexp.frontend.components.LatticeComponent;


public interface ILatticeComponentFactory {
    LatticeComponent makeLatticeComponent(Context cxt);
}

/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/

package conexp.frontend.latticeeditor;

import conexp.core.ItemSet;


public interface EdgeSizeCalcStrategy extends DimensionCalcStrategy {
    float edgeThickness(ItemSet start, ItemSet end);
}

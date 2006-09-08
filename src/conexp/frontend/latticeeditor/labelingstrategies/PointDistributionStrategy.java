/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor.labelingstrategies;

import java.awt.geom.Point2D;

public interface PointDistributionStrategy {
    void setNextCoords(Point2D coords);
}

/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.layout;

import conexp.core.ItemSet;

import java.awt.geom.Point2D;

public interface ConceptCoordinateMapper {
    void setCoordsForConcept(ItemSet c, Point2D coords);
}

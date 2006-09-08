/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.layoutengines;

import conexp.core.layout.ConceptCoordinateMapper;

import java.util.EventListener;

public interface LayoutListener extends EventListener {
    void layoutChange(ConceptCoordinateMapper mapper);
}

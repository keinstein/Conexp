/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend;

import conexp.core.layout.Layouter;
import conexp.core.layout.LayouterProvider;
import conexp.core.layout.MinIntersectionLayout;

public class MinIntersectionLayouterProvider implements LayouterProvider {
    Layouter layouter = new MinIntersectionLayout();

    public Layouter getLayouter() {
        return layouter;
    }
}

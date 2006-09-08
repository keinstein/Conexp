/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.layout;


public class MinIntersectionLayouterProvider implements LayouterProvider {
    Layouter layouter = new MinIntersectionLayout();

    public Layouter getLayouter() {
        return layouter;
    }
}

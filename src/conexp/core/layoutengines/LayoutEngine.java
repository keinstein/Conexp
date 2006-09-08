/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.layoutengines;

import conexp.core.Lattice;
import conexp.core.layout.LayoutParameters;
import conexp.core.layout.LayouterProvider;

public interface LayoutEngine {
    void init(LayouterProvider provider);

    void shutdown();

    void startLayout(Lattice lattice, LayoutParameters drawParameters);

    void addLayoutListener(LayoutListener listener);

    void removeLayoutListener(LayoutListener listener);

    LayoutEngine newInstance();
}

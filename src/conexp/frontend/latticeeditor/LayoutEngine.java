/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor;

import conexp.core.Lattice;
import conexp.core.layout.LayouterProvider;
import conexp.core.layoutengines.LayoutListener;

public interface LayoutEngine {
    void init(LayouterProvider provider);

    void shutdown();

    void startLayout(Lattice lattice, DrawParameters drawParameters);

    void addLayoutListener(LayoutListener listener);

    void removeLayoutListener(LayoutListener listener);
}

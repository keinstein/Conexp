/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.layout;

import conexp.core.Lattice;
import conexp.util.GenericStrategy;
import conexp.util.gui.paramseditor.ParamsProvider;

import java.beans.PropertyChangeListener;


public interface Layouter extends GenericStrategy, ConceptCoordinateMapper, ParamsProvider {
    String LAYOUT_CHANGE = "layoutChanged";
    String LAYOUT_PARAMS_CHANGE = "layoutParamsChanged";

    void initLayout(Lattice l, LayoutParameters drawParams);

    void addLayoutChangeListener(PropertyChangeListener listener);

    void removeLayoutChangeListener(PropertyChangeListener listener);

    boolean isDone();

    boolean isIncremental();

    void calcInitialPlacement();

    void improveOnce();

    void performLayout();
}

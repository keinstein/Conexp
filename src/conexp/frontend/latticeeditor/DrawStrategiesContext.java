/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor;

import conexp.core.ConceptsCollection;
import conexp.util.gui.strategymodel.StrategyValueItem;

import java.beans.PropertyChangeSupport;
import java.util.prefs.Preferences;


public interface DrawStrategiesContext extends FigureDimensionCalcStrategyProvider {
    void setupStrategiesParams(ConceptsCollection conceptSet);

    EdgeSizeCalcStrategy getEdgeSizeCalcStrategy();

    Highlighter getHighlighter();

    DrawStrategiesContext makeCopy(PropertyChangeSupport propertyChange);

    void setPreferences(Preferences preferences);

    void doStorePreferences();

    StrategyValueItem getLayoutStrategyItem();

    StrategyValueItem getEdgeSizeCalcStrategyItem();

    StrategyValueItem getHighlightStrategyItem();

    StrategyValueItem getNodeRadiusStrategyItem();
}

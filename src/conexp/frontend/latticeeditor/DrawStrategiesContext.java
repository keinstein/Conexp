/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.latticeeditor;

import conexp.core.ConceptsCollection;

import java.beans.PropertyChangeSupport;
import java.util.prefs.Preferences;


public interface DrawStrategiesContext extends FigureDimensionCalcStrategyProvider {
    void setupStrategiesParams(ConceptsCollection conceptSet);

    EdgeSizeCalcStrategy getEdgeSizeCalcStrategy();

    HighlightStrategy getHighlightStrategy();

    DrawStrategiesContext makeCopy(PropertyChangeSupport propertyChange);

    void setPreferences(Preferences preferences);

    void doStorePreferences();

    String getFigureDimensionStrategyKey();

    String getEdgeSizeStrategyKey();

    boolean setFigureDimensionStrategyKey(String key);

    boolean setEdgeSizeStrategyKey(String key);

    String getHighlightStrategyKey();

    boolean setHighlightStrategyKey(String key);

}

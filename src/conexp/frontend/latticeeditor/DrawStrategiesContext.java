/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor;

import java.util.prefs.Preferences;
import java.beans.PropertyChangeSupport;


public interface DrawStrategiesContext extends conexp.frontend.latticeeditor.FigureDimensionCalcStrategyProvider {
    void setupStrategiesParams(conexp.core.ConceptsCollection conceptSet);

    EdgeSizeCalcStrategy getEdgeSizeCalcStrategy();

    HighlightStrategy getHighlightStrategy();

    DrawStrategiesContext makeCopy(PropertyChangeSupport propertyChange);

    void setPreferences(Preferences preferences);

    void doStorePreferences();

}

/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor;


public interface DrawStrategiesContext extends conexp.frontend.latticeeditor.FigureDimensionCalcStrategyProvider {
    void setupStrategiesParams(conexp.core.ConceptsCollection conceptSet);

    EdgeSizeCalcStrategy getEdgeSizeCalcStrategy();

    HighlightStrategy getHighlightStrategy();
}

/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor.figures;

import canvas.Figure;
import conexp.frontend.latticeeditor.FigureDimensionCalcStrategyProvider;

public interface LineDiagramFigure extends Figure {

    public void setFigureDimensionCalcStrategyProvider(FigureDimensionCalcStrategyProvider figureDimensionProvider);

    boolean hasCollision();
    void setCollision(boolean value);
}

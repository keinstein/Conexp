/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.latticeeditor.figures;

import canvas.Figure;
import conexp.frontend.latticeeditor.FigureDimensionCalcStrategyProvider;

public interface LineDiagramFigure extends Figure {

    public void setFigureDimensionCalcStrategyProvider(FigureDimensionCalcStrategyProvider figureDimensionProvider);
}

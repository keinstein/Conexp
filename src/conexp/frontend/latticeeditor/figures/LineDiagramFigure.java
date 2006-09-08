/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor.figures;

import canvas.Figure;
import conexp.frontend.latticeeditor.FigureDimensionCalcStrategyProvider;

public interface LineDiagramFigure extends Figure, Collidable {

    void setFigureDimensionCalcStrategyProvider(FigureDimensionCalcStrategyProvider figureDimensionProvider);

}

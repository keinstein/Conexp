/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.latticeeditor.figures;

import canvas.figures.BorderCalculatingFigure;
import canvas.figures.ColorTransformerWithFadeOut;
import conexp.core.Set;

import java.awt.BasicStroke;

public class NodeObjectConnectionFigure extends LineFigureWithFigureDimensionCalcStrategyProvider {
    public NodeObjectConnectionFigure(AbstractConceptCorrespondingFigure start, BorderCalculatingFigure end) {
        super(start, end);
        setColorTransformer(ColorTransformerWithFadeOut.getInstance());
    }

    protected AbstractConceptCorrespondingFigure getConceptCorrespondingFigure() {
        return (AbstractConceptCorrespondingFigure) startFigure;
    }

    private static final float[] dashstyle = {4, 4};

    protected BasicStroke getLineStroke(float thickness) {
        return new BasicStroke(thickness, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_BEVEL, 1, dashstyle, 0);
    }

    public Set getIntentQuery() {
        return getConceptCorrespondingFigure().getIntentQuery();
    }
}

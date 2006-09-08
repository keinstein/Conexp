/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor.figures;

import canvas.CanvasScheme;
import canvas.figures.ColorTransformerWithFadeOut;
import canvas.figures.LineFigure;
import conexp.core.ItemSet;
import conexp.core.Set;
import conexp.frontend.latticeeditor.LatticeCanvasScheme;
import util.StringUtil;

import java.awt.Color;

public class EdgeFigure extends LineFigure implements Collidable {
    public EdgeFigure(AbstractConceptCorrespondingFigure start, AbstractConceptCorrespondingFigure end) {
        super(start, end);
        setColorTransformer(ColorTransformerWithFadeOut.getInstance());
    }


    boolean collision;

    public boolean hasCollision() {
        return collision;
    }

    public void setCollision(boolean collision) {
        this.collision = collision;
    }

    protected Color getLineColor(CanvasScheme opt) {
        if (hasCollision()) {
            return opt.getColorScheme().getCollisionColor();
        }
        return super.getLineColor(opt);
    }

    protected float getLineThickness(CanvasScheme opt) {
        if (hasCollision()) {
            return 2.0f;
        }
        return doGetLineThickness(opt);
    }


    public Set getStartIntentQuery() {
        return ((AbstractConceptCorrespondingFigure) getStartFigure()).getIntentQuery();
    }

    public Set getEndIntentQuery() {
        return ((AbstractConceptCorrespondingFigure) getEndFigure()).getIntentQuery();
    }

    protected ItemSet getStartConcept() {
        return ((AbstractConceptCorrespondingFigure) getStartFigure()).getConcept();
    }

    protected ItemSet getEndConcept() {
        return ((AbstractConceptCorrespondingFigure) getEndFigure()).getConcept();
    }

    protected float doGetLineThickness(CanvasScheme opt) {
        if (opt instanceof LatticeCanvasScheme) {
            LatticeCanvasScheme canvasSchema = (LatticeCanvasScheme) opt;
            return canvasSchema.getDrawStrategiesContext().getEdgeSizeCalcStrategy().edgeThickness(getStartConcept(), getEndConcept());
        }
        return super.getLineThickness(opt);
    }

    public String toString() {
        return StringUtil.extractClassName(getClass().getName()) + "[ startConcept:" + getStartConcept() + ", endConcept:" + getEndConcept() + "]";
    }
}

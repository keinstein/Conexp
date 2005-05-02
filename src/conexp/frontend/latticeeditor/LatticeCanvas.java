/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.latticeeditor;

import canvas.Figure;
import canvas.FigureDrawingCanvas;
import canvas.IFigurePredicate;
import conexp.core.*;
import conexp.core.layout.LayoutParameters;
import conexp.frontend.ConceptSetDrawingConsumer;
import conexp.frontend.latticeeditor.figures.AbstractConceptCorrespondingFigure;
import conexp.frontend.latticeeditor.figures.ConceptFigure;
import util.Assert;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public class LatticeCanvas extends FigureDrawingCanvas implements ConceptSetDrawingConsumer {

    public LatticeCanvas(LatticeCanvasScheme latticeCanvasScheme) {
        super(latticeCanvasScheme);
    }


    protected AffineTransform makeTranslatingTransform(Point2D viewPoint) {
        DrawParameters drawParams = getConceptSetDrawing().getLatticeDrawingSchema().getDrawParams();
        return AffineTransform.getTranslateInstance(-viewPoint.getX() + drawParams.getMinGapX(), -viewPoint.getY() + drawParams.getMinGapY());
    }

    protected LatticeCanvasScheme getLatticeCanvasSchema() {
        return (LatticeCanvasScheme) getOptions();
    }

    public ConceptsCollection getConceptSet() {
        return getConceptSetDrawing().getConceptSet();
    }

    protected ExtendedContextEditingInterface getContext() {
        return getConceptSet().getContext();
    }

    protected boolean notEmpty() {
        return getConceptSetDrawing().hasConceptSet();
    }

    protected void makeDrawing() {
        setDrawing(makeConceptSetDrawing());
    }

    public ConceptSetDrawing getConceptSetDrawing() {
        return (ConceptSetDrawing) getDrawing();
    }

    protected static ConceptSetDrawing makeConceptSetDrawing() {
        return new LatticeDrawing();
    }

    public void setConceptSetDrawing(ConceptSetDrawing drawing) {
        setFigureDrawing(drawing);
    }

    public void clearConceptSetDrawing() {
        clearSelection();
        getDrawing().clear();
    }

    public double getUpMoveConstraintForConcept(AbstractConceptCorrespondingFigure f, IFigurePredicate predicate) {
        double ret = findMinimalYDistanceToPredecessorsFiguresCenters(f, predicate);
        LayoutParameters drawParams = getDrawParameters();
        ret = Math.max(0, ret - 2 * drawParams.getMaxNodeRadius());
        Assert.isTrue(ret >= 0);
        return ret;
    }

    protected double findMinimalYDistanceToPredecessorsFiguresCenters(AbstractConceptCorrespondingFigure f, IFigurePredicate includeInComputation) {
        LatticeElement el = f.getConcept();
        double ret = Double.MAX_VALUE;
        for (int i = 0; i < el.getSuccCount(); i++) {
            LatticeElement succEl = el.getSucc(i);
            AbstractConceptCorrespondingFigure succFigures = getFigureForConcept(succEl);
            if (includeInComputation.accept(succFigures)) {
                ret = Math.min(ret, f.getCenterY() - succFigures.getCenterY());
            }
        }
        return ret;
    }

    public double getDownMoveConstraintForConcept(ConceptFigure f) {
        double ret = findMinimalYDistanceToSuccesorFigureCenters(f);
        LayoutParameters drawParams = getDrawParameters();
        ret = Math.max(0, ret - 2 * drawParams.getMaxNodeRadius());
        Assert.isTrue(ret >= 0);
        return ret;
    }

    protected DrawParameters getDrawParameters() {
        return getConceptSetDrawing().getLatticeDrawingSchema().getDrawParams();
    }

    private double findMinimalYDistanceToSuccesorFigureCenters(ConceptFigure f) {
        LatticeElement el = f.getConcept();
        double ret = Double.MAX_VALUE;
        for (int i = 0; i < el.getPredCount(); i++) {
            LatticeElement predEl = el.getPred(i);
            AbstractConceptCorrespondingFigure predFigure = getFigureForConcept(predEl);
            ret = Math.min(ret, predFigure.getCenterY() - f.getCenterY());
        }
        return ret;
    }

    public AbstractConceptCorrespondingFigure getFigureForConcept(ItemSet c) {
        return getConceptSetDrawing().getFigureForConcept(c);
    }

//-------------------------------------------------------------------------
//PART, DEVOTED TO MOVING FIGURES
    MoveStrategy figureMoveStrategy;

    protected MoveStrategy getFigureMoveStrategy() {
        return figureMoveStrategy;
    }

    public void setFigureMoveStrategy(MoveStrategy figureMoveStrategy) {
        this.figureMoveStrategy = figureMoveStrategy;
    }

    public void moveFigure(Figure f, double dx, double dy) {
        getFigureMoveStrategy().moveFigure(this, f, dx, dy);
    }

    /**
     * @test_public
     */

    protected Set getCurrentQuery() {
        if (hasSelection() && getFirstSelectedFigure() instanceof AbstractConceptCorrespondingFigure) {
            return ((AbstractConceptCorrespondingFigure) getFirstSelectedFigure()).getIntentQuery();
        }
        return null;
    }

    protected boolean canDescribePoint(Point ptSrc) {
        if (!hasSelection()) {
            return false;
        }
        if (!(getFirstSelectedFigure() instanceof ConceptFigure)) {
            return false;
        }
        Point2D pt = getWorldCoords(ptSrc);
        return getFirstSelectedFigure().contains(pt.getX(), pt.getY());
    }

    protected String describeActivePoint() {
        return ((ConceptFigure) getFirstSelectedFigure()).getDescription(getContext());
    }

    public void initPaint() {
        DrawStrategiesContext drawContext = getLatticeCanvasSchema().getDrawStrategiesContext();
        drawContext.getHighlightStrategy().setQuery(getCurrentQuery());
        if (null != getConceptSet()) {
            drawContext.setupStrategiesParams(getConceptSet());
        }
    }

    public LatticePainterOptions getPainterOptions() {
        return (LatticePainterOptions) getOptions();
    }

}

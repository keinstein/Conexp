/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor;

import canvas.Figure;
import canvas.figures.FigureWithCoords;
import canvas.figures.FigureWithCoordsMap;
import canvas.figures.IFigureWithCoords;
import com.visibleworkings.trace.Trace;
import conexp.core.ConceptsCollection;
import conexp.core.ContextEntity;
import conexp.core.ItemSet;
import conexp.core.Lattice;
import conexp.frontend.latticeeditor.figures.AbstractConceptCorrespondingFigure;
import conexp.frontend.latticeeditor.figures.LineDiagramFigure;
import conexp.util.gui.paramseditor.ParamInfo;

import java.awt.*;
import java.awt.geom.Dimension2D;

public abstract class ConceptSetDrawing extends canvas.FigureDrawing {
    FigureDimensionCalcStrategyProvider figureDimensionProvider = new FigureDimensionCalcStretegyProviderImplementation();
    LatticeDrawingOptions latticeDrawingOptions;

    public ConceptSetDrawing() {
        super();
        setOptions(new LatticePainterOptions(getLatticeDrawingSchema().getDrawParams()));
    }

    protected LatticeCanvasScheme getLatticeCanvasScheme() {
        return (LatticeCanvasScheme) getOptions();
    }

    public abstract ConceptsCollection getConceptSet();

    public abstract Lattice getLattice();

    public abstract AbstractConceptCorrespondingFigure getFigureForConcept(ItemSet c);

    public abstract int getNumberOfLevelsInDrawing();

    public abstract boolean hasConceptSet();

    public Dimension2D makeDrawingDimension(int width, int height) {
        DrawParameters drawParams = getLatticeDrawingSchema().getDrawParams();
        return new Dimension(drawParams.getMinGapX() * 2 + width, drawParams.getMinGapY() * 2 + height);
    }


    private LabelingStrategyModelFactory makeDrawStrategiesFactory() {
        return new conexp.frontend.latticeeditor.drawstrategies.DefaultLabelingStrategiesFactory();
    }

    private LabelingStrategiesContextImpl labelingStrategiesContext;

    private LabelingStrategyModelFactory factory = makeDrawStrategiesFactory();

    protected synchronized LabelingStrategiesContext getLabelingStrategiesContext() {
        if (null == labelingStrategiesContext) {
            labelingStrategiesContext = new LabelingStrategiesContextImpl(factory, null);
            labelingStrategiesContext.addPropertyChangeListener(new LabelingStrategiesEventHandler());
        }
        return labelingStrategiesContext;
    }

    class LabelingStrategiesEventHandler implements java.beans.PropertyChangeListener {
        public void propertyChange(java.beans.PropertyChangeEvent evt) {
            String propertyName = evt.getPropertyName();
            Trace.gui.eventm("Get message for lattice conceptSetDrawing labelingStrategy", evt.getPropertyName());
            if (propertyName.equals("drawAttribs") ||
                    propertyName.equals("drawObjects")) {
                changeLabelingStrategy((ILabelingStrategy) evt.getOldValue(), (ILabelingStrategy) evt.getNewValue());
            }
        }
    }

    public ParamInfo[] getLabelingStrategiesParams() {
        return getLabelingStrategiesContextImpl().getParams();
    }

    protected LabelingStrategiesContextImpl getLabelingStrategiesContextImpl() {
        return (LabelingStrategiesContextImpl) getLabelingStrategiesContext();
    }

    /**
     *  TODO: Clean up
     * */
    private synchronized void changeLabelingStrategy(ILabelingStrategy oldStrategy, ILabelingStrategy newStrategy) {
        oldStrategy.shutdown(this);

        if (hasConceptSet()) {
            getLabelingStrategiesContext().setupLabelingStrategies(getLattice().getContext());
            newStrategy.init(this, getLatticeDrawingOptions().getDrawParams());
            fireNeedUpdate();
        }
    }

    private FigureWithCoordsMap attributeLabels = new FigureWithCoordsMap();
    private FigureWithCoordsMap objectLabels = new FigureWithCoordsMap();
    private FigureWithCoordsMap conceptLabels = new FigureWithCoordsMap();

    //todo: check transposing for preserving properties as object and attribute
    public void setFigureForContextObject(ContextEntity object, IFigureWithCoords figure) {
        if (object.isObject()) {
            objectLabels.put(object, figure);
        } else {
            attributeLabels.put(object, figure);
        }
    }

    public FigureWithCoords getLabelForAttribute(ContextEntity attribute) {
        return attributeLabels.get(attribute);
    }

    public boolean hasLabelsForAttributes() {
        return !attributeLabels.isEmpty();
    }

    public void clearAttributeLabels() {
        attributeLabels.clear();
    }

    public FigureWithCoords getLabelForObject(ContextEntity attribute) {
        return objectLabels.get(attribute);
    }

    public boolean hasLabelsForObjects() {
        return !objectLabels.isEmpty();
    }

    public void clearObjectLabels() {
        objectLabels.clear();
    }

    public FigureWithCoords getLabelForConcept(ItemSet concept) {
        return conceptLabels.get(concept);
    }

    public boolean hasLabelsForConcepts() {
        return !conceptLabels.isEmpty();
    }

    public void setLabelForConcept(ItemSet concept, IFigureWithCoords label) {
        conceptLabels.put(concept, label);
    }

    public void clearConceptLabels() {
        conceptLabels.clear();
    }

    protected FigureDimensionCalcStrategyProvider getFigureDimensionProvider() {
        return figureDimensionProvider;
    }

    protected void doAddFigure(Figure f) {
        if (f instanceof LineDiagramFigure) {
            LineDiagramFigure lineDiagramFigure = (LineDiagramFigure) f;
            lineDiagramFigure.setFigureDimensionCalcStrategyProvider(getFigureDimensionProvider());
        }
        super.doAddFigure(f);
    }

    protected void initStrategies() {
        getLatticeCanvasScheme().getDrawStrategiesContext().setupStrategiesParams(getConceptSet());
        getLabelingStrategiesContext().initStrategies(getConceptSet().getContext(), this);
    }

    public void setLatticeDrawingOptions(LatticeDrawingOptions latticeDrawingOptions) {
        this.latticeDrawingOptions = latticeDrawingOptions;
    }

    public synchronized LatticeDrawingOptions getLatticeDrawingOptions() {
        if (null == latticeDrawingOptions) {
            latticeDrawingOptions = new LatticeDrawingOptions();
        }
        return latticeDrawingOptions;
    }

    public LatticeDrawingSchema getLatticeDrawingSchema() {
        return getLatticeDrawingOptions();
    }

    public abstract void drawingParametersChanged();

    class FigureDimensionCalcStretegyProviderImplementation implements FigureDimensionCalcStrategyProvider {
        public FigureDimensionCalcStrategy getFigureDimensionCalcStrategy() {
            return getLatticeCanvasScheme().getDrawStrategiesContext().getFigureDimensionCalcStrategy();
        }
    }

}

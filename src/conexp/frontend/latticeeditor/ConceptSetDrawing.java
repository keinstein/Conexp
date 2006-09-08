/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor;

import canvas.Figure;
import canvas.FigureDrawing;
import canvas.figures.FigureWithCoords;
import canvas.figures.FigureWithCoordsMap;
import canvas.figures.IFigureWithCoords;
import com.visibleworkings.trace.Trace;
import conexp.core.ConceptsCollection;
import conexp.core.ContextEntity;
import conexp.core.ItemSet;
import conexp.core.Lattice;
import conexp.frontend.latticeeditor.drawstrategies.DefaultLabelingStrategiesFactory;
import conexp.frontend.latticeeditor.figures.AbstractConceptCorrespondingFigure;
import conexp.frontend.latticeeditor.figures.LineDiagramFigure;
import conexp.util.gui.paramseditor.ParamInfo;

import java.awt.Dimension;
import java.awt.geom.Dimension2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public abstract class ConceptSetDrawing extends FigureDrawing {
    FigureDimensionCalcStrategyProvider figureDimensionProvider = new FigureDimensionCalcStretegyProviderImplementation();
    LatticeDrawingOptions latticeDrawingOptions;

    protected ConceptSetDrawing() {
        super();
        setOptions(new LatticePainterOptions(getLatticeDrawingSchema().getDrawParams()));
    }

    public LatticeCanvasScheme getLatticeCanvasScheme() {
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


    private static LabelingStrategyModelFactory makeDrawStrategiesFactory() {
        return new DefaultLabelingStrategiesFactory();
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

    public LatticePainterOptions getPainterOptions() {
        return (LatticePainterOptions) getOptions();
    }

    public void restorePreferences() {
        getEditableDrawParameters().restorePreferences();
        getPainterOptions().restorePreferences();
        getLabelingStrategiesContextImpl().restorePreferences();
    }

    public LatticePainterDrawParams getEditableDrawParameters() {
        return getLatticeDrawingOptions().getEditableDrawingOptions();
    }


    public void storePreferences() {
        getEditableDrawParameters().doStorePreferences();
        getPainterOptions().doStorePreferences();
        getLabelingStrategiesContextImpl().doStorePreferences();
    }


    class LabelingStrategiesEventHandler implements PropertyChangeListener {
        public void propertyChange(PropertyChangeEvent evt) {
            String propertyName = evt.getPropertyName();
            Trace.gui.eventm("Get message for lattice conceptSetDrawing labelingStrategy", evt.getPropertyName());
            if ("drawAttribs".equals(propertyName) ||
                    "drawObjects".equals(propertyName)) {
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
     * TODO: Clean up
     */
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
//    private FigureWithCoordsMap conceptLabels = new FigureWithCoordsMap();
    private FigureWithCoordsMap upConceptLabels = new FigureWithCoordsMap();
    private FigureWithCoordsMap downConceptLabels = new FigureWithCoordsMap();

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

    public IFigureWithCoords getLabelForConcept(ItemSet concept) {
        return getDownLabelForConcept(concept);
    }

    public boolean hasLabelsForConcepts() {
        return hasDownLabelsForConcepts() || hasUpLabelsForConcepts();
    }

    public void setUpLabelForConcept(ItemSet concept,
                                     IFigureWithCoords label) {
        upConceptLabels.put(concept, label);
    }


    public void setDownLabelForConcept(ItemSet concept, IFigureWithCoords label) {
        downConceptLabels.put(concept, label);
    }

    public void clearConceptLabels() {
        clearDownLabelsForConcepts();
    }

    public void clearDownLabelsForConcepts() {
        downConceptLabels.clear();
    }


    public void clearUpLabelsForConcepts() {
        upConceptLabels.clear();
    }


    public boolean hasUpLabelsForConcepts() {
        return !upConceptLabels.isEmpty();
    }

    public boolean hasDownLabelsForConcepts() {
        return !downConceptLabels.isEmpty();
    }

    public IFigureWithCoords getDownLabelForConcept(ItemSet conceptNode) {
        return downConceptLabels.get(conceptNode);
    }

    public IFigureWithCoords getUpLabelForConcept(ItemSet conceptNode) {
        return upConceptLabels.get(conceptNode);
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
        getDrawStrategiesContext().setupStrategiesParams(getConceptSet());
        getLabelingStrategiesContext().initStrategies(getConceptSet().getContext(), this);
    }

    protected DrawStrategiesContext getDrawStrategiesContext() {
        return getLatticeCanvasScheme().getDrawStrategiesContext();
    }

    public synchronized LatticeDrawingOptions getLatticeDrawingOptions() {
        if (null == latticeDrawingOptions) {
            latticeDrawingOptions = new LatticeDrawingOptions();
        }
        return latticeDrawingOptions;
    }

    public DrawParamsProvider getLatticeDrawingSchema() {
        return getLatticeDrawingOptions();
    }

    public abstract void drawingParametersChanged();

    class FigureDimensionCalcStretegyProviderImplementation implements FigureDimensionCalcStrategyProvider {
        public FigureDimensionCalcStrategy getFigureDimensionCalcStrategy() {
            return getDrawStrategiesContext().getFigureDimensionCalcStrategy();
        }
    }

}

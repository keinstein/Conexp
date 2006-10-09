/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor;

import canvas.BaseFigureVisitor;
import canvas.Figure;
import com.visibleworkings.trace.Trace;
import conexp.core.ConceptsCollection;
import conexp.core.Edge;
import conexp.core.ItemSet;
import conexp.core.Lattice;
import conexp.core.LatticeElement;
import conexp.core.layout.ConceptCoordinateMapper;
import conexp.core.layout.Layouter;
import conexp.core.layout.LayouterProvider;
import conexp.core.layoutengines.LayoutEngine;
import conexp.core.layoutengines.LayoutListener;
import conexp.core.layoutengines.ThreadedLayoutEngine;
import conexp.frontend.latticeeditor.figures.AbstractConceptCorrespondingFigure;
import conexp.frontend.latticeeditor.figures.ConceptFigure;
import conexp.frontend.latticeeditor.figures.EdgeFigure;
import conexp.frontend.latticeeditor.queries.ConceptNodeQuery;
import conexp.util.gui.strategymodel.StrategyValueItem;
import util.collection.CollectionFactory;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class LatticeDrawing extends ConceptSetDrawing {

    LayouterProvider layoutProvider = new DefaultLayouterProvider();
    List edges = CollectionFactory.createDefaultList();

    public LayouterProvider getLayoutProvider() {
        return layoutProvider;
    }

    public void setLayoutProvider(LayouterProvider layoutProvider) {
        this.layoutProvider = layoutProvider;
        getLayoutEngine().init(layoutProvider);
    }


    public LatticeDrawing makeSetupCopy() {
        LatticeDrawing ret = new LatticeDrawing();
        ret.setOptions(getOptions().makeCopy());
        return ret;
    }


    private class DefaultLayouterProvider implements LayouterProvider {
        public Layouter getLayouter() {
            return getPainterOptions().getLayoutStrategy();
        }
    }

    public void initPaint() {
        updateCollisions();
    }

    boolean needUpdateCollisions;


    public void drawingParametersChanged() {
        markNeedUpdateCollisions();
    }

    public void markNeedUpdateCollisions() {
        if (isCollisionDetectionEnabled()) {
            doMarkNeedUpdateCollisions();
        }
    }

    public boolean hasNeedUpdateCollisions() {
        return needUpdateCollisions;
    }

    public boolean isUpdatingCollisions() {
        return collisionThread != null;
    }

    private void markCollisionDetectionStarted() {
        needUpdateCollisions = false;
    }

    CollisionDetector collisionDetector = new CollisionDetector();

    protected void onAfterFigureMove(Figure f) {
        super.onAfterFigureMove(f);
        markNeedUpdateCollisions();
    }

    Thread collisionThread;

    public void onCollisionThreadEnd() {
        collisionThread = null;
        fireNeedUpdate();
    }

    public void updateCollisions() {
        if (hasNeedUpdateCollisions()) {
            if (collisionThread == null) {
                collisionThread = new CollisionUpdateThread();
                collisionThread.start();
            }
        }
    }

    public void shutdown() {
        getLayoutEngine().shutdown();
    }

    public void layoutLattice() {
        Trace.gui.debugm("called layout lattice");
        if (!hasLattice()) {
            return;
        }
        if (getLattice().isEmpty()) {
            return;
        }
        getLayoutEngine().startLayout(getLattice(), getDrawParams());
    }

    public LayoutEngine getLayoutEngine() {
        return layoutEngine;
    }

    LayoutListener layoutListener = new LayoutListener() {
        public void layoutChange(ConceptCoordinateMapper mapper) {
            setCoordinatesFromMapper(mapper);
        }
    };

    public void setCoordinatesFromMapper(ConceptCoordinateMapper mapper) {
        BaseFigureVisitor visitor = new CoordinateMapperFigureVisitor(mapper);
        applyUpdatingFigureVisitor(visitor);
    }

    public void setLayoutEngine(LayoutEngine layoutEngine) {
        if (!layoutEngine.equals(this.layoutEngine)) {
            if (null != this.layoutEngine) {
                this.layoutEngine.removeLayoutListener(layoutListener);
            }
        }
        this.layoutEngine = layoutEngine;
        this.layoutEngine.addLayoutListener(layoutListener);
        layoutEngine.init(this.getLayoutProvider());
    }

    private LayoutEngine layoutEngine;

    public LatticeDrawing() {
        super();
        setLayoutEngine(new ThreadedLayoutEngine());
        setLayoutProvider(new DefaultLayouterProvider());
        getEditableDrawParameters().addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                if (LatticePainterDrawParams.SHOW_COLLISIONS_PROPERTY.equals(evt.getPropertyName())) {
                    if (!isEmpty()) {
                        doMarkNeedUpdateCollisions();
                    }
                }
            }
        });
    }

    private void doMarkNeedUpdateCollisions() {
        needUpdateCollisions = true;
    }

    public DrawParameters getDrawParams() {
        return getLatticeDrawingSchema().getDrawParams();
    }

    private Lattice lattice;

    public void setLattice(Lattice lattice) {
        if (hasLattice()) {
            getLabelingStrategiesContext().shutdownStrategies(this);
        }
        clear();
        this.lattice = lattice;
        if (hasLattice()) {
            makeLatticeDiagramFigures();
            initStrategies();
            markNeedUpdateCollisions();
        }
    }

    boolean isCollisionDetectionEnabled() {
        return getEditableDrawParameters().isShowCollisions();
    }


    public Lattice getLattice() {
        return lattice;
    }

    public ConceptsCollection getConceptSet() {
        return getLattice();
    }

    public int getNumberOfLevelsInDrawing() {

        return hasLattice() ? getLattice().getHeight() : 0;
    }

    public boolean hasConceptSet() {
        return hasLattice();
    }

    public boolean hasLattice() {
        return null != lattice;
    }

    public String getAttributeLabelingStrategyKey() {
        return getLabelingStrategiesContext().getAttributeLabelingStrategyKey();
    }

    public boolean setAttributeLabelingStrategyKey(String key) {
        return getLabelingStrategiesContext().setAttributeLabelingStrategyByKey(key);
    }

    public String getObjectLabelingStrategyKey() {
        return getLabelingStrategiesContext().getObjectLabelingStrategyKey();
    }

    public boolean setObjectLabelingStrategyKey(String key) {
        return getLabelingStrategiesContext().setObjectLabelingStrategyKey(key);
    }

    public StrategyValueItem getNodeRadiusStrategyItem() {
        return getDrawStrategiesContext().getNodeRadiusStrategyItem();
    }


    public StrategyValueItem getEdgeSizeCalcStrategyItem() {
        return getDrawStrategiesContext().getEdgeSizeCalcStrategyItem();
    }


    public StrategyValueItem getHighlightStrategyItem() {
        return getDrawStrategiesContext().getHighlightStrategyItem();
    }

    public StrategyValueItem getLayoutStrategyItem() {
        return getPainterOptions().
                getLatticePainterDrawStrategyContext().
                getLayoutStrategyItem();
    }


    protected void makeLatticeDiagramFigures() {
        makeConceptsFigures();
        makeEdgeFigures();
    }

    private ConceptFigure[] elementFigureMap;

    public AbstractConceptCorrespondingFigure getFigureForConcept(ItemSet c) {
        return elementFigureMap[c.getIndex()];
    }

    private void makeConceptsFigures() {
        elementFigureMap = new ConceptFigure[lattice.conceptsCount()];
        lattice.forEach(new Lattice.LatticeElementVisitor() {
            public void visitNode(LatticeElement node) {
                ConceptFigure f = new ConceptFigure(makeConceptQueryForElement(node));
                elementFigureMap[node.getIndex()] = f;
                addFigure(f);
            }
        });
    }

    //todo:sye - change to protected after restructuting tests
    public ConceptNodeQuery makeConceptQueryForElement(LatticeElement node) {
        return ConceptNodeQuery.createNodeQuery(lattice, node);
    }

    private void makeEdgeFigures() {
        edges.clear();
        lattice.forEach(new Lattice.LatticeElementVisitor() {
            public void visitNode(LatticeElement node) {
                Iterator edgeIterator = node.successorsEdges();
                while (edgeIterator.hasNext()) {
                    Edge e = (Edge) edgeIterator.next();
                    EdgeFigure edgeFigure = makeEdgeFigure(e);
                    edges.add(edgeFigure);
                    addFigure(edgeFigure);
                }
            }
        });
    }

    private EdgeFigure makeEdgeFigure(Edge e) {
        return new EdgeFigure(getFigureForConcept(e.getStart()),
                getFigureForConcept(e.getEnd()));
    }


    protected void finalize() throws Throwable {
        super.finalize();
        shutdown();
    }

    public List getEdges() {
        return Collections.unmodifiableList(edges);
    }


    private class CollisionUpdateThread extends Thread {
        public void run() {
            markCollisionDetectionStarted();
            CollisionDetector.detectCollisions(LatticeDrawing.this);
            onCollisionThreadEnd();
        }
    }


}

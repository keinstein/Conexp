/*
 * User: Serhiy Yevtushenko
 * Date: Feb 10, 2002
 * Time: 6:30:15 PM
 */
package conexp.frontend.latticeeditor;

import canvas.BaseFigureVisitor;
import canvas.figures.ConnectionFigure;
import com.visibleworkings.trace.Trace;
import conexp.core.*;
import conexp.core.layout.ConceptCoordinateMapper;
import conexp.core.layout.Layouter;
import conexp.core.layout.LayouterProvider;
import conexp.core.layoutengines.LayoutListener;
import conexp.core.layoutengines.ThreadedLayoutEngine;
import conexp.frontend.latticeeditor.figures.AbstractConceptCorrespondingFigure;
import conexp.frontend.latticeeditor.figures.ConceptFigure;
import conexp.frontend.latticeeditor.figures.EdgeFigure;
import conexp.frontend.latticeeditor.queries.ConceptNodeQuery;

import java.util.Iterator;

public class LatticeDrawing extends ConceptSetDrawing {

    LayouterProvider layoutProvider = new DefaultLayouterProvider();

    public LayouterProvider getLayoutProvider() {
        return layoutProvider;
    }

    public void setLayoutProvider(LayouterProvider layoutProvider) {
        this.layoutProvider = layoutProvider;
        getLayoutEngine().init(layoutProvider);
    }

    private class DefaultLayouterProvider implements LayouterProvider {
        public Layouter getLayouter() {
            return getPainterOptions().getLayoutStrategy();
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
        if(getLattice().isEmpty()){
            return;
        }
        getLayoutEngine().startLayout(getLattice(), getDrawParams());
    }

    public LayoutEngine getLayoutEngine() {
        return layoutEngine;
    }

    LayoutListener layoutListener = new LayoutListener(){
        public void layoutChange(ConceptCoordinateMapper mapper) {
            BaseFigureVisitor visitor = new CoordinateMapperFigureVisitor(mapper);
            applyUpdatingFigureVisitor(visitor);
        }
    };

    public void setLayoutEngine(LayoutEngine layoutEngine) {
        if(layoutEngine!=this.layoutEngine){
            if(null!=this.layoutEngine){
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
        }
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

    private LatticePainterOptions getPainterOptions() {
        return (LatticePainterOptions) getOptions();
    }

    public Lattice getLattice() {
        return lattice;
    }

    public ConceptsCollection getConceptSet() {
        return getLattice();
    }

    public int getNumberOfLevelsInDrawing() {
        return getLattice().getHeight();
    }

    public boolean hasConceptSet() {
        return hasLattice();
    }

    public boolean hasLattice() {
        return null != lattice;
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
                ConceptFigure f = new ConceptFigure(new ConceptNodeQuery(lattice.getContext(), node, lattice.getFeatureMask()));
                elementFigureMap[node.getIndex()] = f;
                addFigure(f);
            }
        });
    }

    private void makeEdgeFigures() {
        lattice.forEach(new Lattice.LatticeElementVisitor() {
            public void visitNode(LatticeElement node) {
                Iterator edges = node.successors();
                while (edges.hasNext()) {
                    Edge e = (Edge) edges.next();
                    addFigure(makeEdgeFigure(e));
                }

            }
        });
    }

    private ConnectionFigure makeEdgeFigure(Edge e) {
        return new EdgeFigure(
                getFigureForConcept(e.getStart()),
                getFigureForConcept(e.getEnd()));
    }


    public void finalize() throws java.lang.Throwable {
        super.finalize();
        shutdown();
    }


}

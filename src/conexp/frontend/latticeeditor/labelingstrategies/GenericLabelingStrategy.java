package conexp.frontend.latticeeditor.labelingstrategies;

import canvas.BaseFigureVisitor;
import canvas.Figure;
import canvas.figures.TextFigure;
import conexp.frontend.latticeeditor.ConceptQuery;
import conexp.frontend.latticeeditor.ConceptSetDrawing;
import conexp.frontend.latticeeditor.DrawParameters;
import conexp.frontend.latticeeditor.figures.AbstractConceptCorrespondingFigure;
import conexp.frontend.latticeeditor.figures.CompositeFigureWithFigureDimensionCalcStrategyProvider;
import conexp.frontend.latticeeditor.figures.DefaultFigureVisitor;
import conexp.frontend.latticeeditor.figures.NodeObjectConnectionFigure;

public abstract class GenericLabelingStrategy extends conexp.frontend.latticeeditor.LabelingStrategy {

    private java.util.HashMap conceptLabelsMap = new java.util.HashMap();
    protected DrawParameters opt;

    public class InitStrategyVisitor extends DefaultFigureVisitor {
        ConceptSetDrawing drawing;

        InitStrategyVisitor(ConceptSetDrawing drawing) {
            this.drawing = drawing;
        }

        public void visitConceptCorrespondingFigure(AbstractConceptCorrespondingFigure f) {
            if (!accept(f.getConceptQuery())) {
                return;
            }
            setConnectedObject(f, makeConnectedObject(drawing, f));
        }
    }

    class ShutDownStrategyVisitor extends DefaultFigureVisitor {

        ConceptSetDrawing drawing;

        ShutDownStrategyVisitor(ConceptSetDrawing drawing) {
            this.drawing = drawing;
        }

        public void visitConceptCorrespondingFigure(AbstractConceptCorrespondingFigure f) {
            if (!accept(f.getConceptQuery())) {
                return;
            }
            Object obj = getConnectedObject(f);
            if (null == obj) {
                return;
            }
            removeConnectedObjectFromContainer(drawing, f, obj);
            removeConnectedObject(f);
        }
    }


    /**
     * GenericLabelingStrategy constructor comment.
     */
    public GenericLabelingStrategy(DrawParameters opt) {
        super();
        this.opt = opt;
    }

    public abstract boolean accept(ConceptQuery query);

    /**
     * Insert the method's description here.
     * Creation date: (25.12.00 0:01:27)
     * @return java.lang.Object
     * @param f conexp.frontend.latticeeditor.Figures.ConceptFigure
     */
    private Object getConnectedObject(AbstractConceptCorrespondingFigure f) {
        return conceptLabelsMap.get(f);
    }

    public boolean hasConnectedObjects() {
        return !conceptLabelsMap.isEmpty();
    }

    protected abstract Object makeConnectedObject(ConceptSetDrawing fd, AbstractConceptCorrespondingFigure f);


    private void removeConnectedObject(AbstractConceptCorrespondingFigure f) {
        conceptLabelsMap.remove(f);
    }

    protected abstract void removeConnectedObjectFromContainer(ConceptSetDrawing fd, AbstractConceptCorrespondingFigure f, Object obj);

    private void setConnectedObject(AbstractConceptCorrespondingFigure f, Object obj) {
        conceptLabelsMap.put(f, obj);
    }

    public void setContext(conexp.core.ExtendedContextEditingInterface cxt) {
        //DEFAULT EMPTY IMPLEMENTATION
    }

    public BaseFigureVisitor makeInitStrategyVisitor(ConceptSetDrawing drawing) {
        return new InitStrategyVisitor(drawing);
    }

    public BaseFigureVisitor makeShutDownVisitor(ConceptSetDrawing drawing) {
        return new ShutDownStrategyVisitor(drawing);
    }

    protected Figure makeConnectedFigure(AbstractConceptCorrespondingFigure f, TextFigure tf) {
        NodeObjectConnectionFigure connector = new NodeObjectConnectionFigure(f, tf);
        CompositeFigureWithFigureDimensionCalcStrategyProvider cf = new CompositeFigureWithFigureDimensionCalcStrategyProvider();
        cf.addFigure(tf);
        cf.addFigure(connector);
        return cf;
    }

}
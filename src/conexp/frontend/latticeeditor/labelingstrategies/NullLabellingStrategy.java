package conexp.frontend.latticeeditor.labelingstrategies;

import conexp.frontend.latticeeditor.ConceptSetDrawing;

/**
 * Insert the type's description here.
 * Creation date: (22.12.00 2:15:35)
 * @author
 */
public class NullLabellingStrategy extends conexp.frontend.latticeeditor.LabelingStrategy {
    private static conexp.frontend.latticeeditor.figures.DefaultFigureVisitor visitor = new conexp.frontend.latticeeditor.figures.DefaultFigureVisitor();
    private static NullLabellingStrategy singleton;

    /**
     * EmptyLabellingStrategy constructor comment.
     */
    private NullLabellingStrategy() {
        super();
    }

    /**
     * Insert the method's description here.
     * Creation date: (24.12.00 4:01:36)
     * @return conexp.frontend.latticeeditor.labelingstrategies.NullLabellingStrategy
     */
    public static NullLabellingStrategy makeNull() {
        if (null == singleton) {
            singleton = new NullLabellingStrategy();
        }
        return singleton;
    }

    /**
     * setContext method comment.
     */
    public void setContext(conexp.core.ExtendedContextEditingInterface cxt) {
    }

    /**
     * makeInitStrategyVisitor method comment.
     */
    public canvas.BaseFigureVisitor makeInitStrategyVisitor(ConceptSetDrawing fd) {
        return visitor;
    }

    /**
     * makeShutDownVisitor method comment.
     */
    public canvas.BaseFigureVisitor makeShutDownVisitor(conexp.frontend.latticeeditor.ConceptSetDrawing fd) {
        return visitor;
    }
}
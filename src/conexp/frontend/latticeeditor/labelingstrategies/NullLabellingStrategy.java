/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.latticeeditor.labelingstrategies;

import conexp.core.layout.LayoutParameters;
import conexp.frontend.latticeeditor.ConceptSetDrawing;


public class NullLabellingStrategy extends conexp.frontend.latticeeditor.LabelingStrategy {
    private static conexp.frontend.latticeeditor.figures.DefaultFigureVisitor visitor = new conexp.frontend.latticeeditor.figures.DefaultFigureVisitor();
    private static NullLabellingStrategy singleton;

    /**
     *  made public in order to allow to create via reflexion
     */
    public NullLabellingStrategy() {
        super();
    }

    public static NullLabellingStrategy makeNull() {
        if (null == singleton) {
            singleton = new NullLabellingStrategy();
        }
        return singleton;
    }

    public void setContext(conexp.core.ExtendedContextEditingInterface cxt) {
    }

    public canvas.BaseFigureVisitor makeInitStrategyVisitor(ConceptSetDrawing fd, LayoutParameters opt) {
        return visitor;
    }

    public canvas.BaseFigureVisitor makeShutDownVisitor(conexp.frontend.latticeeditor.ConceptSetDrawing fd) {
        return visitor;
    }
}

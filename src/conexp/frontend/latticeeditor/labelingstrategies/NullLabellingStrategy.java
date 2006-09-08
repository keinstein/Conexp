/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor.labelingstrategies;

import canvas.BaseFigureVisitor;
import conexp.core.ExtendedContextEditingInterface;
import conexp.core.layout.LayoutParameters;
import conexp.frontend.latticeeditor.ConceptSetDrawing;
import conexp.frontend.latticeeditor.LabelingStrategy;
import conexp.frontend.latticeeditor.figures.DefaultFigureVisitor;


public class NullLabellingStrategy extends LabelingStrategy {
    private static DefaultFigureVisitor visitor = new DefaultFigureVisitor();
    private static NullLabellingStrategy singleton;

    /**
     * made public in order to allow to create via reflexion
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

    public void setContext(ExtendedContextEditingInterface cxt) {
    }

    public BaseFigureVisitor makeInitStrategyVisitor(ConceptSetDrawing fd, LayoutParameters opt) {
        return visitor;
    }

    public BaseFigureVisitor makeShutDownVisitor(ConceptSetDrawing fd) {
        return visitor;
    }
}

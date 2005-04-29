/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.latticeeditor;

import conexp.core.layout.LayoutParameters;
import canvas.BaseFigureVisitor;


public abstract class LabelingStrategy implements ILabelingStrategy {
    public LabelingStrategy() {
        super();
    }

    public void shutdown(ConceptSetDrawing drawing) {
        drawing.visitFigures(makeShutDownVisitor(drawing));
//        conceptSetDrawing.applyChanges();
    }

    public void init(ConceptSetDrawing drawing, LayoutParameters drawParams) {
        drawing.visitFiguresAndApplyChanges(makeInitStrategyVisitor(drawing, drawParams));
    }

    public abstract BaseFigureVisitor makeInitStrategyVisitor(ConceptSetDrawing fd, LayoutParameters opt);

    public abstract BaseFigureVisitor makeShutDownVisitor(ConceptSetDrawing fd);
}

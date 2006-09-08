/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor.figures;

import canvas.BaseFigureVisitor;


public interface FigureVisitor extends BaseFigureVisitor {
    /**
     * Insert the method's description here.
     * Creation date: (20.12.00 22:09:31)
     *
     * @param f conexp.frontend.latticeeditor.ConceptFigure
     */
    void visitConceptCorrespondingFigure(AbstractConceptCorrespondingFigure f);
}

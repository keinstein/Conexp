package conexp.frontend.latticeeditor.figures;

import canvas.BaseFigureVisitor;


/**
 * Insert the type's description here.
 * Creation date: (20.12.00 21:46:54)
 * @author Serhiy Yevtushenko
 */
public interface FigureVisitor extends BaseFigureVisitor {
    /**
     * Insert the method's description here.
     * Creation date: (20.12.00 22:09:31)
     * @param f conexp.frontend.latticeeditor.ConceptFigure
     */
    void visitConceptCorrespondingFigure(AbstractConceptCorrespondingFigure f);
}
package conexp.frontend.latticeeditor;


import conexp.frontend.latticeeditor.figures.AbstractConceptCorrespondingFigure;
import conexp.frontend.latticeeditor.figures.DefaultFigureVisitor;

/**
 * Insert the type's description here.
 * Creation date: (25.12.00 21:52:10)
 */
public class RescaleByYFigureVisitor extends DefaultFigureVisitor {
    private int minDelta;
    private int latticeHeight;
    private int shift;

    public RescaleByYFigureVisitor(int shift, int minDelta, int latticeHeight) {
        this.minDelta = minDelta;
        this.latticeHeight = latticeHeight;
        this.shift = shift;
    }

    public void visitConceptCorrespondingFigure(AbstractConceptCorrespondingFigure f) {
        f.moveBy(0, shift + (latticeHeight - f.getConcept().getHeight()) * minDelta - f.getCenterY());
    }
}
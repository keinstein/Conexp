package conexp.frontend.latticeeditor;


import conexp.frontend.latticeeditor.figures.AbstractConceptCorrespondingFigure;
import conexp.frontend.latticeeditor.figures.DefaultFigureVisitor;

/**
 * Insert the type's description here.
 * Creation date: (25.12.00 21:52:10)
 * @author Serhiy Yevtushenko
 */
public class RescaleByXFigureVisitor extends DefaultFigureVisitor {
    private int shift;
    private double coeff;

    /**
     * Insert the method's description here.
     * Creation date: (25.12.00 21:54:43)
     * @param shift int
     * @param coeff double
     */
    public RescaleByXFigureVisitor(int shift, double coeff) {
        this.shift = shift;
        this.coeff = coeff;
    }

    public void visitConceptCorrespondingFigure(AbstractConceptCorrespondingFigure f) {
        f.moveBy(((coeff - 1.) * (f.getCenterX() - shift)), 0);
    }
}
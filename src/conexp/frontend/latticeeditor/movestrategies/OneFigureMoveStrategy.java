package conexp.frontend.latticeeditor.movestrategies;

import conexp.frontend.latticeeditor.LatticeCanvas;
import conexp.frontend.latticeeditor.MoveStrategy;
import conexp.frontend.latticeeditor.figures.ConceptFigure;

public class OneFigureMoveStrategy implements MoveStrategy {
    public void moveFigure(LatticeCanvas canvas, canvas.Figure f, double dx, double dy) {
        if (f instanceof ConceptFigure) {
            ConceptFigure conceptFigure = (ConceptFigure) f;
            if (dy < 0) {
                dy = -Math.min(canvas.getUpMoveConstraintForConcept(conceptFigure), -dy);
            } else {
                dy = Math.min(canvas.getDownMoveConstraintForConcept(conceptFigure), dy);
            }
        }
        f.moveBy(dx, dy);
    }
}

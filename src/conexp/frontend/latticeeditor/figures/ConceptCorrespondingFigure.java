/*
 * User: Serhiy Yevtushenko
 * Date: Jun 2, 2002
 * Time: 7:20:04 PM
 */
package conexp.frontend.latticeeditor.figures;

import conexp.core.LatticeElement;

public abstract class ConceptCorrespondingFigure extends AbstractConceptCorrespondingFigure {

    private LatticeElement concept;

    public LatticeElement getConcept() {
        return concept;
    }

    public ConceptCorrespondingFigure(LatticeElement el) {
        super();
        this.concept = el;
    }


}

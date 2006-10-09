package conexp.frontend.latticeeditor;

import conexp.core.Set;
import conexp.util.GenericStrategy;
import conexp.frontend.latticeeditor.figures.AbstractConceptCorrespondingFigure;

public interface ConceptHighlightStrategy extends GenericStrategy {
    /**
     * Determines whether figure corresponid to specific attributes
     * should be highlighted.
     *
     * @param attribs
     * @return <code>true</code> if figure corresponding to query should be highligther
     */
    boolean highlightQuery(Set attribs);

    /**
     * Determines whether edge in lattice drawing should be highlighted.
     * @param startAttribs
     * @param endAttribs
     */

    boolean highlightEdge(Set startAttribs, Set endAttribs);

    ConceptHighlightStrategy createNew();

}

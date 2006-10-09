/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.latticeeditor.highlightstrategies;

import conexp.core.ConceptIterator;
import conexp.core.LatticeElement;
import conexp.core.LatticeElementCollection;
import conexp.core.Set;
import conexp.frontend.latticeeditor.ConceptHighlightStrategy;
import conexp.frontend.latticeeditor.ConceptHighlightAtomicStrategy;
import conexp.frontend.latticeeditor.figures.AbstractConceptCorrespondingFigure;


public class NeigboursHighlightStrategy implements ConceptHighlightAtomicStrategy {

    LatticeElement node;

    public void initFromFigure(AbstractConceptCorrespondingFigure figure) {
        if(figure!=null){
            node = figure.getConcept();
        }else{
            node = null;
        }
    }

    public ConceptHighlightStrategy createNew() {
        return new NeigboursHighlightStrategy();
    }

    public boolean highlightQuery(Set attribs) {
        if (isQueryAttributes(attribs)) {
            return true;
        }
        return hasAtLeastOneNodeWithAttribes(node.getSuccessors(), attribs)
                || hasAtLeastOneNodeWithAttribes(node.getPredecessors(), attribs);
    }

    private boolean isQueryAttributes(Set attribs) {
        if(node==null){
            return false;
        }
        return node.getAttribs().equals(attribs);
    }

    private boolean hasAtLeastOneNodeWithAttribes(LatticeElementCollection successors, Set attribs) {
        successors.iterator();
        for (ConceptIterator iterator = successors.iterator(); iterator.hasNext();) {
            LatticeElement concept = iterator.nextConcept();
            if (concept.getAttribs().equals(attribs)) {
                return true;
            }
        }
        return false;
    }

    public boolean highlightEdge(Set startAttribs, Set endAttribs) {
        return isQueryAttributes(startAttribs) || isQueryAttributes(endAttribs);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final NeigboursHighlightStrategy strategy = (NeigboursHighlightStrategy) o;

        if (node != null ? !node.equals(strategy.node) : strategy.node != null) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        return (node != null ? node.hashCode() : 0);
    }

    public NeigboursHighlightStrategy() {
    }
}

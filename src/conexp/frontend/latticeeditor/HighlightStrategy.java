/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/

package conexp.frontend.latticeeditor;

import canvas.Figure;
import canvas.IHighlightStrategy;
import conexp.core.Set;
import conexp.frontend.latticeeditor.figures.AbstractConceptCorrespondingFigure;
import conexp.frontend.latticeeditor.figures.ConceptRelatedTextFigure;
import conexp.frontend.latticeeditor.figures.EdgeFigure;
import conexp.frontend.latticeeditor.figures.NodeObjectConnectionFigure;
import conexp.util.GenericStrategy;


public abstract class HighlightStrategy implements GenericStrategy, IHighlightStrategy {
    protected conexp.core.LatticeElement node;
    protected Set query;

    public boolean highlightFigure(Figure figure) {
        if (figure instanceof AbstractConceptCorrespondingFigure) {
            AbstractConceptCorrespondingFigure conceptFigure = (AbstractConceptCorrespondingFigure) figure;
            return highlightNodeWithQuery(conceptFigure.getIntentQuery());

        }
        if (figure instanceof ConceptRelatedTextFigure) {
            ConceptRelatedTextFigure textFigure = (ConceptRelatedTextFigure) figure;
            return highlightNodeWithQuery(textFigure.getIntentQuery());
        }
        if (figure instanceof NodeObjectConnectionFigure) {
            NodeObjectConnectionFigure connectionFigure = (NodeObjectConnectionFigure) figure;
            return highlightNodeWithQuery(connectionFigure.getIntentQuery());
        }
        if (figure instanceof EdgeFigure) {
            EdgeFigure edgeFigure = (EdgeFigure) figure;
            return highlightEdge(edgeFigure.getStartIntentQuery(), edgeFigure.getEndIntentQuery());
        }
        return false;
    }

    public boolean highlightNodeWithQuery(final Set attribs) {
        return isActive() && highlightQuery(attribs);
    }

    protected abstract boolean highlightQuery(Set attribs);

    public boolean highlightEdge(Set startAttribs, Set endAttribs) {
        return isActive() && doHighlightEdge(startAttribs, endAttribs);
    }

    protected abstract boolean doHighlightEdge(Set startAttribs, Set endAttribs);

    public void setNode(conexp.core.LatticeElement el) {
        node = el;
        if (null != el) {
            query = el.getAttribs();
        } else {
            query = null;
        }
    }

    public void setQuery(Set query) {
        this.query = query;
    }

    public boolean isActive() {
        return query != null;
    }

    /**
     * AbstractHilightStrategy constructor comment.
     */
    public HighlightStrategy() {
        super();
    }
}

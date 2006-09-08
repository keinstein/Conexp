/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor;

import canvas.Figure;
import canvas.IHighlightStrategy;
import conexp.core.LatticeElement;
import conexp.core.Set;
import conexp.frontend.latticeeditor.figures.AbstractConceptCorrespondingFigure;
import conexp.frontend.latticeeditor.figures.EdgeFigure;
import conexp.frontend.latticeeditor.figures.IConceptRelatedTextFigure;
import conexp.frontend.latticeeditor.figures.NodeObjectConnectionFigure;
import conexp.util.GenericStrategy;


public abstract class HighlightStrategy implements GenericStrategy, IHighlightStrategy {
    protected LatticeElement node;
    protected Set query;

    public boolean highlightFigure(Figure figure) {
        if (figure instanceof AbstractConceptCorrespondingFigure) {
            AbstractConceptCorrespondingFigure conceptFigure = (AbstractConceptCorrespondingFigure) figure;
            return highlightNodeWithQuery(conceptFigure.getIntentQuery());

        }
        if (figure instanceof IConceptRelatedTextFigure) {
            IConceptRelatedTextFigure textFigure = (IConceptRelatedTextFigure) figure;
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

    public void setNode(LatticeElement el) {
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

    protected HighlightStrategy() {
        super();
    }

    public IHighlightStrategy makeCopy() {
        HighlightStrategy ret = createNew();
        ret.setNode(node);
        return ret;
    }

    protected abstract HighlightStrategy createNew();

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof HighlightStrategy)) {
            return false;
        }

        final HighlightStrategy highlightStrategy = (HighlightStrategy) obj;
        if (!getClass().equals(highlightStrategy.getClass())) {
            return false;
        }

        if (node != null ? !node.equals(highlightStrategy.node) : highlightStrategy.node != null) {
            return false;
        }
        if (query != null ? !query.equals(highlightStrategy.query) : highlightStrategy.query != null) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        int result;
        result = node != null ? node.hashCode() : 0;
        result = 29 * result + (query != null ? query.hashCode() : 0);
        return result;
    }


    public String toString() {
        return getClass().getName();
    }
}

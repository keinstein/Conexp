/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor;

import canvas.Figure;
import canvas.IHighlightStrategy;
import conexp.core.Set;
import conexp.frontend.latticeeditor.figures.AbstractConceptCorrespondingFigure;
import conexp.frontend.latticeeditor.figures.EdgeFigure;
import conexp.frontend.latticeeditor.figures.IConceptRelatedTextFigure;
import conexp.frontend.latticeeditor.figures.NodeObjectConnectionFigure;

import java.util.Iterator;
import java.util.Collections;


public class Highlighter implements IHighlightStrategy{
    private ConceptHighlightStrategy conceptHighlightStrategy;

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

    public boolean highlightQuery(Set attribs){
        return conceptHighlightStrategy.highlightQuery(attribs);
    }

    public final boolean highlightEdge(Set startAttribs, Set endAttribs) {
        return isActive() && doHighlightEdge(startAttribs, endAttribs);
    }

    public boolean doHighlightEdge(Set startAttribs, Set endAttribs){
        return conceptHighlightStrategy.highlightEdge(startAttribs, endAttribs);
    }


    java.util.Set/*<AbstractConceptCorrespondingFigure>*/ selection = Collections.EMPTY_SET;

    /**
     * Current mode of usage of this method is to call in on each paint action.
     *
     * @param aSelection
     */
    public void setSelectedConcepts(java.util.Set/*<AbstractConceptCorrespondingFigure>*/ aSelection) {
        //todo:sye - fix selection building logic
        this.selection = aSelection;
        updateSelection(aSelection);
    }

    private void updateSelection(java.util.Set aSelection) {
        if(selection.size()>1){
            //todo: implement and/or building logic here
//            throw new IllegalArgumentException("Not yet implemented");
        }
        if(selection.size()==1){
            for (Iterator iterator = selection.iterator(); iterator.hasNext();) {
                AbstractConceptCorrespondingFigure figure = (AbstractConceptCorrespondingFigure) iterator.next();
                conceptHighlightStrategy.initFromFigure(figure);
            }
        }else{
            assert aSelection.size()==0;
            conceptHighlightStrategy.initFromFigure(null);
        }
    }

    public boolean isActive() {
        return !selection.isEmpty();
    }

    public Highlighter() {
        super();
    }

    public void setConceptHighlightStrategy(ConceptHighlightStrategy conceptHighlightStrategy) {
        this.conceptHighlightStrategy = conceptHighlightStrategy;
        updateSelection(selection);
    }

    public IHighlightStrategy makeCopy() {
        Highlighter ret = new Highlighter();
        ret.setConceptHighlightStrategy(conceptHighlightStrategy.createNew());
        return ret;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Highlighter)) {
            return false;
        }

        final Highlighter highlighter = (Highlighter) obj;
        if (!getClass().equals(highlighter.getClass())) {
            return false;
        }

        if (conceptHighlightStrategy != null ? !conceptHighlightStrategy.equals(highlighter.conceptHighlightStrategy) : highlighter.conceptHighlightStrategy != null) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int result;
        result = conceptHighlightStrategy != null ? conceptHighlightStrategy.hashCode() : 0;
        return result;
    }


    public String toString() {
        return getClass().getName();
    }

    public boolean hasConceptHighlighStrategy() {
        return conceptHighlightStrategy!=null;
    }
}

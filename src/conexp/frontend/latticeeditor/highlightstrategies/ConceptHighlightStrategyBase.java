package conexp.frontend.latticeeditor.highlightstrategies;

import conexp.frontend.latticeeditor.Highlighter;
import conexp.frontend.latticeeditor.ConceptHighlightStrategy;
import conexp.frontend.latticeeditor.ConceptHighlightAtomicStrategy;
import conexp.frontend.latticeeditor.figures.AbstractConceptCorrespondingFigure;
import conexp.core.LatticeElement;
import conexp.core.Set;

public abstract class ConceptHighlightStrategyBase implements ConceptHighlightAtomicStrategy {
    protected Set query=null;

    public void initFromFigure(AbstractConceptCorrespondingFigure figure) {
        if(figure!=null){
            query = figure.getIntentQuery();
        }else{
            query = null;
        }
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final ConceptHighlightStrategyBase that = (ConceptHighlightStrategyBase) o;

        if (query != null ? !query.equals(that.query) : that.query != null) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        return (query != null ? query.hashCode() : 0);
    }
}

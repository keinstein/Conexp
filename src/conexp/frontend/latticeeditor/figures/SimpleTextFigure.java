package conexp.frontend.latticeeditor.figures;

import conexp.frontend.latticeeditor.ConceptQuery;

public class SimpleTextFigure extends ConceptRelatedTextFigure {
    private java.lang.String str;

    public SimpleTextFigure(ConceptQuery conceptQuery, String str) {
        super(conceptQuery);
        this.str = str;
    }

    protected String getString() {
        return str;
    }
}
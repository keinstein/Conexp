/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.latticeeditor.figures;

import conexp.frontend.latticeeditor.ConceptQuery;

import java.awt.Color;

public class ContextObjectTextFigure extends ConceptRelatedTextFigure {
    protected conexp.core.ContextEntity obj;

    public ContextObjectTextFigure(ConceptQuery conceptQuery, conexp.core.ContextEntity obj) {
        super(conceptQuery);
        this.obj = obj;
    }

    protected Color getBackground() {
        return obj.isObject() ? Color.white : Color.lightGray;
    }

    protected String getString() {
        return obj.getName();
    }


}

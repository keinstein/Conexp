/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.latticeeditor.figures;

import canvas.CanvasScheme;
import conexp.frontend.latticeeditor.ConceptQuery;

import java.awt.*;

public class ContextObjectTextFigure extends ConceptRelatedTextFigure {
    protected conexp.core.ContextEntity obj;

    public ContextObjectTextFigure(ConceptQuery conceptQuery, conexp.core.ContextEntity obj) {
        super(conceptQuery);
        this.obj = obj;
    }

    protected Color getBackground(CanvasScheme opt) {
        return obj.isObject() ? Color.white : Color.lightGray;
    }

    protected String getString() {
        return obj.getName();
    }


}

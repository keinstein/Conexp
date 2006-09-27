/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor.figures;

import canvas.CanvasScheme;
import conexp.core.ContextEntity;
import conexp.frontend.latticeeditor.ConceptQuery;

import java.awt.Color;

public class ContextEntityTextFigure extends ConceptRelatedTextFigure {
    protected ContextEntity obj;

    public ContextEntityTextFigure(ConceptQuery conceptQuery, ContextEntity obj) {
        super(conceptQuery);
        this.obj = obj;
    }

    protected Color getBackground(CanvasScheme opt) {
        return ColorUtil.getLabelBackgroungColor(obj.isObject());
    }

    protected String getString() {
        return obj.getName();
    }


}

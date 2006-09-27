/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor.figures;

import conexp.frontend.latticeeditor.ConceptQuery;

import java.awt.Color;

import canvas.CanvasScheme;

public class SimpleTextFigure extends ConceptRelatedTextFigure {
    private String str;
    boolean object;

    public SimpleTextFigure(ConceptQuery conceptQuery, String text) {
        this(conceptQuery, text, true);
    }

    public SimpleTextFigure(ConceptQuery conceptQuery, String text, boolean objectFlag) {
        super(conceptQuery);
        this.str = text;
        this.object = objectFlag;
    }

    protected Color getBackground(CanvasScheme opt) {
        return ColorUtil.getLabelBackgroungColor(object);
    }

    protected String getString() {
        return str;
    }
}

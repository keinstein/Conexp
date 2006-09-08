/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.contexteditor;

import conexp.core.ContextEditingInterface;

import javax.swing.Icon;


public class SimpleContextDrawStrategy implements ContextRenderStrategy {
    public Icon getRelationIcon(ContextEditingInterface cxt, int row, int col) {
        if (cxt.getRelationAt(row - 1, col - 1)) {
            return CrossIcon.getCross();
        }
        return null;
    }
}

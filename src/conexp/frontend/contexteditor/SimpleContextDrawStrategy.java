/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.contexteditor;

import conexp.core.ContextEditingInterface;


public class SimpleContextDrawStrategy implements ContextRenderStrategy {
    public javax.swing.Icon getRelationIcon(ContextEditingInterface cxt, int row, int col) {
        if (cxt.getRelationAt(row - 1, col - 1)) {
            return CrossIcon.getCross();
        }
        return null;
    }
}

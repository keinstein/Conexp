/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/

package conexp.frontend.contexteditor;

import conexp.core.ContextEditingInterface;
import conexp.core.ContextEditingInterfaceWithArrowRelations;

import javax.swing.*;

public class ArrowRelDrawStrategy extends SimpleContextDrawStrategy {
    final static Class THIS = ArrowRelDrawStrategy.class;
    final static Icon doubleArrow = new ImageIcon(THIS.getResource("doubleArrow.gif"));
    final static Icon upArrow = new ImageIcon(THIS.getResource("upArrow.gif"));
    final static Icon downArrow = new ImageIcon(THIS.getResource("downArrow.gif"));

    public javax.swing.Icon getRelationIcon(ContextEditingInterface cxt, int row, int col) {
        util.Assert.isTrue(row > 0);
        util.Assert.isTrue(col > 0);
        if (!(cxt instanceof ContextEditingInterfaceWithArrowRelations)) {
            return super.getRelationIcon(cxt, row, col);
        }

        ContextEditingInterfaceWithArrowRelations arrowCxt = (ContextEditingInterfaceWithArrowRelations) cxt;

        Icon icon = null;
        if (cxt.getRelationAt(row - 1, col - 1)) {
            icon = CrossIcon.getCross();
        } else {
            boolean isDownArrow = arrowCxt.hasDownArrow(row - 1, col - 1);
            boolean isUpArrow = arrowCxt.hasUpArrow(row - 1, col - 1);
            if (isDownArrow) {
                icon = isUpArrow ? doubleArrow : downArrow;
            } else if (isUpArrow) {
                icon = upArrow;
            }
        }
        return icon;
    }
}

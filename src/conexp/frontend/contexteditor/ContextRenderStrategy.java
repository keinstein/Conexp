/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.contexteditor;

import conexp.core.ContextEditingInterface;
import conexp.util.GenericStrategy;

import javax.swing.Icon;


public interface ContextRenderStrategy extends GenericStrategy {
    /**
     * Insert the method's description here.
     * Creation date: (22.04.01 21:13:19)
     *
     * @param cxt conexp.core.Context
     * @param row int
     * @param col int
     * @return javax.swing.Icon
     */
    Icon getRelationIcon(ContextEditingInterface cxt, int row, int col);
}

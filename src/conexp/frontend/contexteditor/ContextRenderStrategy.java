package conexp.frontend.contexteditor;

import conexp.core.ContextEditingInterface;
import conexp.util.GenericStrategy;

import javax.swing.*;


/**
 * Insert the type's description here.
 * Creation date: (22.04.01 21:08:13)
 * @author Serhiy Yevtushenko
 */
public interface ContextRenderStrategy extends GenericStrategy {
    /**
     * Insert the method's description here.
     * Creation date: (22.04.01 21:13:19)
     * @return javax.swing.Icon
     * @param cxt conexp.core.Context
     * @param row int
     * @param col int
     */
    Icon getRelationIcon(ContextEditingInterface cxt, int row, int col);
}
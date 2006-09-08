/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package canvas;

import javax.swing.JPopupMenu;



public interface IFigurePopupMenuProvider {
    void fillPopupMenu(JPopupMenu menu, Figure figure);
}

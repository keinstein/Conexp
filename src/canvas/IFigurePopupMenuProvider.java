package canvas;

import javax.swing.JPopupMenu;

/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 * Date: 24/10/2003
 * Time: 14:26:01
 */

public interface IFigurePopupMenuProvider {
    void fillPopupMenu(JPopupMenu menu, Figure figure);
}

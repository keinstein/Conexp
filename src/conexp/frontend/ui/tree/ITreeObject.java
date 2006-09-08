/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.frontend.ui.tree;

import javax.swing.JPopupMenu;


public interface ITreeObject {
    void fillPopupMenu(JPopupMenu popupMenu);

    void navigate();

    String toString();
}

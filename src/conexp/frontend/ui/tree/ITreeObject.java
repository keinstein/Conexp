package conexp.frontend.ui.tree;

import javax.swing.JPopupMenu;

/**
 * User: sergey
 * Date: 16/5/2005
 * Time: 15:09:32
 */
public interface ITreeObject {
    void fillPopupMenu(JPopupMenu popupMenu);
    void navigate();
    String toString();
}

package conexp.util.gui;

import javax.swing.undo.UndoableEdit;

/**
 * Author: Serhiy Yevtushenko
 * Date: Dec 28, 2002
 * Time: 1:03:46 AM
 */
public interface Command extends UndoableEdit{
   void performCommand();
}

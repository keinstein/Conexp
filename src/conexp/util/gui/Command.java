/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.util.gui;

import javax.swing.undo.UndoableEdit;


public interface Command extends UndoableEdit {
    void performCommand();
}

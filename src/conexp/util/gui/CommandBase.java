/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.util.gui;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;


public abstract class CommandBase extends AbstractUndoableEdit implements Command {
    private String name;

    public CommandBase(String name) {
        this.name = name;
    }

    public String getPresentationName() {
        return name;
    }

    public void performCommand() {
        doCommand();
    }

    public abstract void doCommand();

    public abstract void undoCommand();


    public void redo() throws CannotRedoException {
        super.redo();
        doCommand();
    }

    public void undo() throws CannotUndoException {
        super.undo();
        undoCommand();
    }
}

package conexp.util.gui;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

/**
 * Author: Serhiy Yevtushenko
 * Date: Dec 28, 2002
 * Time: 12:47:08 AM
 */
public abstract class CommandBase extends AbstractUndoableEdit implements Command{
    String name;

    public CommandBase(String name) {
        this.name = name;
    }

    public String getPresentationName() {
        return name;
    }

    public void performCommand(){
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

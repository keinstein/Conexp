/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.contexteditor;

import conexp.core.AttributeExplorer;
import conexp.core.ContextEditingInterface;
import conexp.core.ExtendedContextEditingInterface;
import conexp.core.Set;
import conexp.util.gui.Command;

import javax.swing.JScrollPane;
import javax.swing.event.UndoableEditListener;

public class ContextTablePane extends JScrollPane {

    public ContextTablePane(ExtendedContextEditingInterface cxt) {
        createComponent(cxt);
    }

    //----------------------------------------------
    protected ContextTable contextTableView;

    protected void createComponent(ExtendedContextEditingInterface cxt) {
        contextTableView = new ContextTable(cxt);
        contextTableView.setFullContextMenuProvider();

        add(contextTableView);
        setViewportView(contextTableView);
    }

    public ContextTable getContextTableView() {
        return contextTableView;
    }

    public void setContext(ContextEditingInterface source) {
        contextTableView.setContext(source);
    }

    public void addUndoableEditListener(UndoableEditListener listener) {
        contextTableView.addUndoableEditListener(listener);
    }

    public void removeUndoableEditListener(UndoableEditListener listener) {
        contextTableView.removeUndoableEditListener(listener);
    }

    protected void performCommand(Command command) {
        contextTableView.performCommand(command);
    }

    public AttributeExplorer.ContextModificationCallback getContextModificationCallback() {
        return new AttributeExplorer.ContextModificationCallback() {
            public void addObjectToContext(String name, Set intent) {
                performCommand(getContextTableModel().new AddObjectWithNameCommand(name, intent));
            }
        };
    }

    protected ContextTableModel getContextTableModel() {
        return getContextTableView().getContextTableModel();
    }

}

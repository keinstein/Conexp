/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.contexteditor;


import com.visibleworkings.trace.Trace;
import conexp.core.Context;
import conexp.frontend.ResourceLoader;
import conexp.frontend.ViewChangeInterfaceWithConfig;
import conexp.frontend.util.ActionChainUtil;
import conexp.frontend.util.IResourceManager;
import conexp.frontend.util.ResourceManager;
import conexp.util.gui.paramseditor.ParamEditorTable;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.ResourceBundle;


public class ContextViewPanel extends ContextTablePane implements ViewChangeInterfaceWithConfig {
    //ActionChainBearer
    private ActionMap actionChain = new ActionMap();

    //----------------------------------------------
    public ActionMap getActionChain() {
        return actionChain;
    }


    UndoManager undoManager = new UndoManager();

    class UndoAction extends AbstractAction {
        public UndoAction() {
            super("undo");
            setEnabled(false);
        }

        public void actionPerformed(ActionEvent e) {

            try {
                undoManager.undo();
            } catch (CannotUndoException ex) {
                Trace.trace.errorm("Unable to undo", ex);
            }
            update();
            redoAction.update();
        }

        protected void update() {
            if (undoManager.canUndo()) {
                setEnabled(true);
                putValue(Action.NAME, undoManager.getUndoPresentationName());
            } else {
                setEnabled(false);
                putValue(Action.NAME, "Undo");
            }
        }
    }

    UndoAction undoAction = new UndoAction();
    RedoAction redoAction = new RedoAction();

    class RedoAction extends AbstractAction {
        public RedoAction() {
            super("redo");
            setEnabled(false);
        }

        public void actionPerformed(ActionEvent e) {
            try {
                undoManager.redo();
            } catch (CannotRedoException ex) {
                Trace.trace.errorm("Unable to redo: ", ex);
            }
            update();
            undoAction.update();
        }

        protected void update() {
            if (undoManager.canRedo()) {
                setEnabled(true);
                putValue(Action.NAME, undoManager.getRedoPresentationName());
            } else {
                setEnabled(false);
                putValue(Action.NAME, "Redo");
            }
        }
    }


    UndoableEditListener undoableEditListener = new UndoableEditListener() {
        public void undoableEditHappened(UndoableEditEvent e) {
            undoManager.addEdit(e.getEdit());
            undoAction.update();
            redoAction.update();
        }
    };

    private Action[] actions = {
            new AddRowAction(), new ClarifyObjectsAction(),
            new AddAttribAction(), new ClarifyAttribsAction(),
            new ReduceObjectsAction(), new ReduceAttribsAction(),
            new ReduceAllAction(),
            new TrasposeContextAction(),
            undoAction, redoAction
    };

    public Action[] getActions() {
        return actions;
    }

    class AddRowAction extends AbstractAction {
        AddRowAction() {
            super("addObj");//$NON-NLS-1$
        }

        public void actionPerformed(ActionEvent e) {
            performCommand(getContextTableModel().new AddRowCommand());
        }
    }

    class TrasposeContextAction extends AbstractAction {
        TrasposeContextAction() {
            super("transpose");//$NON-NLS-1$
        }

        public void actionPerformed(ActionEvent e) {
            performCommand(getContextTableModel().new TransposeContextCommand());
        }

    }

    class ClarifyObjectsAction extends AbstractAction {

        ClarifyObjectsAction() {
            super("clarifyObj");//$NON-NLS-1$
        }

        public void actionPerformed(ActionEvent e) {
            performCommand(getContextTableModel().new ClarifyObjectsCommand());
        }
    }

    class AddAttribAction extends AbstractAction {

        AddAttribAction() {
            super("addAttr");//$NON-NLS-1$
        }

        public void actionPerformed(ActionEvent e) {
            performCommand(getContextTableModel().new AddAttribCommand());
        }
    }

    class ClarifyAttribsAction extends AbstractAction {

        ClarifyAttribsAction() {
            super("clarifyAttributes");//$NON-NLS-1$
        }

        public void actionPerformed(ActionEvent e) {
            performCommand(getContextTableModel().new ClarifyAttributesCommand());
        }
    }

    class ReduceObjectsAction extends AbstractAction {

        ReduceObjectsAction() {
            super("reduceObj");//$NON-NLS-1$
        }

        public void actionPerformed(ActionEvent e) {
            performCommand(getContextTableModel().new ReduceObjectsCommand());
        }
    }


    class ReduceAttribsAction extends AbstractAction {

        ReduceAttribsAction() {

            super("reduceAttr");//$NON-NLS-1$
        }

        public void actionPerformed(ActionEvent e) {
            performCommand(getContextTableModel().new ReduceAttributesCommand());
        }
    }

    class ReduceAllAction extends AbstractAction {

        ReduceAllAction() {
            super("reduceAll");//$NON-NLS-1$
        }

        public void actionPerformed(ActionEvent e) {
            performCommand(getContextTableModel().new ReduceContextCommand());
        }
    }
    //----------------------------------------------

    Context cxt;

    public ContextViewPanel(Context cxt) {
        super(cxt);
        this.cxt = cxt;
        ActionChainUtil.putActions(getActionChain(), getActions());

        addUndoableEditListener(undoableEditListener);
    }

    public void setParentActionMap(ActionMap parentActionChain) {
        getActionChain().setParent(parentActionChain);
    }

    //----------------------------------------------
    //Config Provider

    private static ResourceBundle resContextViewPanel;

    static {
        resContextViewPanel = ResourceLoader.getResourceBundle("conexp/frontend/resources/ContextViewPanel");  //$NON-NLS-1$
    }

    public static ResourceBundle getResources() {
        return ContextViewPanel.resContextViewPanel;
    }

    public IResourceManager getResourceManager() {
        return new ResourceManager(getResources());
    }

    //----------------------------------------------

    //ViewChangeInterface
    //----------------------------------------------

    private JComponent options;

    public JComponent getViewOptions() {
        if (null == options) {
            JScrollPane paneDrawing = new JScrollPane();
            paneDrawing.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
            ParamEditorTable table = new ParamEditorTable();
            table.getParamsModel().addParams(contextTableView.getParams());
            paneDrawing.add(table);
            paneDrawing.setViewportView(table);
            options = paneDrawing;
        }
        return options;
    }

    //View
    //----------------------------------------------
    public void initialUpdate() {

    }

    //----------------------------------------------
    public Component getViewComponent() {
        return this;
    }
}

package conexp.frontend.contexteditor;

/**
 *  ContextViewPanel.java Created: Sun Apr 30 18:57:12 2000
 *
 *@author     Sergey Yevtushenko
 *@version
 */

import conexp.core.Context;
import conexp.frontend.ResourceLoader;
import conexp.frontend.ViewChangeInterfaceWithConfig;
import conexp.frontend.util.ActionChainUtil;
import conexp.frontend.util.IResourceManager;
import conexp.frontend.util.ResourceManager;
import conexp.util.gui.paramseditor.ParamEditorTable;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.ResourceBundle;

/**
 *  Description of the Class
 *
 *@author     Sergey
 *created    8 Èþíü 2000 ã.
 */
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
                System.out.println("Unable to undo: " + ex);
                ex.printStackTrace();
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
                System.out.println("Unable to redo: " + ex);
                ex.printStackTrace();
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


    UndoableEditListener undoableEditListener = new UndoableEditListener(){
        public void undoableEditHappened(UndoableEditEvent e) {
            undoManager.addEdit(e.getEdit());
            undoAction.update();
            redoAction.update();
        }
    };

    private Action[] actions = {
        new AddRowAction(), new PurifyObjectsAction(),
        new AddAttribAction(), new PurifyAttribsAction(),
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

    class PurifyObjectsAction extends AbstractAction {

        PurifyObjectsAction() {
            super("purifyObj");//$NON-NLS-1$
        }

        public void actionPerformed(ActionEvent e) {
            performCommand(getContextTableModel().new PurifyObjectsCommand());
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

    class PurifyAttribsAction extends AbstractAction {

        PurifyAttribsAction() {
            super("purifyAttr");//$NON-NLS-1$
        }

        public void actionPerformed(ActionEvent e) {
            performCommand(getContextTableModel().new PurifyAttributesCommand());
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

    Context getContext() {
        return cxt;
    }

    //----------------------------------------------

    //----------------------------------------------
    //Config Provider

    private static java.util.ResourceBundle resContextViewPanel;

    static {
        resContextViewPanel = ResourceLoader.getResourceBundle("conexp/frontend/resources/ContextViewPanel");  //$NON-NLS-1$
    }

    public ResourceBundle getResources() {
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
/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/

package conexp.frontend.contexteditor;

import conexp.core.ContextEditingInterface;
import conexp.util.gui.Command;
import conexp.util.gui.paramseditor.ParamInfo;
import conexp.util.gui.paramseditor.ParamsProvider;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.UndoableEditListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class ContextTable extends JTable implements ParamsProvider {
    ContextTooltipTableCellRenderer cellRenderer;
    ParamInfo[] params;

    boolean repaintMode = false;

    public void setRepaintMode(boolean repaintMode) {
        this.repaintMode = repaintMode;
    }

    public ContextTable(ContextEditingInterface cxt) {
        super(new ContextTableModel(cxt));
        cellRenderer = new ContextTooltipTableCellRenderer();
        cellRenderer.addRenderingChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                if (evt.getNewValue() instanceof ArrowRelDrawStrategy) {
                    setRepaintMode(true);
                } else {
                    setRepaintMode(false);
                }
                invalidate();
                repaint();
            }
        });

        setDefaultRenderer(Boolean.class, cellRenderer);
        setDefaultRenderer(String.class, cellRenderer);

        ContextCellEditor cellEditor = new ContextCellEditor();

        setDefaultEditor(Boolean.class, cellEditor);
        setDefaultEditor(String.class, cellEditor);

        cellEditor.addCellEditorListener(new CellEditorListener() {
            public void editingStopped(ChangeEvent e) {
                if (repaintMode) {
                    repaint();
                }
            }

            public void editingCanceled(ChangeEvent e) {
            }
        });

        addMouseListener(new PopupListener());

        setCellSelectionEnabled(true);
        setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        getTableHeader().setReorderingAllowed(false);
    }


    PopupMenuProvider popupMenuProvider = new DefaultPopupMenuProvider();

    protected JPopupMenu makePopupMenu() {
        return popupMenuProvider.makePopupMenu();

    }

    public void setPopupMenuProvider(PopupMenuProvider newPopupMenuProvider) {
        this.popupMenuProvider = newPopupMenuProvider;
    }

    /**
     *  @deprecated
     *  will be changed, when implement mechanism for declaring action and
     *  loading actions from XML resources
     * */
    public void setFullContextMenuProvider() {
        setPopupMenuProvider(new ContextTablePopupMenuProvider());
    }

    class ContextTablePopupMenuProvider implements PopupMenuProvider {
        public JPopupMenu makePopupMenu() {
            JPopupMenu popupMenu = new JPopupMenu();
            popupMenu.add(new RemoveAttributeAction());
            popupMenu.add(new RemoveObjectAction());
            popupMenu.add(new FillCellAction());
            popupMenu.add(new ClearCellAction());
            popupMenu.add(new InverseCellAction());
            return popupMenu;
        }
    }

    class PopupListener extends MouseAdapter {
        public void mouseReleased(MouseEvent e) {
            maybeShowPopup(e);
        }

        private void maybeShowPopup(MouseEvent e) {
            if (e.isPopupTrigger()) {
                JPopupMenu popupMenu = makePopupMenu();
                if (null != popupMenu && (popupMenu.getComponentCount() != 0)) {
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        }

        public void mousePressed(MouseEvent e) {
            maybeShowPopup(e);
        }
    }

    public abstract static class ActionWithKey extends AbstractAction {
        public ActionWithKey(String key, String name) {
            super(name);
            putValue(AbstractAction.ACTION_COMMAND_KEY, key);
        }
    }

    class RemoveAttributeAction extends ActionWithKey {
        public RemoveAttributeAction() {
            super("removeAttributes", "Remove attribute(s)");
        }

        public boolean isEnabled() {
            return getContextTableModel().canProcessColumns(getSelectedColumns());
        }

        public void actionPerformed(ActionEvent e) {
            getContextTableModel().removeColumns(getSelectedColumns());
        }
    }

    class RemoveObjectAction extends ActionWithKey {
        public RemoveObjectAction() {
            super("removeObjects", "Remove object(s)");
        }

        public boolean isEnabled() {
            return getContextTableModel().canDeleteRows(getSelectedRows());
        }

        public void actionPerformed(ActionEvent e) {
            getContextTableModel().removeRows(getSelectedRows());
        }
    }

    abstract class ActionOnSelectedContextCells extends ActionWithKey {
        public ActionOnSelectedContextCells(String key, String name) {
            super(key, name);
        }

        public boolean isEnabled() {
            return getContextTableModel().hasAtLeastOneNonHeaderCell(getSelectedRows(), getSelectedColumns());
        }

        public void actionPerformed(ActionEvent e) {
            getContextTableModel().applyCellTransformerToNonHeaderCells(getSelectedRows(), getSelectedColumns(),
                    getTransformer());
        }

        protected abstract CellTransformer getTransformer();
    }


    class FillCellAction extends ActionOnSelectedContextCells {
        FillCellAction() {
            super("fillSelectedCells", "Fill selected cells");
        }

        protected CellTransformer getTransformer() {
            return new FillByValueCellTransformer(Boolean.TRUE);
        }

    }

    class ClearCellAction extends ActionOnSelectedContextCells {
        ClearCellAction() {
            super("clearSelectedCells", "Clear selected keys");
        }

        protected CellTransformer getTransformer() {
            return new FillByValueCellTransformer(Boolean.FALSE);
        }
    }


    class InverseCellAction extends ActionOnSelectedContextCells {
        InverseCellAction() {
            super("inverseSelectedCells", "Inverse selected cells");
        }

        protected CellTransformer getTransformer() {
            return new InverseValueCellTransformer();
        }
    }

    protected ContextTableModel getContextTableModel() {
        return ((ContextTableModel) getModel());
    }

    public void setContext(ContextEditingInterface cxt) {
        getContextTableModel().setContext(cxt);
    }

    public void addUndoableEditListener(UndoableEditListener listener) {
        getContextTableModel().addUndoableEditListener(listener);
    }

    public void removeUndoableEditListener(UndoableEditListener listener) {
        getContextTableModel().removeUndoableEditListener(listener);
    }

    public void performCommand(Command command) {
        getContextTableModel().performCommand(command);
    }

    public ParamInfo[] getParams() {
        if (null == params) {
            ParamInfo[] rendererParams = cellRenderer.getParams();
            ParamInfo[] modelParams = getContextTableModel().getParams();
            params = new ParamInfo[rendererParams.length
                    + modelParams.length];
            System.arraycopy(rendererParams, 0, params, 0, rendererParams.length);
            System.arraycopy(modelParams, 0, params, rendererParams.length, modelParams.length);
        }
        return params;
    }
}

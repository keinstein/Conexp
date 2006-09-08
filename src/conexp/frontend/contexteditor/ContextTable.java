/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.contexteditor;

import conexp.core.ContextEditingInterface;
import conexp.util.gui.Command;
import conexp.util.gui.paramseditor.BooleanParamInfo;
import conexp.util.gui.paramseditor.ParamInfo;
import conexp.util.gui.paramseditor.ParamsProvider;
import conexp.util.valuemodels.BooleanValueModel;
import util.DataFormatException;
import util.gui.ActionWithKey;

import javax.swing.JComponent;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.table.TableColumn;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


public class ContextTable extends JTable implements ParamsProvider {
    ContextTooltipTableCellRenderer cellRenderer;
    ParamInfo[] params;

    boolean repaintMode = false;
    private BooleanValueModel compressView;
    public static final String COMPRESSED_VIEW_PROPERTY = "compressedView";
    public static final int COMPRESSED_WIDTH = 16;
    public static final int USUAL_WIDTH = 75;

    public void setRepaintMode(boolean repaintMode) {
        this.repaintMode = repaintMode;
    }

    public ContextTable(ContextEditingInterface cxt) {
        super(new ContextTableModel(cxt));

        cellRenderer = new ContextTooltipTableCellRenderer();
        cellRenderer.addRenderingChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                setRepaintMode(evt.getNewValue() instanceof ArrowRelDrawStrategy);
                invalidate();
                repaint();
            }
        });

        setDefaultRenderer(Boolean.class, cellRenderer);
        setDefaultRenderer(String.class, cellRenderer);


        final ContextCellEditor contextCellEditor = new ContextCellEditor();

        setDefaultEditor(Boolean.class, contextCellEditor);
        setDefaultEditor(String.class, contextCellEditor);

        contextCellEditor.addCellEditorListener(new CellEditorListener() {
            public void editingStopped(ChangeEvent e) {
                if (repaintMode) {
                    repaint();
                }
            }

            public void editingCanceled(ChangeEvent e) {
            }
        });

        compressView = new BooleanValueModel(COMPRESSED_VIEW_PROPERTY, false);
        compressView.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                if (COMPRESSED_VIEW_PROPERTY.equals(evt.getPropertyName())) {
                    setCompressedView(Boolean.TRUE.equals(evt.getNewValue()));
                }
            }
        });

/*
        getContextTableModel().addTableModelListener(new SyncListener() {
            void doSync() {
                System.out.println("ContextTable.doSync");
               setCompressedView(compressView.getValue());
            }

            protected boolean reactOnEvent(TableModelEvent evt) {
                System.out.println("ContextTable.reactOnEvent");
                return super.reactOnEvent(evt);
            }
        });
*/

        //this line fix an issue with switching focus from window, containing table, when editing is in process
        putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);

        ContextTable.initKeyboard(this);
        addMouseListener(new PopupListener());

        setCellSelectionEnabled(true);
        setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        getTableHeader().setReorderingAllowed(false);

    }

    public void tableChanged(TableModelEvent e) {
        super.tableChanged(e);
        afterTableChanged(e);
    }

    protected void afterTableChanged(TableModelEvent e) {
        //is called here because the table column model is rebuilt after the change
        if (compressView != null) {
            setCompressedView(compressView.getValue());
        }
    }

    private void setCompressedView(boolean compressed) {
        doSetCompresedView(compressed);
    }

    private void doSetCompresedView(boolean compressed) {
        final int preferredWidth = compressed ? COMPRESSED_WIDTH : USUAL_WIDTH;
        final int columnCount = getColumnCount();
        for (int i = 1; i < columnCount; i++) {
            TableColumn column = columnModel.getColumn(i);
            column.setPreferredWidth(preferredWidth);
        }
    }


    public BooleanValueModel getCompressView() {
        return compressView;
    }

    PopupMenuProvider popupMenuProvider = new DefaultPopupMenuProvider();

    public PopupMenuProvider getPopupMenuProvider() {
        return popupMenuProvider;
    }

    protected JPopupMenu makePopupMenu() {
        JPopupMenu popupMenu = new JPopupMenu();
        popupMenuProvider.fillPopupMenu(popupMenu);
        return popupMenu;
    }

    public void setPopupMenuProvider(PopupMenuProvider newPopupMenuProvider) {
        this.popupMenuProvider = newPopupMenuProvider;
    }

    /**
     * @deprecated will be changed, when implement mechanism for declaring action and
     *             loading actions from XML resources
     */
    public void setFullContextMenuProvider() {
        setPopupMenuProvider(new ContextTablePopupMenuProvider());
    }

    class ContextTablePopupMenuProvider extends DefaultPopupMenuProvider {
        public void fillPopupMenu(JPopupMenu popupMenu) {
            popupMenu.add(new CutAction());
            popupMenu.add(new CopyAction());
            popupMenu.add(new PasteAction());
            popupMenu.addSeparator();
            popupMenu.add(new RemoveAttributeAction());
            popupMenu.add(new RemoveObjectAction());
            popupMenu.addSeparator();
            popupMenu.add(new FillCellAction());
            popupMenu.add(new ClearCellAction());
            popupMenu.add(new InverseCellAction());
        }
    }

    class PopupListener extends MouseAdapter {
        public void mouseReleased(MouseEvent e) {
            maybeShowPopup(e);
        }

        private void maybeShowPopup(MouseEvent e) {
            if (e.isPopupTrigger()) {
                JPopupMenu popupMenu = makePopupMenu();
                if (null != popupMenu && popupMenu.getComponentCount() != 0) {
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        }

        public void mousePressed(MouseEvent e) {
            maybeShowPopup(e);
        }
    }


    class CopyAction extends ActionWithKey {
        public CopyAction() {
            this("copy", "Copy");
        }

        protected CopyAction(String key, String name) {
            super(key, name);
        }

        public boolean isEnabled() {
            return getSelectedColumnCount() > 0 && getSelectedRowCount() > 0;
        }

        public void actionPerformed(ActionEvent e) {
            if (isEnabled()) {
                int selectedColCount = getSelectedColumnCount();
                int selectedRowCount = getSelectedRowCount();

                int[] selectedRows = getSelectedRows();
                int[] selectedColumns = getSelectedColumns();

                String data = buildStringRepresentation(selectedRowCount, selectedColCount, selectedRows, selectedColumns);

                StringSelection stringSelection = new StringSelection(data);
                Clipboard systemClipboard = getSystemClipboard();
                systemClipboard.setContents(stringSelection, stringSelection);
            }
        }

    }

    class CutAction extends CopyAction {
        public CutAction() {
            super("cut", "Cut");
        }

        public boolean isEnabled() {
            return super.isEnabled() &&
                    getSelectedColumn() >= 1 &&
                    getSelectedRow() >= 1 &&
                    getContextTableModel().hasAtLeastOneNonHeaderCell(getSelectedRows(), getSelectedColumns());
        }

        public void actionPerformed(ActionEvent e) {
            super.actionPerformed(e);
            new ClearCellAction().actionPerformed(e);
        }
    }


    private static Clipboard getSystemClipboard() {
        return Toolkit.getDefaultToolkit().getSystemClipboard();
    }

    public String buildStringRepresentation(int selectedRowCount, int selectedColCount, int[] selectedRows, int[] selectedColumns) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < selectedRowCount; i++) {
            for (int j = 0; j < selectedColCount; j++) {
                int selectedRow = selectedRows[i];
                final int selectedColumn = selectedColumns[j];
                Object valueAt = getExternalValueRepresentation(selectedRow, selectedColumn);
                buffer.append(valueAt);
                if (j < selectedColCount - 1) {
                    buffer.append('\t');
                }
            }
            buffer.append('\n');
        }
        return buffer.toString();
    }

    private Object getExternalValueRepresentation(int selectedRow, int selectedColumn) {
        return getContextTableModel().getExternal(selectedRow, convertColumnIndexToModel(selectedColumn));
    }

    class PasteAction extends ActionWithKey {
        public PasteAction() {
            super("paste", "Paste");
        }

        public boolean isEnabled() {
            if (getSelectedRow() < 0) {
                return false;
            }
            if (getSelectedColumn() < 0) {
                return false;
            }
            final Transferable contents = getSystemClipboard().getContents(ContextTable.this);
            if (null == contents) {
                return false;
            }
            final boolean dataFlavorSupported = contents.isDataFlavorSupported(DataFlavor.stringFlavor);
            if (!dataFlavorSupported) {
                return false;
            }
            try {
                String content = (String) contents.getTransferData(DataFlavor.stringFlavor);
                return contentCanBePasted(content, getSelectedRow(), getSelectedColumn());
            } catch (UnsupportedFlavorException e) {
                return false;
            } catch (IOException e) {
                return false;//To change body of catch statement use Options | File Templates.
            }
        }


        public void actionPerformed(ActionEvent e) {

            int startRow = getSelectedRow();
            int startCol = getSelectedColumn();
            try {
                String clipboardContent =
                        (String) getSystemClipboard().getContents(ContextTable.this).getTransferData(DataFlavor.stringFlavor);

                try {
                    getContextTableModel().startCompoundCommand();
                    processCellsOnPaste(clipboardContent, startRow, startCol, new CellProcessor() {
                        public void processCell(String element, int row, int col) throws DataFormatException {
                            setValueAt(convertElement(element, row, col), row, col);
                        }
                    });
                } finally {
                    getContextTableModel().endCompoundCommand();
                }


            } catch (UnsupportedFlavorException e1) {
                e1.printStackTrace();  //To change body of catch statement use Options | File Templates.
            } catch (IOException e1) {
                e1.printStackTrace();  //To change body of catch statement use Options | File Templates.
            }
        }


    }


    interface CellProcessor {
        void processCell(String element, int row, int col) throws DataFormatException;
    }

    public boolean contentCanBePasted(String content, int firstSelectedRow, int firstSelectedColumn) {
        CellProcessor cellProcessor = new CellProcessor() {
            public void processCell(String element, int row, int col) throws DataFormatException {
                convertElement(element, row, col);
            }
        };
        return processCellsOnPaste(content, firstSelectedRow, firstSelectedColumn, cellProcessor);
    }

    private boolean processCellsOnPaste(String content, int firstSelectedRow, int firstSelectedColumn, CellProcessor cellProcessor) {
        StringTokenizer lineTokenizer = new StringTokenizer(content, "\n");
        int currRow = firstSelectedRow;

        int firstColCount = -1;
        try {
            while (lineTokenizer.hasMoreTokens()) {
                if (currRow >= getRowCount()) {
                    return false;
                }
                String line = lineTokenizer.nextToken();


                List elementTokenizer = split(line, '\t');
                int colCounter = 0;
                for (; colCounter < elementTokenizer.size(); colCounter++) {
                    String element = (String) elementTokenizer.get(colCounter);
                    final int currCol = firstSelectedColumn + colCounter;
                    if (currCol >= getColumnCount()) {
                        return false;
                    }
                    cellProcessor.processCell(element, currRow, currCol);
                }
                if (firstColCount == -1) {
                    firstColCount = colCounter;
                } else if (firstColCount != colCounter) {
                    //different number of elements in rows
                    return false;
                }
                currRow++;
            }

        } catch (DataFormatException e) {
            return false;
        }

        return true;
    }

    public static List split(String line, char c) {
        List result = new ArrayList();
        int index = line.indexOf(c);
        int startIndex = 0;
        while (-1 != index) {
            result.add(line.substring(startIndex, index));
            startIndex = index + 1;
            index = line.indexOf(c, startIndex);
        }
        result.add(line.substring(startIndex));
        return result;
    }

    private Object convertElement(String element, int currRow, int currCol) throws DataFormatException {
        return getContextTableModel().convertToInternal(element, currRow, convertColumnIndexToModel(currCol));
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
        protected ActionOnSelectedContextCells(String key, String name) {
            super(key, name);
        }

        public boolean isEnabled() {
            return getContextTableModel().hasAtLeastOneNonHeaderCell(getSelectedRows(), getSelectedColumns());
        }

        public void actionPerformed(ActionEvent e) {
            getContextTableModel().applyCellTransformerToNonHeaderCells(getSelectedRows(), getModelSelectedColumns(),
                    getTransformer());
        }

        private int[] getModelSelectedColumns() {
            final int[] selectedColumns = getSelectedColumns();
            for (int i = 0; i < selectedColumns.length; i++) {
                selectedColumns[i] = convertColumnIndexToModel(selectedColumns[i]);
            }
            return selectedColumns;
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
        return (ContextTableModel) getModel();
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
            ParamInfo[] tableViewerParams = this.getNativeParams();
            ParamInfo[] modelParams = getContextTableModel().getParams();
            params = new ParamInfo[rendererParams.length
                    + modelParams.length + tableViewerParams.length];
            System.arraycopy(rendererParams, 0, params, 0, rendererParams.length);
            System.arraycopy(tableViewerParams, 0, params, rendererParams.length, tableViewerParams.length);

            System.arraycopy(modelParams, 0, params, rendererParams.length + tableViewerParams.length, modelParams.length);
        }
        return params;
    }

    private ParamInfo[] getNativeParams() {

        return new ParamInfo[]{
                new BooleanParamInfo("Compressed", compressView)
        };
    }

    public static void initKeyboard(ContextTable contextTable) {
        KeyStroke xPressed = KeyStroke.getKeyStroke('x');
        KeyStroke dotPressed = KeyStroke.getKeyStroke('.');

        contextTable.registerKeyboardAction(new FastEditingListener(contextTable, Boolean.TRUE), xPressed, JComponent.WHEN_FOCUSED);
        FastEditingListener makeCellEmptyListener = new FastEditingListener(contextTable, Boolean.FALSE);
        contextTable.registerKeyboardAction(makeCellEmptyListener, dotPressed, JComponent.WHEN_FOCUSED);

        KeyStroke copy = KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK, false);
        KeyStroke paste = KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK, false);
        KeyStroke cut = KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK, false);

        contextTable.registerKeyboardAction(contextTable.new CopyAction(), copy, JComponent.WHEN_FOCUSED);
        contextTable.registerKeyboardAction(contextTable.new PasteAction(), paste, JComponent.WHEN_FOCUSED);
        contextTable.registerKeyboardAction(contextTable.new CutAction(), cut, JComponent.WHEN_FOCUSED);
    }

}

/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.contexteditor;


import javax.swing.*;
import javax.swing.event.CellEditorListener;
import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.event.*;
import java.io.Serializable;
import java.util.EventObject;

public class ContextCellEditor extends util.gui.celleditors.BaseCellEditor {
    protected Component editorComponent;
    protected JButton crossEditorComponent = new JButton();
    protected JTextField textEditorComponent = new JTextField();
    protected EditorDelegate crossDelegate;
    protected EditorDelegate textDelegate;
    protected EditorDelegate delegate;
    protected int clickCountToStart = 1;


    boolean repaintMode = false;

    protected class EditorDelegate implements ActionListener, ItemListener, Serializable {

        protected Object value;

        public Object getCellEditorValue() {
            return value;
        }

        public void setValue(Object x) {
            this.value = x;
        }

        public boolean isCellEditable(EventObject anEvent) {
            return true;
        }

        public boolean startCellEditing(EventObject anEvent) {
            return true;
        }

        public boolean stopCellEditing() {
///*DBG*/ System.out.println("stopCellEditing");
            return true;
        }

        public void cancelCellEditing() {
        }

        // Implementing ActionListener interface
        public void actionPerformed(ActionEvent e) {
///*DBG*/ System.out.println("actionPerformed");
            fireEditingStopped();
        }

        // Implementing ItemListener interface
        public void itemStateChanged(ItemEvent e) {
            ///*DBG*/ System.out.println("itemStateChanged");
            fireEditingStopped();
        }
    }

    public ContextCellEditor() {
//        this.clickCountToStart = 1;
        this.clickCountToStart = 2;
        this.crossDelegate = new EditorDelegate() {
            public void setValue(Object v) {
                //*DBG*/ System.out.println("cross setValue "+v );
                if (v != null) {
                    if (v instanceof Boolean) {
                        super.setValue(((Boolean) v).booleanValue() ? Boolean.FALSE : Boolean.TRUE);
                        crossEditorComponent.setIcon(((Boolean) value).booleanValue() ? CrossIcon.getCross() : null);
                    }
                } else {
                    crossEditorComponent.setIcon(null);
                    super.setValue(Boolean.FALSE);
                }
            }

            public boolean startCellEditing(EventObject anEvent) {
                //*DBG*/ System.out.println("startCellEditing" );
                if (anEvent instanceof AWTEvent) {
                    fireEditingStopped();
                    return true;
                }
                return false;
            }

            public String toString() {
                return "Cross Delegete " + value;
            }


        };
        textDelegate = new EditorDelegate() {
            public void setValue(Object v) {
                //*DBG*/ System.out.println("text setValue "+v );
                if (v != null) {
                    if (v instanceof String) {
                        textEditorComponent.setText((String) v);
                    }
                }
                super.setValue(v);

            }

            public boolean stopCellEditing() {
                //*DBG*/ System.out.println("stopCellEditing");
                String res = textEditorComponent.getText();
                res.trim();
                if (res.length() > 0) {
                    setValue(res);
                    return true;
                }
                return false;
            }

            // Implementing ActionListener interface
            public void actionPerformed(ActionEvent e) {
                //*DBG*/ System.out.println("actionPerformed");
                if (stopCellEditing())
                    fireEditingStopped();
                else
                    fireEditingCanceled();
            }

            public String toString() {
                return "Text Delegete " + value;
            }
        };
        crossEditorComponent.addActionListener(crossDelegate);
        crossEditorComponent.setSelectedIcon(CrossIcon.getCross());
        textEditorComponent.addActionListener(textDelegate);
        delegate = textDelegate;
    }

    // implements javax.swing.CellEditor
    public void addCellEditorListener(CellEditorListener l) {
        listenerList.add(CellEditorListener.class, l);
    }

    // implements javax.swing.CellEditor
    public void cancelCellEditing() {
        delegate.cancelCellEditing();
        fireEditingCanceled();
    }

//
//  Implementing the CellEditor Interface
//

    // implements javax.swing.CellEditor
    public Object getCellEditorValue() {
        return delegate.getCellEditorValue();
    }

    /**
     *  clickCountToStart controls the number of clicks required to start
     *  editing if the event passed to isCellEditable() or startCellEditing() is
     *  a MouseEvent.  For example, by default the clickCountToStart for
     *  a JTextField is set to 2, so in a JTable the user will need to
     *  double click to begin editing a cell.
     */
    public int getClickCountToStart() {
        return clickCountToStart;
    }

    /**
     * Returns the a reference to the editor component.
     *
     * @return the editor Component
     */
    public Component getComponent() {
        return editorComponent;
    }
//
//  Implementing the CellEditor Interface
//

    // implements javax.swing.table.TableCellEditor
    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected,
                                                 int row, int col) {
        if ((0 == row && 0 != col)
                || (0 == col && 0 != row)) {
            delegate = textDelegate;
            editorComponent = textEditorComponent;
        } else {
            delegate = crossDelegate;
            editorComponent = crossEditorComponent;
        }
        delegate.setValue(value);
        return editorComponent;
    }
//
//  Implementing the TreeCellEditor Interface
//

    // implements javax.swing.tree.TreeCellEditor
    public Component getTreeCellEditorComponent(JTree tree, Object value,
                                                boolean isSelected,
                                                boolean expanded,
                                                boolean leaf, int row) {
        String stringValue = tree.convertValueToText(value, isSelected,
                expanded, leaf, row, false);

        delegate.setValue(stringValue);
        return editorComponent;
    }

    // implements javax.swing.CellEditor
    public boolean isCellEditable(EventObject anEvent) {
        if (anEvent instanceof MouseEvent) {
            if (((MouseEvent) anEvent).getClickCount() < clickCountToStart)
                return false;
        }
        if (null != delegate) {
            return delegate.isCellEditable(anEvent);
        }
        return true;
    }

    // implements javax.swing.CellEditor
    public void removeCellEditorListener(CellEditorListener l) {
        listenerList.remove(CellEditorListener.class, l);
    }
//
//  Modifying
//

    /**
     * Specifies the number of clicks needed to start editing.
     *
     * @param count  an int specifying the number of clicks needed to start editing
     * @see #getClickCountToStart
     */
    public void setClickCountToStart(int count) {
        clickCountToStart = count;
    }

    // implements javax.swing.CellEditor
    public boolean shouldSelectCell(EventObject anEvent) {

        ///*DBG*/ System.out.println("shouldSelectCell");
        if (null != crossEditorComponent) {
            ///*DBG*/ System.out.println("shouldSelectCell " + crossEditorComponent);
        }

        boolean retValue = true;
        if (this.isCellEditable(anEvent)) {
            if (anEvent == null ||
                    ((MouseEvent) anEvent).getClickCount() >= clickCountToStart) {
                retValue = delegate.startCellEditing(anEvent);
            }
        }

        // By default we want the cell the be selected so
        // we return true
        //TODO: Check return value
        return retValue;
    }

    // implements javax.swing.CellEditor
    public boolean stopCellEditing() {
        if (delegate.stopCellEditing()) {
            fireEditingStopped();
            return true;
        }

        return false;
    }
}

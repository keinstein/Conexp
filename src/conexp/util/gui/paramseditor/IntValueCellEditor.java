package conexp.util.gui.paramseditor;



public class IntValueCellEditor extends javax.swing.DefaultCellEditor {


    public Object getCellEditorValue() {
        return new Integer(getEditField().getValue());
    }

    public void setValue(Object obj) {
        if (obj instanceof Integer) {
            getEditField().setValue(((Integer) obj).intValue());
        }
    }

    public boolean stopCellEditing() {
        try {
            getEditField().updateValue();
            fireEditingStopped();
            return true;
        } catch (java.beans.PropertyVetoException ex) {
            javax.swing.JOptionPane.showMessageDialog(null, ex.getMessage());
            javax.swing.SwingUtilities.invokeLater(new util.gui.FocusGrabber(getEditField()));
            return false;
        }
    }

    IntValueCellEditor(conexp.util.gui.IntValueWholeNumberField f) {
        super(f);
        f.setAlignmentX(javax.swing.JLabel.RIGHT_ALIGNMENT);
        f.removeActionListener(delegate);
        //to overcame differences between 1.2.2 and 1.3
        f.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                stopCellEditing();
            }
        });
    }


    conexp.util.gui.IntValueWholeNumberField getEditField() {
        return (conexp.util.gui.IntValueWholeNumberField) editorComponent;
    }
}
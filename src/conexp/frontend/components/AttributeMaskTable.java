/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.components;

import conexp.frontend.AttributeMask;

import javax.swing.*;

public class AttributeMaskTable extends JTable {
    public AttributeMaskTable(AttributeMask attributeMask) {
        super(new AttributeMaskTableModel(attributeMask));
        setRowSelectionAllowed(false);
        setColumnSelectionAllowed(false);
        getTableHeader().setReorderingAllowed(false);
        setBackground(javax.swing.UIManager.getColor("control"));
    }

    protected AttributeMaskTableModel getAttributeMaskTableModel() {
        return (AttributeMaskTableModel) getModel();
    }

    public void setAttributeMask(AttributeMask newMask) {
        getAttributeMaskTableModel().setAttributeMask(newMask);
    }

}

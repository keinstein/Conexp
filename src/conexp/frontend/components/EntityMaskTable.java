/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.components;

import conexp.frontend.EntitiesMask;

import javax.swing.*;

public class EntityMaskTable extends JTable {
    public EntityMaskTable(EntitiesMask attributeMask) {
        super(new AttributeMaskTableModel(attributeMask));
        setRowSelectionAllowed(false);
        setColumnSelectionAllowed(false);
        getTableHeader().setReorderingAllowed(false);
        setBackground(javax.swing.UIManager.getColor("control"));
    }

    protected AttributeMaskTableModel getAttributeMaskTableModel() {
        return (AttributeMaskTableModel) getModel();
    }

    public void setAttributeMask(EntitiesMask newMask) {
        getAttributeMaskTableModel().setAttributeMask(newMask);
    }

}

/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.components;

import conexp.frontend.EntitiesMask;

import javax.swing.*;

import util.gui.JTableX;

public class EntityMaskTable extends JTableX {
    public EntityMaskTable(EntitiesMask attributeMask) {
        super(new EntityMaskTableModel(attributeMask));
        setRowSelectionAllowed(false);
        setColumnSelectionAllowed(false);
        getTableHeader().setReorderingAllowed(false);
        setBackground(UIManager.getColor("control"));
    }

    protected EntityMaskTableModel getAttributeMaskTableModel() {
        return (EntityMaskTableModel) getModel();
    }

    public void setAttributeMask(EntitiesMask newMask) {
        getAttributeMaskTableModel().setEntitiesMask(newMask);
    }

}

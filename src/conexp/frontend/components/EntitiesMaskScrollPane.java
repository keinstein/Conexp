/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.components;

import conexp.frontend.EntitiesMask;

import javax.swing.*;
import javax.swing.border.BevelBorder;

public class EntitiesMaskScrollPane extends JScrollPane {
    private EntityMaskTable entitySelectionTable;

    public EntityMaskTable getEntitySelectionTable() {
        return entitySelectionTable;
    }

    public EntitiesMaskScrollPane(EntitiesMask mask) {
        entitySelectionTable = new EntityMaskTable(mask);
        setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        add(entitySelectionTable);
        setViewportView(entitySelectionTable);
    }
}
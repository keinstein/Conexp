/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.components;

import conexp.frontend.AttributeMask;

import javax.swing.*;
import javax.swing.border.BevelBorder;

public class AttributeMaskScrollPane extends JScrollPane {
    private AttributeMaskTable attributeSelectionTable;

    public AttributeMaskTable getAttributeSelectionTable() {
        return attributeSelectionTable;
    }

    public AttributeMaskScrollPane(AttributeMask mask) {
        attributeSelectionTable = new AttributeMaskTable(mask);
        setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        add(attributeSelectionTable);
        setViewportView(attributeSelectionTable);
    }
}

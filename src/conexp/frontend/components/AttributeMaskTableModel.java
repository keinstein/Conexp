/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.components;

import conexp.frontend.EntitiesMask;
import util.Assert;
import util.BooleanUtil;

import javax.swing.table.AbstractTableModel;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class AttributeMaskTableModel extends AbstractTableModel {
    EntitiesMask attributeMask;

    class AttributeMaskPropertyChangeListener implements PropertyChangeListener {
        public void propertyChange(PropertyChangeEvent evt) {
            final String propertyName = evt.getPropertyName();

            if (EntitiesMask.ENTITIES_SELECTION_CHANGED.equals(propertyName)) {
                fireTableDataChanged(); //for general case, with single selection
            }

            if (EntitiesMask.ENTITIES_NAMES_CHANGED.equals(propertyName)) {
                fireTableDataChanged(); //will be good fireTableCellUpdated
            }
            if (EntitiesMask.ENTITIES_COUNT_CHANGED.equals(propertyName)) {
                fireTableStructureChanged();
            }
        }
    };

    public AttributeMaskTableModel(EntitiesMask attributeMask) {
        setAttributeMask(attributeMask);
    }

    public void setAttributeMask(EntitiesMask attributeMask) {
        this.attributeMask = attributeMask;
        attributeMask.addPropertyChangeListener(new AttributeMaskPropertyChangeListener());
        fireTableDataChanged();
        fireTableStructureChanged();
    }

    public int getRowCount() {
        return attributeMask.getCount();
    }

    public int getColumnCount() {
        return 2;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        if (isAttributesNamesColumn(columnIndex)) {
            return attributeMask.getName(rowIndex);
        } else {
            return BooleanUtil.valueOf(attributeMask.isSelected(rowIndex));
        }
    }

    private boolean isAttributesNamesColumn(int columnIndex) {
        return 0 == columnIndex;
    }

    public Class getColumnClass(int columnIndex) {
        if (isAttributesNamesColumn(columnIndex)) {
            return String.class;
        }
        return Boolean.class;
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return !isAttributesNamesColumn(columnIndex);
    }

    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Assert.isTrue(!isAttributesNamesColumn(columnIndex));
        if (aValue instanceof Boolean) {
            attributeMask.setSelected(rowIndex, ((Boolean) aValue).booleanValue());
        }
    }

    public String getColumnName(int column) {
        return isAttributesNamesColumn(column) ? "Attribute" : "Is selected";
    }
}

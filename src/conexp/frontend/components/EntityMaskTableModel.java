/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
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

public class EntityMaskTableModel extends AbstractTableModel {
    EntitiesMask entitiesMask;

    class EntityMaskPropertyChangeListener implements PropertyChangeListener {
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
    }

    public EntityMaskTableModel(EntitiesMask attributeMask) {
        setEntitiesMask(attributeMask);
    }

    public void setEntitiesMask(EntitiesMask entitiesMask) {
        this.entitiesMask = entitiesMask;
        entitiesMask.addPropertyChangeListener(new EntityMaskPropertyChangeListener());
        fireTableDataChanged();
        fireTableStructureChanged();
    }

    public int getRowCount() {
        return entitiesMask.getCount();
    }

    public int getColumnCount() {
        return 2;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        if (isNamesColumn(columnIndex)) {
            return entitiesMask.getName(rowIndex);
        } else {
            return BooleanUtil.valueOf(entitiesMask.isSelected(rowIndex));
        }
    }

    private static boolean isNamesColumn(int columnIndex) {
        return 0 == columnIndex;
    }

    public Class getColumnClass(int columnIndex) {
        if (isNamesColumn(columnIndex)) {
            return String.class;
        }
        return Boolean.class;
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return !isNamesColumn(columnIndex);
    }

    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Assert.isTrue(!isNamesColumn(columnIndex));
        if (aValue instanceof Boolean) {
            entitiesMask.setSelected(rowIndex, ((Boolean) aValue).booleanValue());
        }
    }

    public String getColumnName(int column) {
        return isNamesColumn(column) ? "Name" : "Is selected";
    }
}

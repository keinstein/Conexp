/*
 * User: Serhiy Yevtushenko
 * Date: May 8, 2002
 * Time: 1:02:26 AM
 */
package conexp.frontend.components;

import conexp.frontend.AttributeMask;
import util.Assert;
import util.BooleanUtil;

import javax.swing.table.AbstractTableModel;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class AttributeMaskTableModel extends AbstractTableModel {
    AttributeMask attributeMask;

    class AttributeMaskPropertyChangeListener implements PropertyChangeListener {
        public void propertyChange(PropertyChangeEvent evt) {
            final String propertyName = evt.getPropertyName();

            if (AttributeMask.ATTRIBUTE_SELECTION_CHANGED.equals(propertyName)) {
                fireTableDataChanged(); //for general case, with single selection
            }

            if (AttributeMask.ATTRIBUTE_NAMES_CHANGED.equals(propertyName)) {
                fireTableDataChanged(); //will be good fireTableCellUpdated
            }
            if (AttributeMask.ATTRIBUTE_COUNT_CHANGED.equals(propertyName)) {
                fireTableStructureChanged();
            }
        }
    };

    public AttributeMaskTableModel(AttributeMask attributeMask) {
        setAttributeMask(attributeMask);
    }

    public void setAttributeMask(AttributeMask attributeMask) {
        this.attributeMask = attributeMask;
        attributeMask.addPropertyChangeListener(new AttributeMaskPropertyChangeListener());
        fireTableDataChanged();
        fireTableStructureChanged();
    }

    public int getRowCount() {
        return attributeMask.getAttributeCount();
    }

    public int getColumnCount() {
        return 2;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        if (isAttributesNamesColumn(columnIndex)) {
            return attributeMask.getAttributeName(rowIndex);
        } else {
            return BooleanUtil.valueOf(attributeMask.isAttributeSelected(rowIndex));
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
            attributeMask.setAttributeSelected(rowIndex, ((Boolean) aValue).booleanValue());
        }
    }

    public String getColumnName(int column) {
        return isAttributesNamesColumn(column) ? "Attribute" : "Is selected";
    }
}

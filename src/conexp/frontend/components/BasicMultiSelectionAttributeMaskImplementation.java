/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.components;


public abstract class BasicMultiSelectionAttributeMaskImplementation extends BasicAttributeMaskImplementation {

    public void setAttributeSelected(int index, boolean attributeSelected) {
        boolean oldValue = isAttributeSelected(index);
        if (oldValue != attributeSelected) {
            doSetAttributeSelected(index, attributeSelected);
            getPropertyChangeSupport().firePropertyChange(ATTRIBUTE_SELECTION_CHANGED, null, null);
        }
    }

    public void selectAll() {
        setValueForAll(Boolean.TRUE);
    }

    public void deselectAll() {
        setValueForAll(Boolean.FALSE);
    }


}

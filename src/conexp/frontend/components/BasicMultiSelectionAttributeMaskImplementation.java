/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.components;

import conexp.frontend.MultiSelectionAttributeMask;


public abstract class BasicMultiSelectionAttributeMaskImplementation extends BasicAttributeMaskImplementation implements MultiSelectionAttributeMask{

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

    public boolean hasUnselectedAttributes() {
        for(int i=getAttributeCount(); --i>=0;){
            if(!isAttributeSelected(i)){
                return true;
            }
        }
        return false;
    }


}

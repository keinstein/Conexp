/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.components;


public abstract class BasicSingleSelectionAttributeMaskImplementation extends BasicAttributeMaskImplementation {
    public BasicSingleSelectionAttributeMaskImplementation() {
        super();
    }

    public void setAttributeSelected(int index, boolean attributeSelected) {
        boolean oldValue = isAttributeSelected(index);
        if (oldValue != attributeSelected) {
            final int attributeCount = getAttributeCount();
            if (attributeSelected) {
                for (int i = 0; i < attributeCount; i++) {
                    doSetAttributeSelected(i, i == index);
                }
            } else {
                doSetAttributeSelected(index, false);
            }
            getPropertyChangeSupport().firePropertyChange(ATTRIBUTE_SELECTION_CHANGED, null, null);
        }
    }
}


/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.components;


public abstract class BasicSingleSelectionAttributeMaskImplementation extends BasicEntityMaskImplementation {
    public BasicSingleSelectionAttributeMaskImplementation() {
        super();
    }

    public void setSelected(int index, boolean attributeSelected) {
        boolean oldValue = isSelected(index);
        if (oldValue != attributeSelected) {
            final int attributeCount = getCount();
            if (attributeSelected) {
                for (int i = 0; i < attributeCount; i++) {
                    doSetAttributeSelected(i, i == index);
                }
            } else {
                doSetAttributeSelected(index, false);
            }
            getPropertyChangeSupport().firePropertyChange(ENTITIES_SELECTION_CHANGED, null, null);
        }
    }
}


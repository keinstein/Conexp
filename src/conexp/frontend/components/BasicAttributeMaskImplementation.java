/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.components;

import conexp.frontend.AttributeMask;
import util.BasePropertyChangeSupplier;
import util.BooleanUtil;

import java.util.ArrayList;

public abstract class BasicAttributeMaskImplementation extends BasePropertyChangeSupplier implements AttributeMask {
    protected ArrayList selectedAttributes = new ArrayList();

    protected void initializeMask(final int attributeCount, final Boolean defaultValue) {
        for (int i = 0; i < attributeCount; i++) {
            addAttributeWithDefaultValue(defaultValue);
        }
    }

    protected void addAttributeWithDefaultValue(Boolean defaultValue) {
        selectedAttributes.add(defaultValue);
    }

    public boolean isAttributeSelected(int index) {
        return ((Boolean) selectedAttributes.get(index)).booleanValue();
    }

    protected void doSetAttributeSelected(int index, boolean attributeSelected) {
        selectedAttributes.set(index, BooleanUtil.valueOf(attributeSelected));
    }

    protected void setValueForAll(Boolean newValue) {
        boolean changed = false;
        final boolean newBooleanValue = newValue.booleanValue();
        final int attributeCount = getAttributeCount();

        for (int i = 0; i < attributeCount; i++) {
            boolean oldValue = isAttributeSelected(i);
            if (oldValue != newBooleanValue) {
                changed = true;
                selectedAttributes.set(i, newValue);
            }
        }
        if (changed) {
            getPropertyChangeSupport().firePropertyChange(ATTRIBUTE_SELECTION_CHANGED, null, null);
        }
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof AttributeMask)) {
            return false;
        }
        AttributeMask that = (AttributeMask) obj;
        if (this.getAttributeCount() != that.getAttributeCount()) {
            return false;
        }
        for (int i = getAttributeCount(); --i >= 0;) {
            if (this.isAttributeSelected(i) != that.isAttributeSelected(i)) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        return selectedAttributes.hashCode();
    }
}

/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.components;

import conexp.frontend.EntitiesMask;
import util.BasePropertyChangeSupplier;
import util.BooleanUtil;
import util.collection.CollectionFactory;

import java.util.List;

public abstract class BasicEntityMaskImplementation extends BasePropertyChangeSupplier implements EntitiesMask {
    protected List selectedEntities = CollectionFactory.createFastIndexAccessList();

    protected void initializeMask(final int count, final Boolean defaultValue) {
        for (int i = 0; i < count; i++) {
            addAttributeWithDefaultValue(defaultValue);
        }
    }

    protected void addAttributeWithDefaultValue(Boolean defaultValue) {
        selectedEntities.add(defaultValue);
    }

    public boolean isSelected(int index) {
        return ((Boolean) selectedEntities.get(index)).booleanValue();
    }

    protected void doSetAttributeSelected(int index, boolean newValue) {
        selectedEntities.set(index, BooleanUtil.valueOf(newValue));
    }

    protected void setValueForAll(Boolean newValue) {
        boolean changed = false;
        final boolean newBooleanValue = newValue.booleanValue();
        final int count = getCount();

        for (int i = 0; i < count; i++) {
            boolean oldValue = isSelected(i);
            if (oldValue != newBooleanValue) {
                changed = true;
                selectedEntities.set(i, newValue);
            }
        }
        if (changed) {
            getPropertyChangeSupport().firePropertyChange(ENTITIES_SELECTION_CHANGED, null, null);
        }
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof EntitiesMask)) {
            return false;
        }
        EntitiesMask that = (EntitiesMask) obj;
        if (this.getCount() != that.getCount()) {
            return false;
        }
        for (int i = getCount(); --i >= 0;) {
            if (this.isSelected(i) != that.isSelected(i)) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        return selectedEntities.hashCode();
    }
}

/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.components;

import conexp.core.ContextFactoryRegistry;
import conexp.core.ModifiableSet;
import conexp.core.Set;
import conexp.frontend.MultiSelectionEntitiesMask;
import conexp.frontend.SetProvidingEntitiesMask;
import util.Assert;
import util.StringUtil;


public abstract class BasicMultiSelectionEntityMaskImplementation extends BasicEntityMaskImplementation implements MultiSelectionEntitiesMask, SetProvidingEntitiesMask {

    public void setSelected(int index, boolean attributeSelected) {
        boolean oldValue = isSelected(index);
        if (oldValue != attributeSelected) {
            doSetAttributeSelected(index, attributeSelected);
            fireEntitiesSelectionChanged();
        }
    }

    public void selectAll() {
        setValueForAll(Boolean.TRUE);
    }

    public void deselectAll() {
        setValueForAll(Boolean.FALSE);
    }

    public boolean hasUnselected() {
        for (int i = getCount(); --i >= 0;) {
            if (!isSelected(i)) {
                return true;
            }
        }
        return false;
    }

    public Set toSet() {
        ModifiableSet result = ContextFactoryRegistry.createSet(getCount());
        for (int j = getCount(); --j >= 0;) {
            if (isSelected(j)) {
                result.put(j);
            }
        }
        return result;
    }


    protected void fireEntitiesSelectionChanged() {
        getPropertyChangeSupport().firePropertyChange(ENTITIES_SELECTION_CHANGED, null, null);
    }

    protected void uncheckedCopyTo(BasicMultiSelectionEntityMaskImplementation ret) {
        Assert.isTrue(getCount() == ret.getCount());
        for (int i = getCount(); --i >= 0;) {
            ret.setSelected(i, isSelected(i));
        }
    }

    public String toString() {
        return StringUtil.extractClassName(getClass().getName()) + toSet().toString();
    }
}

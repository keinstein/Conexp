/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.components;

import conexp.core.*;
import conexp.frontend.SetProvidingAttributeMask;
import util.Assert;

import java.beans.PropertyChangeEvent;

public class ContextAttributeMask extends BasicMultiSelectionAttributeMaskImplementation implements SetProvidingAttributeMask {
    ExtendedContextEditingInterface context;

    class AttributeMaskContextListener extends DefaultContextListener {
        public void attributeChanged(ContextChangeEvent changeEvent) {
            switch (changeEvent.getType()) {
                case ContextChangeEvent.ATTRIBUTE_REMOVED:
                    {
                        int oldValue = selectedAttributes.size();
                        selectedAttributes.remove(changeEvent.getColumn());
                        getPropertyChangeSupport().firePropertyChange(ATTRIBUTE_COUNT_CHANGED, oldValue, selectedAttributes.size());
                        break;
                    }
                case ContextChangeEvent.ATTRIBUTE_ADDED:
                    {
                        int oldValue = selectedAttributes.size();
                        selectedAttributes.add(Boolean.FALSE);
                        getPropertyChangeSupport().firePropertyChange(ATTRIBUTE_COUNT_CHANGED, oldValue, selectedAttributes.size());
                        break;
                    }
            }
        }

        public void attributeNameChanged(PropertyChangeEvent evt) {
            getPropertyChangeSupport().firePropertyChange(ATTRIBUTE_NAMES_CHANGED, null, null);
        }

        public void contextTransposed() {
            int newValue = getAttributeCount();
            final int oldValue = selectedAttributes.size();
            if (newValue < oldValue) {
                for (int i = oldValue; --i >= newValue;) {
                    selectedAttributes.remove(i);
                }
            } else {
                for (int i = oldValue; i < newValue; i++) {
                    selectedAttributes.add(Boolean.TRUE);
                }
            }
            Assert.isTrue(selectedAttributes.size() == newValue);
            getPropertyChangeSupport().firePropertyChange(ATTRIBUTE_COUNT_CHANGED,
                    oldValue, newValue);
            selectAll();

        }

    }

    public ContextAttributeMask(ExtendedContextEditingInterface context) {
        this.context = context;
        initializeMask(getAttributeCount(), Boolean.TRUE);
        this.context.addContextListener(new AttributeMaskContextListener());
    }

    public int getAttributeCount() {
        return context.getAttributeCount();
    }

    public String getAttributeName(int index) {
        return context.getAttribute(index).getName();
    }

    public Set toSet() {
        ModifiableSet result = ContextFactoryRegistry.createSet(getAttributeCount());
        for (int j = getAttributeCount(); --j >= 0;) {
            if (isAttributeSelected(j)) {
                result.put(j);
            }
        }
        return result;
    }

}

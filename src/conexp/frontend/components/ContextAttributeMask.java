/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.components;

import conexp.core.ContextChangeEvent;
import conexp.core.DefaultContextListener;
import conexp.core.ExtendedContextEditingInterface;
import util.Assert;

import java.beans.PropertyChangeEvent;

public class ContextAttributeMask extends BasicMultiSelectionEntityMaskImplementation {
    ExtendedContextEditingInterface context;

    class AttributeMaskContextListener extends DefaultContextListener {
        public void attributeChanged(ContextChangeEvent changeEvent) {
            switch (changeEvent.getType()) {
                case ContextChangeEvent.ATTRIBUTE_REMOVED:
                    {
                        int oldValue = selectedEntities.size();
                        selectedEntities.remove(changeEvent.getColumn());
                        getPropertyChangeSupport().firePropertyChange(ENTITIES_COUNT_CHANGED, oldValue, selectedEntities.size());
                        break;
                    }
                case ContextChangeEvent.ATTRIBUTE_ADDED:
                    {
                        int oldValue = selectedEntities.size();
                        selectedEntities.add(Boolean.FALSE);
                        getPropertyChangeSupport().firePropertyChange(ENTITIES_COUNT_CHANGED, oldValue, selectedEntities.size());
                        break;
                    }
            }
        }

        public void attributeNameChanged(PropertyChangeEvent evt) {
            getPropertyChangeSupport().firePropertyChange(ENTITIES_NAMES_CHANGED, null, null);
        }

        public void contextTransposed() {
            int newValue = getCount();
            final int oldValue = selectedEntities.size();
            if (newValue < oldValue) {
                for (int i = oldValue; --i >= newValue;) {
                    selectedEntities.remove(i);
                }
            } else {
                for (int i = oldValue; i < newValue; i++) {
                    selectedEntities.add(Boolean.TRUE);
                }
            }
            Assert.isTrue(selectedEntities.size() == newValue);
            getPropertyChangeSupport().firePropertyChange(ENTITIES_COUNT_CHANGED,
                    oldValue, newValue);
            selectAll();

        }

    }

    public ContextAttributeMask(ExtendedContextEditingInterface context) {
        this.context = context;
        initializeMask(getCount(), Boolean.TRUE);
        this.context.addContextListener(new AttributeMaskContextListener());
    }

    public int getCount() {
        return context.getAttributeCount();
    }

    public String getName(int index) {
        return context.getAttribute(index).getName();
    }

}

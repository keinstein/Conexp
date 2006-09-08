/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
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
    private AttributeMaskContextListener maskContextListener;

    public ContextAttributeMask makeCopy() {
        ContextAttributeMask ret = new ContextAttributeMask(context);
        uncheckedCopyTo(ret);
        return ret;
    }

    class AttributeMaskContextListener extends DefaultContextListener {
        public void attributeChanged(ContextChangeEvent changeEvent) {
            switch (changeEvent.getType()) {
                case ContextChangeEvent.ATTRIBUTE_REMOVED: {
                    int oldValue = selectedEntities.size();
                    selectedEntities.remove(changeEvent.getColumn());
                    getPropertyChangeSupport().firePropertyChange(ENTITIES_COUNT_CHANGED, oldValue, selectedEntities.size());
                    break;
                }
                case ContextChangeEvent.ATTRIBUTE_ADDED: {
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
        maskContextListener = new AttributeMaskContextListener();
        this.context.addContextListener(maskContextListener);
    }

    public int getCount() {
        return context.getAttributeCount();
    }

    public String getName(int index) {
        return context.getAttribute(index).getName();
    }

    public void cleanUp() {
        this.context.removeContextListener(maskContextListener);
        maskContextListener = null;
        context = null;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ContextAttributeMask)) {
            return false;
        }
        if (!super.equals(other)) {
            return false;
        }

        final ContextAttributeMask contextAttributeMask = (ContextAttributeMask) other;

        if (context != null ? !context.equals(contextAttributeMask.context) : contextAttributeMask.context != null) {
            return false;
        }

        return true;
    }


}

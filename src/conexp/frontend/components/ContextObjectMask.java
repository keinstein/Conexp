/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.components;

import conexp.core.ContextChangeEvent;
import conexp.core.DefaultContextListener;
import conexp.core.ExtendedContextEditingInterface;
import conexp.frontend.SetProvidingEntitiesMask;
import util.Assert;

import java.beans.PropertyChangeEvent;

public class ContextObjectMask extends BasicMultiSelectionEntityMaskImplementation implements SetProvidingEntitiesMask {
    ExtendedContextEditingInterface context;
    private ObjectMaskContextListener maskContextListener;

    public ContextObjectMask makeCopy() {
        ContextObjectMask ret = new ContextObjectMask(context);
        uncheckedCopyTo(ret);
        return ret;  //To change body of created methods use File | Settings | File Templates.
    }

    class ObjectMaskContextListener extends DefaultContextListener {
        public void objectChanged(ContextChangeEvent changeEvent) {
            switch (changeEvent.getType()) {
                case ContextChangeEvent.OBJECT_REMOVED: {
                    int oldValue = selectedEntities.size();
                    selectedEntities.remove(changeEvent.getColumn());
                    getPropertyChangeSupport().firePropertyChange(ENTITIES_COUNT_CHANGED, oldValue, selectedEntities.size());
                    break;
                }
                case ContextChangeEvent.OBJECT_ADDED: {
                    int oldValue = selectedEntities.size();
                    selectedEntities.add(Boolean.FALSE);
                    getPropertyChangeSupport().firePropertyChange(ENTITIES_COUNT_CHANGED, oldValue, selectedEntities.size());
                    break;
                }
            }
        }

        public void objectNameChanged(PropertyChangeEvent evt) {
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


    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ContextObjectMask)) {
            return false;
        }
        if (!super.equals(other)) {
            return false;
        }

        final ContextObjectMask ContextObjectMask = (ContextObjectMask) other;

        if (context != null ? !context.equals(ContextObjectMask.context) : ContextObjectMask.context != null) {
            return false;
        }

        return true;
    }


    public ContextObjectMask(ExtendedContextEditingInterface context) {
        this.context = context;
        initializeMask(getCount(), Boolean.TRUE);
        maskContextListener = new ObjectMaskContextListener();
        this.context.addContextListener(maskContextListener);
    }

    public void cleanUp() {
        this.context.removeContextListener(maskContextListener);
        maskContextListener = null;
        context = null;
    }

    public int getCount() {
        return context.getObjectCount();
    }

    public String getName(int index) {
        return context.getObject(index).getName();
    }

}

/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core;

import util.Assert;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;


public class ContextListenerSupport {
    private transient ArrayList listeners;
    private final ExtendedContextEditingInterface cxt;

    public ContextListenerSupport(ExtendedContextEditingInterface cxt) {
        super();
        this.cxt = cxt;
        clearPostponedStructureChangeFlag();
    }

    public synchronized void addContextListener(ContextListener lst) {
        getListeners().add(lst);
    }

    public synchronized boolean hasListener(ContextListener lst) {
        if (null == listeners) {
            return false;
        }
        return getListeners().contains(lst);
    }


    interface EventBinder {
        void fireEventForCollection(List events);

    }

    abstract static class ContextListenerEventBinder implements EventBinder {
        public void fireEventForCollection(List targets) {
            EventObject evt = getEvent();
            for (int i = 0; i < targets.size(); i++) {
                fireEventFor((ContextListener) targets.get(i), evt);
            }
        }

        protected EventObject getEvent() {
            return null;
        }

        protected abstract void fireEventFor(ContextListener listener, EventObject evt);

    }

    protected void eventFireHelper(EventBinder binder) {
        List targets;
        synchronized (this) {
            if (null == listeners) {
                return;
            }
            targets = (List) listeners.clone();
        }
        binder.fireEventForCollection(targets);
    }

    private boolean structureChangePostponed;

    /**
     * @test_public
     */
    public boolean hasStructureChangePostponed() {
        return structureChangePostponed;
    }

    public void madePostponedStructureChange() {
        structureChangePostponed = true;
    }


    protected void clearPostponedStructureChangeFlag() {
        structureChangePostponed = false;
    }

    public void realisePostponedStructureChange() {
        if (structureChangePostponed) {
            fireContextStructureChanged();
            Assert.isTrue(!structureChangePostponed);
        }
    }


    public void fireContextStructureChanged() {
        eventFireHelper(new ContextListenerEventBinder() {
            protected void fireEventFor(ContextListener listener, EventObject evt) {
                listener.contextStructureChanged();
            }
        });
        clearPostponedStructureChangeFlag();
    }

    public void fireContextTransposed() {
        eventFireHelper(new ContextListenerEventBinder() {
            protected void fireEventFor(ContextListener listener, EventObject evt) {
                listener.contextTransposed();
            }
        });
    }


    /**
     * Insert the method's description here.
     * Creation date: (19.04.01 23:01:37)
     */
    public void fireRelationChanged() {
        eventFireHelper(new ContextListenerEventBinder() {
            protected void fireEventFor(ContextListener listener, EventObject evt) {
                listener.relationChanged();
            }
        });
    }

    public void fireObjectNameChanged(final PropertyChangeEvent changeEvt) {
        eventFireHelper(new ContextListenerEventBinder() {
            protected EventObject getEvent() {
                return changeEvt;
            }

            protected void fireEventFor(ContextListener listener, EventObject evt) {
                listener.objectNameChanged((PropertyChangeEvent) evt);
            }
        });
    }

    public void fireAttributeNameChanged(final PropertyChangeEvent changeEvt) {
        eventFireHelper(new ContextListenerEventBinder() {
            protected EventObject getEvent() {
                return changeEvt;
            }

            protected void fireEventFor(ContextListener listener, EventObject evt) {
                listener.attributeNameChanged((PropertyChangeEvent) evt);
            }
        });
    }

    /**
     * Insert the method's description here.
     * Creation date: (19.04.01 23:02:41)
     *
     * @return java.util.ArrayList
     */
    protected synchronized List getListeners() {
        if (null == listeners) {
            listeners = new ArrayList();
        }
        return listeners;
    }

    /**
     * Insert the method's description here.
     * Creation date: (19.04.01 23:05:21)
     *
     * @param lst conexp.core.ContextListener
     */
    public synchronized void removeContextListener(ContextListener lst) {
        if (null == listeners) {
            return;
        }
        listeners.remove(lst);
    }

    static class AttributeEventBinder extends ContextListenerEventBinder {
        protected void fireEventFor(ContextListener listener, EventObject evt) {
            listener.attributeChanged((ContextChangeEvent) evt);
        }
    }

    public void fireAttributeInserted(final int attrIndex) {
        eventFireHelper(new AttributeEventBinder() {
            protected EventObject getEvent() {
                return ContextChangeEvent.makeAttributeInsertedEvent(cxt, attrIndex);
            }
        });
    }

    public void fireAttributeRemoved(final int attrIndex) {
        eventFireHelper(new AttributeEventBinder() {
            protected EventObject getEvent() {
                return ContextChangeEvent.makeAttributeRemovedEvent(cxt, attrIndex);
            }
        });
    }

    static class ObjectEventBinder extends ContextListenerEventBinder {
        protected void fireEventFor(ContextListener listener, EventObject evt) {
            listener.objectChanged((ContextChangeEvent) evt);
        }
    }


    public void fireObjectInserted(final int objIndex) {
        eventFireHelper(new ObjectEventBinder() {
            protected EventObject getEvent() {
                return ContextChangeEvent.makeObjectInsertedEvent(cxt, objIndex);
            }
        });
    }

    public void fireObjectRemoved(final int objIndex) {
        eventFireHelper(new ObjectEventBinder() {
            protected EventObject getEvent() {
                return ContextChangeEvent.makeObjectRemovedEvent(cxt, objIndex);
            }
        });
    }

}

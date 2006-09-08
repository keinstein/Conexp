/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend;

import com.visibleworkings.trace.Trace;
import util.PropertyChangeSupplier;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;

public class PropertyChangeBaseController implements PropertyChangeListener {
    public PropertyChangeBaseController() {
    }

    protected PropertyChangeSupplier getEventSupplier() {
        return eventSupplier;
    }

    private PropertyChangeSupplier eventSupplier;

    protected void setEventSupplier(PropertyChangeSupplier supplier) {
        if (this.eventSupplier != null) {
            unregisterSupplierListeners();
        }
        this.eventSupplier = supplier;
        if (this.eventSupplier != null) {
            registerSupplierListeners();
        }
    }

    protected void registerSupplierListeners() {
        eventSupplier.addPropertyChangeListener(this);
    }

    protected void unregisterSupplierListeners() {
        eventSupplier.removePropertyChangeListener(this);
    }


    private Map eventProcessorMap = null;

    private synchronized Map getEventProcessorMap() {
        if (null == eventProcessorMap) {
            eventProcessorMap = new HashMap();
        }
        return eventProcessorMap;
    }

    public void registerEventProcessor(String eventName, EventProcessor processor) {
        util.Assert.isTrue(!getEventProcessorMap().containsKey(eventName));
        util.Assert.isTrue(processor != null, "processor shouldn't be null");
        getEventProcessorMap().put(eventName, processor);
    }


    public void removeEventProcessor(String eventName) {
        util.Assert.isTrue(null != eventName);
        if (null == eventProcessorMap) {
            Trace.gui.warningm("Call to remove unregistered implicationsContextListener");
            return;
        }
        getEventProcessorMap().remove(eventName);

    }

    /**
     * @test_public
     */
    public EventProcessor getEventProcessorForEvent(PropertyChangeEvent evt) {
        util.Assert.isTrue(evt != null);
        if (null == eventProcessorMap) {
            return null;
        }
        return (EventProcessor) getEventProcessorMap().get(evt.getPropertyName());
    }


    public void propertyChange(PropertyChangeEvent evt) {
        EventProcessor processor = getEventProcessorForEvent(evt);
        if (processor != null) {
            applyEventProcessorToListeners(evt, processor);
        }
    }

    protected void applyEventProcessorToListeners(PropertyChangeEvent evt, EventProcessor processor) {
//        System.out.println("PropertyChangeBaseController.applyEventProcessorToListeners "+evt.getPropertyName());
        eventBroadcaster.applyEventProcessorToListeners(evt, processor);
    }

    protected void setEventBroadcaster(EventBroadcaster eventBroadcaster) {
        this.eventBroadcaster = eventBroadcaster;
    }

    protected EventBroadcaster getEventBroadcaster() {
        return eventBroadcaster;
    }

    private EventBroadcaster eventBroadcaster;
}

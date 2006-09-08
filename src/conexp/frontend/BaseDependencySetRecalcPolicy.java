/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend;

import java.beans.PropertyChangeEvent;
import java.util.TooManyListenersException;

public abstract class BaseDependencySetRecalcPolicy extends PropertyChangeBaseController {

    protected BaseDependencySetRecalcPolicy(DependencySetSupplier supplier) {
        super();
        setEventBroadcaster(new MultipleTargetsEventBroadcaster());
        registerEventProcessors();
        setEventSupplier(supplier);
    }

    protected abstract void registerEventProcessors();

    public boolean hasTargets() {
        return getEventBroadcaster().hasTargets();
    }

    public void addDependencySetConsumer(DependencySetConsumer consumer) throws TooManyListenersException {
        getEventBroadcaster().addTarget(consumer);
    }

    private DependencySetSupplier getDependencySetSupplier() {
        return (DependencySetSupplier) getEventSupplier();
    }


    protected DependencySetEventProcessorAdapter updateDependencySetAction = new DependencySetEventProcessorAdapter() {
        public void processEventForDependencySetConsumer(PropertyChangeEvent event, DependencySetConsumer consumer) {
            consumer.setDependencySetSupplier(getDependencySetSupplier());
        }
    };

}

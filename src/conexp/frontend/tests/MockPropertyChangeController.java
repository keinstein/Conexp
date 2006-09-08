/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.tests;

import com.mockobjects.ExpectationCounter;
import conexp.frontend.EventProcessor;
import conexp.frontend.OneTargetEventBroadcaster;
import conexp.frontend.PropertyChangeBaseController;
import util.PropertyChangeSupplier;

import java.beans.PropertyChangeEvent;
import java.util.TooManyListenersException;


public class MockPropertyChangeController extends PropertyChangeBaseController {
    public MockPropertyChangeController(PropertyChangeSupplier doc, String propertyName) {
        super();
        setEventSupplier(doc);
        setEventBroadcaster(new OneTargetEventBroadcaster());
        registerEventProcessor(propertyName, new EventProcessor() {
            public void processEventForTarget(PropertyChangeEvent event, Object target) {
                doProcessEventForTarget();
            }
        });
    }

    ExpectationCounter counter = new ExpectationCounter("MockPropertyChangeController expected number of calls");

    private void doProcessEventForTarget() {
        counter.inc();
    }

    public void setExpected(int expectedCalls) {
        counter.setExpected(expectedCalls);
    }

    public void verify() {
        counter.verify();
    }

    public void addTarget(Object target) throws TooManyListenersException {
        getEventBroadcaster().addTarget(target);
    }

    public void removeTarget(Object target) {
        getEventBroadcaster().removeTarget(target);
    }
}

/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.tests;

import conexp.frontend.EventProcessor;
import conexp.frontend.LatticeCalculator;
import conexp.frontend.PropertyChangeBaseController;
import junit.framework.TestCase;

import java.beans.PropertyChangeEvent;
import java.util.TooManyListenersException;

public class PropertyChangeSupplierBaseControllerTest extends TestCase {

    public static void testCallOfPropertyChangeController() {

        MockPropertyChangeSupplier supplier = new MockPropertyChangeSupplier();


        MockPropertyChangeController controller = new MockPropertyChangeController(supplier, LatticeCalculator.LATTICE_DRAWING_CHANGED) {
            protected void applyEventProcessorToListeners(PropertyChangeEvent evt, EventProcessor processor) {
                counter.inc();
            }
        };


        assertEquals(true, supplier.hasListeners(LatticeCalculator.LATTICE_DRAWING_CHANGED));
        controller.setExpected(1);
        supplier.firePropertyChange(LatticeCalculator.LATTICE_DRAWING_CHANGED);
        controller.verify();

        controller.setExpected(0);

        final String propertyTwo = "Not " + LatticeCalculator.LATTICE_DRAWING_CHANGED;
        assertNull(controller.getEventProcessorForEvent(supplier.makePropertyChangeEvent(propertyTwo)));

        supplier.firePropertyChange(propertyTwo);
        controller.verify();
    }

    public static void testWithTargetsController() {
        MockPropertyChangeSupplier supplier = new MockPropertyChangeSupplier();


        MockPropertyChangeController controller = new MockPropertyChangeController(supplier, LatticeCalculator.LATTICE_DRAWING_CHANGED);

        assertEquals(true, supplier.hasListeners(LatticeCalculator.LATTICE_DRAWING_CHANGED));


        controller.setExpected(0);
        supplier.firePropertyChange(LatticeCalculator.LATTICE_DRAWING_CHANGED);
        controller.verify();

        controller.setExpected(1);
        try {
            controller.addTarget(new Object());
        } catch (TooManyListenersException ex) {
            fail();
        }
        supplier.firePropertyChange(LatticeCalculator.LATTICE_DRAWING_CHANGED);
        controller.verify();

    }

    public void testRegisterEventProcessor() {
        PropertyChangeBaseController controller = new PropertyChangeBaseController() {
            protected void registerSupplierListeners() {
            }

            protected void unregisterSupplierListeners() {
            }
        };

        class EmptyEventProcessor implements EventProcessor {
            public void processEventForTarget(PropertyChangeEvent event, Object target) {
            }
        }

        EventProcessor processor = new EmptyEventProcessor();
        EventProcessor secProcessor = new EmptyEventProcessor();

        controller.registerEventProcessor("ONE", processor);
        try {
            controller.registerEventProcessor("ONE", secProcessor);
            fail("For one event only one processor can be set");
        } catch (Throwable ex) {
            assertTrue(true);
        }
        controller.removeEventProcessor("ONE");
        controller.registerEventProcessor("ONE", secProcessor);
        assertTrue("should register after removal", true);
    }

}

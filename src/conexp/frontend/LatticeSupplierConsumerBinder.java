/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend;

import conexp.frontend.latticeeditor.LatticeDrawing;

import java.beans.PropertyChangeEvent;
import java.util.TooManyListenersException;

public class LatticeSupplierConsumerBinder extends PropertyChangeBaseController {
    public LatticeDrawingProvider getLatticeSupplier() {
        return (LatticeDrawingProvider) getEventSupplier();
    }


    public LatticeSupplierConsumerBinder(LatticeDrawingProvider latticeSupplier) {
        super();
        setEventBroadcaster(new OneTargetEventBroadcaster());
        registerEventProcessors();
        setEventSupplier(latticeSupplier);
    }

    public void addLatticeConsumer(ConceptSetDrawingConsumer container) throws TooManyListenersException {
        getEventBroadcaster().addTarget(container);
    }

    public boolean hasTargets() {
        return getEventBroadcaster().hasTargets();
    }

    private LatticeConsumerEventProcessorAdapter setLatticeFromDocumentToContainer = new LatticeConsumerEventProcessorAdapter() {
        public void processEventForLatticeConsumer(PropertyChangeEvent event, ConceptSetDrawingConsumer container) {
            container.setConceptSetDrawing((LatticeDrawing) event.getNewValue());
        }
    };

    private LatticeConsumerEventProcessorAdapter clearLattice = new LatticeConsumerEventProcessorAdapter() {
        public void processEventForLatticeConsumer(PropertyChangeEvent event, ConceptSetDrawingConsumer container) {
            container.clearConceptSetDrawing();
        }
    };

    private void registerEventProcessors() {
        registerEventProcessor(LatticeCalculator.LATTICE_DRAWING_CHANGED, setLatticeFromDocumentToContainer);
        registerEventProcessor(LatticeCalculator.LATTICE_CLEARED, clearLattice);
    }
}

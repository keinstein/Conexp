/*
 * User: sergey
 * Date: Nov 9, 2001
 * Time: 9:02:24 PM
 */
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

    LatticeConsumerEventProcessorAdapter setLatticeFromDocumentToContainer = new LatticeConsumerEventProcessorAdapter() {
        public void processEventForLatticeConsumer(PropertyChangeEvent event, ConceptSetDrawingConsumer container) {
            container.setConceptSetDrawing((LatticeDrawing) event.getNewValue());
        }
    };

    LatticeConsumerEventProcessorAdapter clearLattice = new LatticeConsumerEventProcessorAdapter() {
        public void processEventForLatticeConsumer(PropertyChangeEvent event, ConceptSetDrawingConsumer container) {
            container.clearConceptSetDrawing();
        }
    };

    protected void registerEventProcessors() {
        registerEventProcessor(LatticeCalculator.LATTICE_DRAWING_CHANGED, setLatticeFromDocumentToContainer);
        registerEventProcessor(LatticeCalculator.LATTICE_CLEARED, clearLattice);
    }
}

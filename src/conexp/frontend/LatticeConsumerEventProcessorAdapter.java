/*
 * Created by IntelliJ IDEA.
 * User: sergey
 * Date: Nov 16, 2001
 * Time: 11:53:44 PM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package conexp.frontend;

import java.beans.PropertyChangeEvent;

public abstract class LatticeConsumerEventProcessorAdapter implements EventProcessor, LatticeConsumerEventProcessor {
    public void processEventForTarget(PropertyChangeEvent event, Object target) {
        processEventForLatticeConsumer(event, (ConceptSetDrawingConsumer) target);
    }
}

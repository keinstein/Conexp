/*
 * Created by IntelliJ IDEA.
 * User: sergey
 * Date: Nov 17, 2001
 * Time: 7:17:05 PM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package conexp.frontend;

import java.beans.PropertyChangeEvent;

public abstract class DependencySetEventProcessorAdapter implements EventProcessor, DependencySetEventProcessor {
    public void processEventForTarget(PropertyChangeEvent event, Object target) {
        processEventForDependencySetConsumer(event, (DependencySetConsumer) target);
    }
}

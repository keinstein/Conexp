package conexp.frontend;

import java.beans.PropertyChangeEvent;

public interface DependencySetEventProcessor {
    void processEventForDependencySetConsumer(PropertyChangeEvent event, DependencySetConsumer container);
}

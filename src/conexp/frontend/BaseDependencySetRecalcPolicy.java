/*
 * Created by IntelliJ IDEA.
 * User: sergey
 * Date: Nov 9, 2001
 * Time: 9:02:24 PM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
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

    protected DependencySetSupplier getDependencySetSupplier() {
        return (DependencySetSupplier) getEventSupplier();
    }


    protected DependencySetEventProcessorAdapter updateDependencySetAction = new DependencySetEventProcessorAdapter() {
        public void processEventForDependencySetConsumer(PropertyChangeEvent event, DependencySetConsumer consumer) {
            consumer.setDependencySet(getDependencySetSupplier().getDependencySet());
        }
    };

}

/*
 * Created by IntelliJ IDEA.
 * User: sergey
 * Date: Nov 9, 2001
 * Time: 8:29:50 PM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
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
                doProcessEventForTarget(event, target);
            }
        });
    }

    ExpectationCounter counter = new ExpectationCounter("MockPropertyChangeController expected number of calls");

    protected void doProcessEventForTarget(PropertyChangeEvent evt, Object target) {
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

/*
 * Created by IntelliJ IDEA.
 * User: sergey
 * Date: Nov 16, 2001
 * Time: 6:35:51 PM
 * To change template for new interface use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package conexp.frontend;

import java.beans.PropertyChangeEvent;

public interface EventProcessor {
    void processEventForTarget(PropertyChangeEvent event, Object target);
}

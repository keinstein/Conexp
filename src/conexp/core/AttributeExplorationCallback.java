/*
 * Created by IntelliJ IDEA.
 * User: sergey
 * Date: Dec 1, 2001
 * Time: 12:46:19 AM
 * To change template for new interface use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package conexp.core;

public interface AttributeExplorationCallback {
    int STOP = -1;
    int REJECT = 0;
    int ACCEPT = 1;

    int acceptImplication(Set premise, Set conclusion);
}


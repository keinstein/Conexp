/*
 * Created by IntelliJ IDEA.
 * User: sergey
 * Date: Dec 13, 2001
 * Time: 8:53:06 PM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package conexp.frontend.util;


import javax.swing.*;

public class ActionChainUtil {

    public static void putActions(ActionMap actionMap, Action[] actions) {
        if (null != actions) {
            for (int i = actions.length; --i >= 0;) {
                actionMap.put(actions[i].getValue(Action.NAME), actions[i]);
            }
        }
    }
}

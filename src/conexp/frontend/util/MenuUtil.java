/*
 * Created by IntelliJ IDEA.
 * User: Serhiy Yevtushenko
 * Date: May 19, 2002
 * Time: 2:01:29 PM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package conexp.frontend.util;

import javax.swing.*;
import java.awt.Component;

public class MenuUtil {
    public static int findIndexOfMenuComponentWithName(JMenu menu, String name) {
        int count = menu.getMenuComponentCount();
        for (int i = 0; i < count; i++) {
            final Component menuComponent = menu.getMenuComponent(i);
            if (name.equals(menuComponent.getName())) {
                return i;
            }
        }
        return -1;
    }
}

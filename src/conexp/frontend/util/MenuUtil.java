/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.util;

import javax.swing.JMenu;
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

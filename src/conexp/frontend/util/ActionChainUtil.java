/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



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

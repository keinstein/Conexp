/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend;

import javax.swing.*;
import java.awt.Component;

public interface Document {
    void addViewChangeListener(ViewChangeListener optionPaneViewChangeListener);

    void removeViewChangeListener(ViewChangeListener optionPaneViewChangeListener);

    void setParentActionChain(ActionMap actionChain);

    void activateViews();

    Component getDocComponent();

    JToolBar getToolBar();
}

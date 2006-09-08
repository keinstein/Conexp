/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend;

import javax.swing.ActionMap;
import javax.swing.JToolBar;
import javax.swing.JTree;
import java.awt.Component;

public interface Document {
    void addViewChangeListener(ViewChangeListener optionPaneViewChangeListener);

    void removeViewChangeListener(ViewChangeListener optionPaneViewChangeListener);

    void setParentActionChain(ActionMap actionChain);

    void activateViews();

    Component getDocComponent();

    JToolBar getToolBar();

    void setFileName(String fileName);

    JTree getTree();

    boolean isModified();

    void markDirty();

    void markClean();
}

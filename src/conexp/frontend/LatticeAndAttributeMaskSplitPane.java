/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend;

import conexp.frontend.components.AttributeMaskScrollPane;
import conexp.frontend.components.LatticeComponent;
import conexp.frontend.latticeeditor.LatticePainterPanel;
import conexp.frontend.util.IResourceManager;
import util.gui.JSplitPaneWithFixedRightPane;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;
import java.util.TooManyListenersException;

public class LatticeAndAttributeMaskSplitPane extends JSplitPaneWithFixedRightPane implements ViewChangeInterfaceWithConfig {

    private LatticePainterPanel latticePanel;

    public LatticeAndAttributeMaskSplitPane(LatticeComponent latticeSupplier, ActionMap parentActionChain) {
        super();


        latticePanel = new LatticePainterPanel(latticeSupplier);
        latticePanel.setParentActionMap(parentActionChain);
        setLeftComponent(new JScrollPane(latticePanel));

        JScrollPane rightPanel = new AttributeMaskScrollPane(latticeSupplier.getAttributeMask());

        final Dimension preferredSize = new Dimension(SizeOptions.getProjectPaneWidth(), SizeOptions.getMainFrameHeight());
        rightPanel.setPreferredSize(preferredSize);
        rightPanel.setMaximumSize(preferredSize);
        rightPanel.setMinimumSize(preferredSize);
        setRightComponent(rightPanel);


        LatticeSupplierConsumerBinder binder = new LatticeSupplierConsumerBinder(latticeSupplier);
        try {
            binder.addLatticeConsumer(latticePanel);
        } catch (TooManyListenersException ex) {
            util.Assert.isTrue(false, "this can't happen");
        }
    }

    public Component getViewComponent() {
        return this;
    }

    public Action[] getActions() {
        return latticePanel.getActions();
    }

    public JComponent getViewOptions() {
        return latticePanel.getViewOptions();
    }

    public void initialUpdate() {
        latticePanel.initialUpdate();
    }

    /* @deprecated*/
    public ResourceBundle getResources() {
        return latticePanel.getResources();
    }

    public IResourceManager getResourceManager() {
        return latticePanel.getResourceManager();
    }

    public ActionMap getActionChain() {
        return latticePanel.getActionChain();
    }

}

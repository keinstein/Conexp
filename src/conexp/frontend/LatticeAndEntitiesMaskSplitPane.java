/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend;

import conexp.frontend.components.EntitiesMaskScrollPane;
import conexp.frontend.components.LatticeComponent;
import conexp.frontend.latticeeditor.LatticePainterPanel;
import conexp.frontend.util.IResourceManager;
import util.gui.JSplitPaneWithFixedRightPane;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.util.TooManyListenersException;

public class LatticeAndEntitiesMaskSplitPane extends JSplitPaneWithFixedRightPane implements ViewChangeInterfaceWithConfig {

    private LatticePainterPanel latticePanel;

    public LatticeAndEntitiesMaskSplitPane(LatticeComponent latticeSupplier, ActionMap parentActionChain) {
        super();


        latticePanel = new LatticePainterPanel(latticeSupplier);
        latticePanel.setParentActionMap(parentActionChain);
        setLeftComponent(new JScrollPane(latticePanel));

        JComponent rightPanel = makeAttributeSelectionPane(latticeSupplier);

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

    private JComponent makeAttributeSelectionPane(LatticeComponent latticeSupplier) {

        JSplitPane ret  = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

        ret.setOneTouchExpandable(true);
        ret.setResizeWeight(0.5);
        //todo: remove duplication

        final SetProvidingEntitiesMask attributeMask = latticeSupplier.getAttributeMask();
        JPanel attributeSelectionPane = new JPanel(new BorderLayout());
        attributeSelectionPane.add(new EntitiesMaskScrollPane(attributeMask), BorderLayout.CENTER);
        final JButton button = new JButton("Select all attributes");
        button.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                attributeMask.selectAll();
            }
        });
        attributeSelectionPane.add(button, BorderLayout.SOUTH);

        ret.setTopComponent(attributeSelectionPane);


        final SetProvidingEntitiesMask objectMask = latticeSupplier.getObjectMask();
        JPanel objectSelectionPane = new JPanel(new BorderLayout());
        objectSelectionPane.add(new EntitiesMaskScrollPane(objectMask), BorderLayout.CENTER);
        final JButton selectAllObjectsButton = new JButton("Select all objects");
        selectAllObjectsButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                objectMask.selectAll();
            }
        });
        objectSelectionPane.add(selectAllObjectsButton, BorderLayout.SOUTH);

        ret.setBottomComponent(objectSelectionPane);
        return ret;
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

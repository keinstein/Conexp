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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ResourceBundle;
import java.util.TooManyListenersException;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

public class LatticeAndAttributeMaskSplitPane extends JSplitPaneWithFixedRightPane implements ViewChangeInterfaceWithConfig {

    private LatticePainterPanel latticePanel;

    public LatticeAndAttributeMaskSplitPane(LatticeComponent latticeSupplier, ActionMap parentActionChain) {
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
        JPanel ret = new JPanel(new BorderLayout());
        final SetProvidingAttributeMask attributeMask = latticeSupplier.getAttributeMask();
        ret.add(new AttributeMaskScrollPane(attributeMask), BorderLayout.CENTER);
        final JButton button = new JButton("Select all");
/*
        button.setEnabled(attributeMask.hasUnselectedAttributes());
        attributeMask.addPropertyChangeListener(new PropertyChangeListener(){
            public void propertyChange(PropertyChangeEvent evt) {
                 if(evt.getPropertyName().equals(AttributeMask.ATTRIBUTE_SELECTION_CHANGED)){
                     button.setEnabled(attributeMask.hasUnselectedAttributes());
                 }
            }
        });
*/
        button.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                attributeMask.selectAll();
            }
        });
        ret.add(button, BorderLayout.SOUTH);
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

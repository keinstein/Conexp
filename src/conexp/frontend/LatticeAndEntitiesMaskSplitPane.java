/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend;

import conexp.frontend.components.EntitiesMaskScrollPane;
import conexp.frontend.components.LatticeComponent;
import conexp.frontend.latticeeditor.LatticePainterPanel;
import conexp.frontend.latticeeditor.BirdviewLatticeView;
import conexp.frontend.util.IResourceManager;
import util.gui.JSplitPaneWithFixedRightPane;

import javax.swing.*;
import java.awt.*;
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
        JSplitPane outer = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        outer.setOneTouchExpandable(true);
        outer.setResizeWeight(0.5);

        final JPanel comp = new JPanel(new BorderLayout());
        BirdviewLatticeView panel = new BirdviewLatticeView(latticeSupplier);
        panel.setFitToSize(true);
        panel.initialUpdate();
        comp.add(panel, BorderLayout.CENTER);

        outer.setTopComponent(comp);

        JSplitPane ret = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        ret.setOneTouchExpandable(true);
        ret.setResizeWeight(0.5);

        ret.setTopComponent(buildMaskEditorPane(latticeSupplier.getAttributeMask(), "Select all attributes"));
        ret.setBottomComponent(buildMaskEditorPane(latticeSupplier.getObjectMask(), "Select all objects"));
        outer.setBottomComponent(ret);

        return outer;
    }

    private JPanel buildMaskEditorPane(final SetProvidingEntitiesMask entitiesMask, String selectAllText) {
        JPanel attributeSelectionPane = new JPanel(new BorderLayout());
        attributeSelectionPane.add(new EntitiesMaskScrollPane(entitiesMask), BorderLayout.CENTER);
        final JButton button = new JButton(selectAllText);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                entitiesMask.selectAll();
            }
        });
        attributeSelectionPane.add(button, BorderLayout.SOUTH);
        return attributeSelectionPane;
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

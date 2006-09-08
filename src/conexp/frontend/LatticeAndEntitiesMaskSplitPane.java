/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend;

import conexp.frontend.components.EntitiesMaskScrollPane;
import conexp.frontend.components.LatticeSupplier;
import conexp.frontend.latticeeditor.LatticePainterPanel;
import conexp.frontend.util.IResourceManager;
import util.Assert;
import util.StringUtil;
import util.gui.JSplitPaneWithFixedRightPane;

import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.util.TooManyListenersException;

public class LatticeAndEntitiesMaskSplitPane extends JSplitPaneWithFixedRightPane implements ViewChangeInterfaceWithConfig {

    private LatticePainterPanel latticePanel;

    public LatticePainterPanel getInnerComponent() {
        return latticePanel;
    }

    public LatticeAndEntitiesMaskSplitPane(LatticeSupplier latticeSupplier, ActionMap parentActionChain) {
        super();
        latticePanel = LatticePainterPanel.createLatticePainterPanel(latticeSupplier);
        latticePanel.setParentActionMap(parentActionChain);
        setLeftComponent(new JScrollPane(latticePanel));

        JComponent rightPanel = makeEntitiesSelectionPane(latticeSupplier);

        final Dimension preferredSize = new Dimension(SizeOptions.getProjectPaneWidth(), SizeOptions.getMainFrameHeight());
        rightPanel.setPreferredSize(preferredSize);
        rightPanel.setMaximumSize(preferredSize);
        rightPanel.setMinimumSize(preferredSize);
        setRightComponent(rightPanel);

        LatticeSupplierConsumerBinder binder = new LatticeSupplierConsumerBinder(latticeSupplier);
        try {
            binder.addLatticeConsumer(latticePanel);
        } catch (TooManyListenersException ex) {
            Assert.isTrue(false, "this can't happen");
        }
    }

    public String toString() {
        return StringUtil.extractClassName(getClass().getName()) + "[latticePanel.Id=" + System.identityHashCode(latticePanel) + "]";
    }

    private JComponent makeEntitiesSelectionPane(LatticeSupplier latticeSupplier) {
        /*       JSplitPane outer = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
               outer.setOneTouchExpandable(true);
               outer.setResizeWeight(0.5);

               final JPanel comp = new JPanel(new BorderLayout());

               BirdviewLatticeView panel = new BirdviewLatticeView(latticeSupplier);
               panel.setFitToSize(true);
               panel.initialUpdate();
               comp.add(panel, BorderLayout.CENTER);

               outer.setTopComponent(comp);

       */
        JSplitPane ret = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        ret.setOneTouchExpandable(true);
        ret.setResizeWeight(0.5);

        ret.setTopComponent(buildMaskEditorPane(latticeSupplier.getAttributeMask(), "Select all attributes"));
        ret.setBottomComponent(buildMaskEditorPane(latticeSupplier.getObjectMask(), "Select all objects"));
//        outer.setBottomComponent(ret);

//        return outer;
        return ret;
    }

    private JPanel buildMaskEditorPane(final SetProvidingEntitiesMask entitiesMask, String selectAllText) {
        JPanel entitySelectionPane = new JPanel(new BorderLayout());
        entitySelectionPane.add(new EntitiesMaskScrollPane(entitiesMask), BorderLayout.CENTER);
        final JButton button = new JButton(selectAllText);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                entitiesMask.selectAll();
            }
        });
        entitySelectionPane.add(button, BorderLayout.SOUTH);
        return entitySelectionPane;
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

    public void restorePreferences() {
        latticePanel.restorePreferences();
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

/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.attributeexploration;

import conexp.core.*;
import conexp.core.attrexplorationimpl.AttributeExplorerImplementation;
import conexp.frontend.contexteditor.ContextTablePane;
import util.Assert;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ContrExampleInputDialog extends JDialog {
    LocalizedMessageSupplier localizedMessageSupplier;

    protected LocalizedMessageSupplier getLocalizedMessageSupplier() {
        return localizedMessageSupplier;
    }

    protected String getLocalizedMessage(String key) {
        return getLocalizedMessageSupplier().getMessage(key);
    }

    public ContrExampleInputDialog(Frame owner, String title, LocalizedMessageSupplier supplier) {
        super(owner, title, true);
        this.localizedMessageSupplier = supplier;
        initComponents();
    }


    ExtendedContextEditingInterface tmp;

    ContextTablePane contextEditor;

    int retCode = AttributeExplorerImplementation.AttributeExplorerUserCallback.ACCEPT_IMPLICATION;

    void initComponents() {


        JPanel inputPanel = new JPanel();
        JPanel TablePlaceHolder = new JPanel();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                setRetCodeAndCloseDialog(AttributeExplorerImplementation.AttributeExplorerUserCallback.ACCEPT_IMPLICATION);
            }
        });


        inputPanel.setLayout(new java.awt.GridLayout(1, 0));

        tmp = FCAEngineRegistry.makeContext(1, 1);

        TablePlaceHolder.setLayout(new BorderLayout());

        contextEditor = new ContextTablePane(tmp);
        TablePlaceHolder.add(contextEditor, BorderLayout.CENTER);

        inputPanel.add(TablePlaceHolder);

        final int width = 500;
        inputPanel.setMinimumSize(new Dimension(width, 100));
        inputPanel.setPreferredSize(new Dimension(width, 100));

        getContentPane().add(inputPanel, java.awt.BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();


        JButton rejectButton = new JButton();
        rejectButton.setText(getLocalizedMessage("CounterExampleQuery.ConfirmCounterExampleLabel"));
        rejectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setRetCodeAndCloseDialog(AttributeExplorerImplementation.AttributeExplorerUserCallback.HAS_CONTREXAMPLE);
            }
        });
        buttonPanel.add(rejectButton);

        JButton acceptButton = new JButton();
        acceptButton.setText(getLocalizedMessage("CounterExampleQuery.AcceptImplicationLabel"));
        acceptButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setRetCodeAndCloseDialog(AttributeExplorerImplementation.AttributeExplorerUserCallback.ACCEPT_IMPLICATION);
            }
        });
        buttonPanel.add(acceptButton);

        JButton stopButton = new javax.swing.JButton();
        stopButton.setText(getLocalizedMessage("CounterExampleQuery.StopAttributeExplorationLabel"));
        stopButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setRetCodeAndCloseDialog(AttributeExplorerImplementation.AttributeExplorerUserCallback.STOP);
            }
        });
        buttonPanel.add(stopButton);
        final int buttonPanelHeight = 30;

        buttonPanel.setPreferredSize(new Dimension(width, buttonPanelHeight));
        buttonPanel.setMinimumSize(new Dimension(width, buttonPanelHeight));

        getContentPane().add(buttonPanel, java.awt.BorderLayout.SOUTH);

    }

    public int queryContrExample(final AttributeInformationSupplier attrInfo, final ContextEntity object, final ModifiableSet premise) {
        class ContrExampleInputContextEditingInterface implements ContextEditingInterface {
            public ContextEntity getAttribute(int index) {
                return attrInfo.getAttribute(index);
            }

            public int getAttributeCount() {
                return attrInfo.getAttributeCount();
            }

            public ContextEntity getObject(int index) {
                return object;
            }

            public int getObjectCount() {
                return 1;
            }

            public boolean getRelationAt(int objectId, int attrId) {
                Assert.isTrue(objectId == 0);
                return premise.in(attrId);
            }

            public void setRelationAt(int objectId, int attrId, boolean value) {
                Assert.isTrue(objectId == 0);
                if (value) {
                    premise.put(attrId);
                } else {
                    premise.remove(attrId);
                }
            }

            //todo: think about elegant fix

            public ContextEditingInterface makeCopy() {
                return null;
            }

            public void setDimension(int numObj, int numAttr) {
            }

            public void removeAttribute(int index) {
            }

            public void removeObject(int index) {
            }

            public void addContextListener(ContextListener lst) {
            }

            public void removeContextListener(ContextListener lst) {
            }

            public void copyFrom(ContextEditingInterface source) {
            }

            public void addObjectWithNameAndIntent(String objectName, Set intent) {
            }

            public BinaryRelation getRelation() {
                return null;
            }
        }
        ;


        setLocationRelativeTo(getOwner());
        contextEditor.setContext(new ContrExampleInputContextEditingInterface());
        pack();
        setLocationRelativeTo(getOwner());
        setVisible(true);

        return retCode;
    }


    private void setRetCodeAndCloseDialog(int retCode) {
        setVisible(false);
        this.retCode = retCode;
        dispose();
    }

}

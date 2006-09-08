/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.frontend;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;


public class AboutConExpDialog extends JDialog {
    private JEditorPane copyrightTextArea;

    public URL getLicenseUrl() {
        return AboutConExpDialog.class.getResource("resources/AboutConExp.html");
    }


    private JEditorPane getCopyrightAreaPane() {
        if (null == copyrightTextArea) {
            try {
                copyrightTextArea = new JEditorPane(getLicenseUrl());
                copyrightTextArea.setEditable(false);
                copyrightTextArea.setPreferredSize(new Dimension(600, 440));
            } catch (IOException e) {
                System.out.println("License absent. ConExp will exit now ...");
                System.exit(0);
            }
        }
        return copyrightTextArea;
    }

    public AboutConExpDialog(Frame frame, String title, boolean modal) {
        super(frame, title, modal);
        try {
            buildGui();
            pack();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void buildGui() {
        final JButton closeButton = new JButton();
        closeButton.setText("Close");
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        getRootPane().setDefaultButton(closeButton);
        final JPanel buttonPane = new JPanel();

        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.X_AXIS));
        buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        buttonPane.add(Box.createHorizontalGlue());
        buttonPane.add(closeButton);

        final JScrollPane scrollView = new JScrollPane(getCopyrightAreaPane());
/*
        this.setTitle("About Concept Explorer");
*/
        getContentPane().add(scrollView, BorderLayout.CENTER);
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        invalidate();
        pack();
        setBounds(new Rectangle(100, 100, 350, 300));
    }

}

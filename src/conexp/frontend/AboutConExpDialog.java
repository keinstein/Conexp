package conexp.frontend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;

/**
 * Created by IntelliJ IDEA.
 * User: sergey
 * Date: 14/4/2005
 * Time: 0:12:22
 * To change this template use File | Settings | File Templates.
 */
public class AboutConExpDialog extends JDialog{
    JEditorPane copyrightTextArea;

    public URL getLicenseUrl() {
        return AboutConExpDialog.class.getResource("resources/AboutConExp.html");
    }


    JEditorPane getCopyrightAreaPane() {
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

    void buildGui() {
        final JButton closeButton = new JButton();
        closeButton.setText("Close");
        closeButton.addActionListener(new java.awt.event.ActionListener() {
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

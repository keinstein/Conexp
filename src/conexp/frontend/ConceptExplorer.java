/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend;

import com.visibleworkings.trace.Trace;
import com.visibleworkings.trace.TraceController;
import util.StringUtil;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.Toolkit;

public class ConceptExplorer {
    private static java.util.ResourceBundle resConceptExplorer = java.util.ResourceBundle.getBundle("conexp/frontend/resources/ConceptExplorer");  //$NON-NLS-1$

    //Construct the application
    public ConceptExplorer() {

        setupTracer();
        setupLookAndFeel();
        try {
            makeMainFrame();
        } catch (java.lang.Exception ex) {
            //System.out.println(StringUtil.stackTraceToString(ex));
            Trace.gui.errorm(StringUtil.stackTraceToString(ex));
            //application will be running further
        } catch (java.lang.Error er) {
            Trace.gui.errorm(StringUtil.stackTraceToString(er));
            throw er;
        }
    }

    boolean packFrame = false;

    private void makeMainFrame() {
        ConceptFrame frame = new ConceptFrame();

        //Validate frames that have preset sizes
        //Pack frames that have useful preferred size info, e.g. from their layout
        if (packFrame)
            frame.pack();
        else
            frame.validate();

        centerFrameWindow(frame);
        frame.setVisible(true);
    }

    private void setupLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception exc) {
            Trace.gui.errorm(resConceptExplorer.getString("Error_loading_L&F_msg"), exc); //$NON-NLS-1$
        }
    }

    private void setupTracer() {
        TraceController.setProperty("TraceBuffer_default", resConceptExplorer.getString("TraceBuffer_Level")); //$NON-NLS-2$//$NON-NLS-1$
        TraceController.setProperty("TraceLog_default", resConceptExplorer.getString("TraceLog_Level")); //$NON-NLS-2$//$NON-NLS-1$
        if ("on".equalsIgnoreCase(resConceptExplorer.getString("Trace"))) {
            TraceController.start();
        }
    }

    protected static void centerFrameWindow(ConceptFrame frame) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = frame.getSize();
        if (frameSize.height > screenSize.height)
            frameSize.height = screenSize.height;
        if (frameSize.width > screenSize.width)
            frameSize.width = screenSize.width;
        frame.setLocation(
                (screenSize.width - frameSize.width) / 2,
                (screenSize.height - frameSize.height) / 2);
    }

    public static void main(String[] args) {
        new ConceptExplorer();
    }
}

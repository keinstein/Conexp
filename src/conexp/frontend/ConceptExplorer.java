/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend;

import com.visibleworkings.trace.Trace;
import com.visibleworkings.trace.TraceController;
import util.StringUtil;
import util.errorhandling.AppErrorHandler;
import util.errorhandling.ErrorDialogErrorHandler;
import util.gui.WindowUtil;

import javax.swing.UIManager;
import java.util.ResourceBundle;

public class ConceptExplorer {
    private static ResourceBundle resConceptExplorer = ResourceBundle.getBundle("conexp/frontend/resources/ConceptExplorer");  //$NON-NLS-1$

    //Construct the application
    public ConceptExplorer() {

        setupTracer();
        setupLookAndFeel();
        setupErrorHandler();
        ConceptFrame frame = null;
        try {
            frame = makeMainFrame();
        } catch (Exception ex) {
            //System.out.println(StringUtil.stackTraceToString(ex));
            Trace.gui.errorm(StringUtil.stackTraceToString(ex));
            AppErrorHandler.getInstance().notify(frame, ex);
            //application will be running further
        } catch (Error er) {
            Trace.gui.errorm(StringUtil.stackTraceToString(er));
            AppErrorHandler.getInstance().notify(frame, er);
        }
    }

    private static void setupErrorHandler() {
        AppErrorHandler.getInstance().setErrorHandler(new ErrorDialogErrorHandler());
    }

    private boolean packFrame = false;

    private ConceptFrame makeMainFrame() {
        ConceptFrame frame = new ConceptFrame();

        //Validate frames that have preset sizes
        //Pack frames that have useful preferred size info, e.g. from their layout
        if (packFrame) {
            frame.pack();
        } else {
            frame.validate();
        }

        WindowUtil.centerWindow(frame);
        frame.setVisible(true);
        return frame;
    }

    private static void setupLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception exc) {
            Trace.gui.errorm(resConceptExplorer.getString("Error_loading_L&F_msg"), exc); //$NON-NLS-1$
        }
    }

    private static void setupTracer() {
        TraceController.setProperty("TraceBuffer_default", resConceptExplorer.getString("TraceBuffer_Level")); //$NON-NLS-2$//$NON-NLS-1$
        TraceController.setProperty("TraceLog_default", resConceptExplorer.getString("TraceLog_Level")); //$NON-NLS-2$//$NON-NLS-1$
        if ("on".equalsIgnoreCase(resConceptExplorer.getString("Trace"))) {
            TraceController.start();
        }
    }

    public static void main(String[] args) {
        new ConceptExplorer();
    }
}

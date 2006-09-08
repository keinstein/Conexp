/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.io;

import util.DataFormatException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class UIDataFormatErrorHandler extends DataFormatErrorHandlerBase {

    JFrame mainFrame;

    public UIDataFormatErrorHandler(JFrame mainAppWindow) {
        super();
        this.mainFrame = mainAppWindow;
    }

    public void handleUncriticalError(DataFormatException ex) throws DataFormatException {

        int result = JOptionPane.showConfirmDialog(mainFrame, ex.getMessage(), "Error: continue loading?", JOptionPane.YES_NO_OPTION);
        if (result != JOptionPane.YES_OPTION) {
            throw ex;
        }
    }
}

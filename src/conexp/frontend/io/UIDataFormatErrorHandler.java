package conexp.frontend.io;

import util.DataFormatException;

import javax.swing.*;

/**
 * Author: Serhiy Yevtushenko
 * Date: Dec 11, 2002
 * Time: 10:04:56 PM
 */
public class UIDataFormatErrorHandler extends DataFormatErrorHandlerBase {
    public UIDataFormatErrorHandler() {
    }

    public void handleUncriticalError(DataFormatException ex) throws DataFormatException {
        int result =JOptionPane.showConfirmDialog(null, ex.getMessage(), "Error: continue loading?", JOptionPane.YES_NO_OPTION);
        if(result != JOptionPane.YES_OPTION){
            throw ex;
        }
    }
}

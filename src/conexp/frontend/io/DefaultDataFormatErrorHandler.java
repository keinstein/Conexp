package conexp.frontend.io;

import conexp.frontend.DataFormatErrorHandler;
import util.DataFormatException;

/**
 * Author: Serhiy Yevtushenko
 * Date: Dec 11, 2002
 * Time: 8:28:56 PM
 */
public class DefaultDataFormatErrorHandler extends DataFormatErrorHandlerBase {

    public void handleUncriticalError(DataFormatException ex) throws DataFormatException {
        ex.printStackTrace();
    }

    private DefaultDataFormatErrorHandler() {
    }

    private static DataFormatErrorHandler g_Instance = new DefaultDataFormatErrorHandler();

    public static DataFormatErrorHandler getInstance() {
        return g_Instance;
    }
}

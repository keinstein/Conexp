package conexp.frontend.io;

import conexp.frontend.DataFormatErrorHandler;
import util.DataFormatException;

/**
 * Author: Serhiy Yevtushenko
 * Date: Dec 11, 2002
 * Time: 10:03:51 PM
 */
public abstract class DataFormatErrorHandlerBase implements DataFormatErrorHandler {
    public void handleCriticalError(DataFormatException ex) throws DataFormatException {
        throw ex;
    }
}

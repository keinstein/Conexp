package conexp.frontend;

import util.DataFormatException;

/**
 * Author: Serhiy Yevtushenko
 * Date: Dec 11, 2002
 * Time: 8:19:14 PM
 */
public interface DataFormatErrorHandler {
    void handleCriticalError(DataFormatException ex) throws DataFormatException;
    void handleUncriticalError(DataFormatException ex) throws DataFormatException;
}

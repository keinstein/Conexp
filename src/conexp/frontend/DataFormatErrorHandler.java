/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/

package conexp.frontend;

import util.DataFormatException;


public interface DataFormatErrorHandler {
    void handleCriticalError(DataFormatException ex) throws DataFormatException;

    void handleUncriticalError(DataFormatException ex) throws DataFormatException;
}

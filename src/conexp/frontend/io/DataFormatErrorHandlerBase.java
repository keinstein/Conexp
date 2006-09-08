/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.io;

import conexp.frontend.DataFormatErrorHandler;
import util.DataFormatException;


public abstract class DataFormatErrorHandlerBase implements DataFormatErrorHandler {
    public void handleCriticalError(DataFormatException ex) throws DataFormatException {
        throw ex;
    }
}

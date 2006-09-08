/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.io;

import conexp.frontend.DataFormatErrorHandler;
import util.DataFormatException;


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

package conexp.frontend.io;

import conexp.core.Context;

import java.io.Reader;
import java.io.IOException;

import util.DataFormatException;

/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 * Date: 17/7/2003
 * Time: 0:33:40
 */

public interface ContextReader {
    Context parseContext(Reader r) throws IOException, DataFormatException;
}

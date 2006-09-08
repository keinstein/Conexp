/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.frontend.io;

import conexp.core.Context;
import util.DataFormatException;

import java.io.IOException;
import java.io.Reader;



public interface ContextReader {
    Context parseContext(Reader r) throws IOException, DataFormatException;
}

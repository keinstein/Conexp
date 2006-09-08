/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend;

import util.DataFormatException;

import java.io.IOException;
import java.io.Reader;

public interface DocumentLoader {
    ContextDocument loadDocument(Reader reader, DataFormatErrorHandler errorHandler) throws IOException, DataFormatException;
}

/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.io;

import conexp.frontend.ContextDocument;
import conexp.frontend.DataFormatErrorHandler;
import conexp.frontend.DocumentLoader;
import util.DataFormatException;

import java.io.IOException;
import java.io.Reader;


public class ContextReaderAdapter implements DocumentLoader {
    private ContextReader loader;

    public ContextReaderAdapter(ContextReader loader) {
        this.loader = loader;
    }

    public ContextDocument loadDocument(Reader r, DataFormatErrorHandler errorHandler) throws IOException, DataFormatException {
        return new ContextDocument(loader.parseContext(r));
    }
}

/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
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

public class ConImpContextDocumenLoaderAdapter implements DocumentLoader {
    private ContextReader loader;

    public ConImpContextDocumenLoaderAdapter(ContextReader loader) {
        this.loader = loader;
    }

    public ConImpContextDocumenLoaderAdapter() {
        this(new ConImpContextLoader());
    }

    public ContextDocument loadDocument(Reader r, DataFormatErrorHandler errorHandler) throws IOException, DataFormatException {
        return new ContextDocument(loader.parseContext(r));
    }
}

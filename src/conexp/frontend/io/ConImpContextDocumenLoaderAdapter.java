/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
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

    public ContextDocument loadDocument(Reader r, DataFormatErrorHandler errorHandler) throws IOException, DataFormatException {
        ConImpContextLoader loader = new ConImpContextLoader();
        return new ContextDocument(loader.loadContext(r));
    }
}

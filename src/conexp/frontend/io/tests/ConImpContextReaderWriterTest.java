/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.io.tests;

import conexp.frontend.DocumentLoader;
import conexp.frontend.DocumentWriter;
import conexp.frontend.io.ConImpContextDocumenLoaderAdapter;
import conexp.frontend.io.ConImpContextWriter;

public class ConImpContextReaderWriterTest extends ContextReaderWriterPairTest {

    protected DocumentLoader makeDocumentLoader() {
        return new ConImpContextDocumenLoaderAdapter();
    }

    protected DocumentWriter makeDocumentWriter() {
        return new ConImpContextWriter();
    }

}

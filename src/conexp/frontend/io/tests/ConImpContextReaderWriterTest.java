/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.io.tests;

import conexp.frontend.DocumentLoader;
import conexp.frontend.DocumentWriter;
import conexp.frontend.io.ConImpContextLoader;
import conexp.frontend.io.ConImpContextWriter;
import conexp.frontend.io.ContextReaderDocumenLoaderAdapter;

public class ConImpContextReaderWriterTest extends ContextReaderWriterPairTest {

    protected DocumentLoader makeDocumentLoader() {
        return new ContextReaderDocumenLoaderAdapter(new ConImpContextLoader());
    }

    protected DocumentWriter makeDocumentWriter() {
        return new ConImpContextWriter();
    }

}

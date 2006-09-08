/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.io.tests;

import conexp.frontend.DocumentLoader;
import conexp.frontend.DocumentWriter;
import conexp.frontend.io.ContextReaderAdapter;
import conexp.frontend.io.TabSeparatedContextReader;
import conexp.frontend.io.TabSeparatedContextWriter;

public class TabSeparatedReaderWriterTest extends ContextReaderWriterPairTest {

    protected boolean isPreserveObjectNames() {
        return false;
    }

    protected DocumentLoader makeDocumentLoader() {
        return new ContextReaderAdapter(new TabSeparatedContextReader());
    }

    protected DocumentWriter makeDocumentWriter() {
        return new TabSeparatedContextWriter();
    }

}

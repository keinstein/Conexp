/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/

package conexp.frontend.io.tests;

import conexp.frontend.DocumentLoader;
import conexp.frontend.DocumentWriter;
import conexp.frontend.io.ConImpContextDocumenLoaderAdapter;
import conexp.frontend.io.ConImpContextWriter;
import junit.framework.Test;
import junit.framework.TestSuite;

public class ConImpContextReaderWriterTest extends ContextReaderWriterPairTest {
    private static final Class THIS = ConImpContextReaderWriterTest.class;


    public static Test suite() {
        return new TestSuite(THIS);
    }

    protected DocumentLoader makeDocumentLoader() {
        return new ConImpContextDocumenLoaderAdapter();
    }

    protected DocumentWriter makeDocumentWriter() {
        return new ConImpContextWriter();
    }

}

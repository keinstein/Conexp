/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.io.tests;

import conexp.core.Context;
import conexp.core.ContextEntity;
import conexp.core.tests.SetBuilder;
import conexp.frontend.ContextDocument;
import conexp.frontend.DocumentLoader;
import conexp.frontend.DocumentWriter;
import conexp.frontend.io.DefaultDataFormatErrorHandler;
import junit.framework.TestCase;
import util.DataFormatException;
import util.testing.TestUtil;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

public abstract class ContextReaderWriterPairTest extends TestCase {
    public void testReaderWriterForEmptyContext() {
        Context cxt = SetBuilder.makeContext(new int[0][0]);
        doTestWriteAndReadForContext(cxt);
    }

    public void testReaderWriterForContextWithAttributes() {
        Context cxt = SetBuilder.makeContext(new int[0][0]);
        cxt.addAttribute(ContextEntity.createContextAttribute("First"));
        cxt.addAttribute(ContextEntity.createContextAttribute("Second"));
        assertEquals(2, cxt.getAttributeCount());
        assertEquals(0, cxt.getObjectCount());
        doTestWriteAndReadForContext(cxt);
    }

    public void testReaderWriterWithObjectsAndAttributes() {
        Context cxt = SetBuilder.makeContext(new int[][]{{0, 0},
                                                         {0, 0},
                                                         {0, 0}
        });
        cxt.getAttribute(0).setName("First Attr");
        cxt.getObject(2).setName("Third Object");
        doTestWriteAndReadForContext(cxt);
    }

    public void testReaderWriterForFullContext() {
        Context cxt = SetBuilder.makeContext(new int[][]{{0, 1},
                                                         {1, 0},
                                                         {1, 1}
        });
        cxt.getAttribute(0).setName("First Attr");
        cxt.getObject(2).setName("Third Object");
        doTestWriteAndReadForContext(cxt);
    }

    protected void doTestWriteAndReadForContext(Context cxt) {
        ContextDocument doc = new ContextDocument();
        doc.setContext(cxt);

        ContextDocument loadedDoc = writeAndReadContextDoc(doc);

        assertEquals(cxt, loadedDoc.getContext());
    }

    protected ContextDocument writeAndReadContextDoc(ContextDocument doc) {
        StringWriter writer = new StringWriter();
        DocumentWriter docWriter = makeDocumentWriter();
        try {
            docWriter.storeDocument(doc, writer);
        } catch (IOException ex) {
            reportUnexpectedException(ex);
        }
        DocumentLoader reader = makeDocumentLoader();

        ContextDocument loadedDoc = null;
        try {
            loadedDoc = reader.loadDocument(new StringReader(writer.getBuffer().toString()), DefaultDataFormatErrorHandler.getInstance());
        } catch (IOException ex) {
            reportUnexpectedException(ex);
        } catch (DataFormatException ex) {
            reportUnexpectedException(ex);
        }
        return loadedDoc;
    }

    protected abstract DocumentLoader makeDocumentLoader();

    protected abstract DocumentWriter makeDocumentWriter();

    private void reportUnexpectedException(Throwable ex) {
        TestUtil.reportUnexpectedException(ex);
    }
}

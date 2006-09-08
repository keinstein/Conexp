/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.util.tests;

import conexp.frontend.ContextDocument;
import conexp.frontend.DataFormatErrorHandler;
import conexp.frontend.Document;
import conexp.frontend.DocumentLoader;
import conexp.frontend.DocumentWriter;
import conexp.frontend.util.StorageFormatManager;
import conexp.frontend.util.StorageFormatRecord;
import junit.framework.TestCase;
import util.DataFormatException;
import util.gui.fileselector.GenericFileFilter;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

public class StorageFormatManagerTest extends TestCase {
    static class MockDocumentLoader implements DocumentLoader {
        public ContextDocument loadDocument(Reader reader, DataFormatErrorHandler errorHandler) throws IOException, DataFormatException {
            return null;
        }
    }

    static class MockDocumentWriter implements DocumentWriter {
        public void storeDocument(Document document, Writer writer) throws IOException {
        }
    }

    public static void testStorage() {
        StorageFormatManager manager = new StorageFormatManager();
        assertNotNull(manager.getLoadFilters());

        MockDocumentLoader mockDocumentLoader = new MockDocumentLoader();
        StorageFormatRecord format = new StorageFormatRecord("Test format", "cxt", mockDocumentLoader, null);
        assertEquals(".cxt", format.getExtension());
        manager.registerStorageFormat(format);

        GenericFileFilter[] filters = manager.getLoadFilters();
        assertEquals(1, filters.length);
        assertTrue(filters[0].accept(null, "context.cxt"));
        assertEquals(mockDocumentLoader, manager.getLoader(".cxt"));
        assertEquals(mockDocumentLoader, manager.getLoader(".CXT"));

        manager.registerStorageFormat(format);
        assertEquals(1, manager.registeredFormatCount());

        DocumentWriter mockDocumentWriter = new MockDocumentWriter();
        StorageFormatRecord format2 = new StorageFormatRecord("Test format 2", "cex", null, mockDocumentWriter);
        manager.registerStorageFormat(format2);
        assertEquals(2, manager.registeredFormatCount());
        assertEquals(1, manager.getLoadFilters().length);
        assertEquals(mockDocumentLoader, manager.getLoader("cxt"));
        assertEquals(1, manager.getStoreFilters().length);
        assertEquals(mockDocumentWriter, manager.getWriter("cex"));
    }

    public static void testGetDefaultLoaderAndWriter() {
        StorageFormatManager manager = new StorageFormatManager();
        MockDocumentLoader mockDocumentLoader = new MockDocumentLoader();
        StorageFormatRecord format = new StorageFormatRecord("Test format", "cxt", mockDocumentLoader, null);
        manager.registerStorageFormat(format);

        assertSame(mockDocumentLoader, manager.getDefaultLoader());

        MockDocumentWriter firstWriter = new MockDocumentWriter();

        StorageFormatRecord format2 = new StorageFormatRecord("Test write format", "cxt2", null, firstWriter);
        manager.registerStorageFormat(format2);
        assertSame(firstWriter, manager.getDefaultWriter());


        MockDocumentWriter secondWriter = new MockDocumentWriter();
        MockDocumentLoader secondLoader = new MockDocumentLoader();
        StorageFormatRecord format3 = new StorageFormatRecord("Test format 2", "cex", secondLoader, secondWriter);
        manager.registerDefaultStorageFormat(format3);

        assertEquals(2, manager.getLoadFilters().length);
        assertSame(secondLoader, manager.getDefaultLoader());
        assertSame(secondWriter, manager.getDefaultWriter());
        assertEquals(".cex", manager.getDefaultExtension());


    }

}

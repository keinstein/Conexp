/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.tests;

import com.mockobjects.ExpectationCounter;
import conexp.frontend.AppErrorHandler;
import conexp.frontend.ConExpStorageFormatManager;
import conexp.frontend.ContextDocManager;
import conexp.frontend.DocManagerMessages;
import conexp.frontend.DocModifiedHandler;
import conexp.frontend.Document;
import conexp.frontend.DocumentRecord;
import conexp.frontend.ViewChangeListener;
import conexp.tests.TestPathResolver;
import junit.framework.TestCase;
import util.DataFormatException;
import util.StringUtil;
import util.gui.MostRecentUrlListManager;
import util.gui.fileselector.MockFileSelectorService;
import util.testing.SimpleMockPropertyChangeListener;

import javax.swing.ActionMap;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.JTree;
import java.awt.Component;
import java.io.File;
import java.io.IOException;

public class ContextDocManagerTest extends TestCase {
    private ContextDocManager docManager;

    protected void setUp() {
        docManager = makeDocManager();
    }

    public void testLoadDocument() {
        SimpleMockPropertyChangeListener listener = new SimpleMockPropertyChangeListener(DocManagerMessages.ACTIVE_DOC_CHANGED);

        listener.setExpected(1);
        docManager.addPropertyChangeListener(DocManagerMessages.ACTIVE_DOC_CHANGED, listener);
        docManager.createNewDocument();
        listener.verify();
    }

    private static ContextDocManager makeDocManager() {
        ContextDocManager docManager =
                new ContextDocManager(new JFrame(), new MockOptionPaneSupplier());
        docManager.setAppErrorHandler(new MockAppErrorHandler());
        return docManager;
    }


    public void testLoadingOfDocumentWithAbsentLoader() {
        assertTrue(docManager.getStorageFormatManager().isEmpty());
        try {
            docManager.doLoadDocumentFromFile(new File("File.notSupportedExtension"));
        } catch (IOException e) {
            fail("Unexpected exception " + StringUtil.stackTraceToString(e));
        } catch (DataFormatException e) {
            assertTrue("Expect exception about absense of loader", true);
        }
    }

    static class MockDocument implements Document {
        boolean modified = false;

        public void setModified(boolean newModified) {
            this.modified = newModified;
        }

        public void markDirty() {
            setModified(true);

        }

        public void markClean() {
            setModified(false);
        }

        public void addViewChangeListener(ViewChangeListener optionPaneViewChangeListener) {
        }

        public void removeViewChangeListener(ViewChangeListener optionPaneViewChangeListener) {
        }

        public void setParentActionChain(ActionMap actionChain) {
        }

        public void activateViews() {
        }

        public Component getDocComponent() {
            return null;
        }

        public JToolBar getToolBar() {
            return null;
        }

        public JTree getTree() {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        public void setFileName(String fileName) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        public boolean isModified() {
            return modified;
        }
    }

    public void testMruList() {
        //MRU - when file should be added to MRU - after close with write;
        MostRecentUrlListManager mruManager = docManager.getMruManager();
        assertTrue(mruManager.getMostRecentUrlList().isEmpty());
        final String DOC_PATH = "One";
        DocumentRecord firstDocRec = makeDocRecordAndAddItToManager(DOC_PATH, true);
        assertEquals(0, mruManager.getMostRecentUrlList().size());
        docManager.removeDocument(firstDocRec);

        assertTrue(mruManager.getMostRecentUrlList().contains(DOC_PATH));
        assertEquals(1, mruManager.getMostRecentUrlList().size());

        makeDocRecordAndAddItToManager(DOC_PATH, true);
        assertEquals(0, mruManager.getMostRecentUrlList().size());

        //When file should be removed from MRU  - when
    }

    public void testNonAdditionToMRUofDocumentsWithoutCorrespondingFiles() {
        MostRecentUrlListManager mruManager = docManager.getMruManager();
        assertTrue(mruManager.getMostRecentUrlList().isEmpty());
        DocumentRecord docRecord = makeDocRecordAndAddItToManager("", false);
        assertEquals(0, mruManager.getMostRecentUrlList().size());
        docManager.removeDocument(docRecord);
        assertEquals(0, mruManager.getMostRecentUrlList().size());
    }

    private DocumentRecord makeDocRecordAndAddItToManager(final String DOC_PATH, boolean expHasCorrespondingFile) {
        final DocumentRecord firstDocRec = new DocumentRecord(new MockDocument(), DOC_PATH);
        assertEquals(expHasCorrespondingFile, firstDocRec.isPersistent());
        docManager.addDocument(firstDocRec);
        return firstDocRec;
    }

    public void testMenuCreation() {
        ResourcesToolbarDefinitionTestHelper.testMenuDefinitionInResource(ContextDocManager.getResourceManager(), docManager.getActionChain(), false);
        ResourcesToolbarDefinitionTestHelper.testMenuDefinitionInResource(ContextDocManager.getResourceManager(), docManager.getActionChain(), true);
    }


    public void testDocCreation() {
        docManager.createNewDocument();

        assertFalse(docManager.getActiveDoc().isModified());
        assertFalse(docManager.getDocRecord().isPersistent());
    }

    public void testDocLoading() {
        checkJustOpenedDoc();
    }

    private void checkJustOpenedDoc() {
        docManager.setStorageFormatManager(new ConExpStorageFormatManager());
        docManager.openUrl(TestPathResolver.getTestPath("conexp/frontend/resources/tests/docTest.cex"));
        assertNotNull("document was not loaded", docManager.getActiveDoc());
        assertFalse(docManager.getActiveDoc().isModified());
    }

    public void testDocNotModifiedAfterSave() {
        checkJustOpenedDoc();
        docManager.getActiveDoc().markDirty();
        assertTrue(docManager.getActiveDoc().isModified());
        docManager.onSave();
        assertFalse(docManager.getActiveDoc().isModified());
        //todo: test scenario with exception during save
    }


    public void testDocProposedToSaveWhenModifiedAndCreatedNewCancelOption() {
        commonDocCreatedAndNotSavedSetup();
        final Document activeDoc = docManager.getActiveDoc();
        MockDocModifiedHandler handler = createMockDocModifiedHandler(
                JOptionPane.CANCEL_OPTION
        );
        docManager.setDocModifiedHandler(handler);
        docManager.onNewDocument();
        handler.verify();
        assertSame(activeDoc, docManager.getActiveDoc());
    }

    public void testDocProposedToSaveWhenModifiedAndCreatedNewNoOption() {
        commonDocCreatedAndNotSavedSetup();
        final Document activeDoc = docManager.getActiveDoc();

        MockDocModifiedHandler handler = createMockDocModifiedHandler(
                JOptionPane.NO_OPTION);

        docManager.setDocModifiedHandler(handler);
        final MockFileSelectorService fileSelectorService =
                MockFileSelectorService.createNotExpectingToBeCalled();
        docManager.setFileSelectorService(fileSelectorService);

        docManager.onNewDocument();

        handler.verify();
        fileSelectorService.verify();
        assertNotSame(activeDoc, docManager.getActiveDoc());
    }

    public void testDocProposedToSaveWhenModifiedAndCreatedNewYesOption() {
        commonDocCreatedAndNotSavedSetup();
        final Document activeDoc = docManager.getActiveDoc();

        MockDocModifiedHandler handler = createMockDocModifiedHandler(
                JOptionPane.YES_OPTION);

        docManager.setDocModifiedHandler(handler);
        final MockFileSelectorService fileSelectorService =
                MockFileSelectorService.createSaveService(
                        TestPathResolver.getTestPath("conexp/frontend/resources/tests/docSaveTest.cex"),
                        true,
                        true);

        docManager.setFileSelectorService(fileSelectorService);
        docManager.onNewDocument();
        handler.verify();
        fileSelectorService.verify();
        assertNotSame(activeDoc, docManager.getActiveDoc());
    }

    public void testDocProposedToSaveWhenModifiedAndCreatedNewYesOptionNoPathProvided() {
        commonDocCreatedAndNotSavedSetup();
        final Document activeDoc = docManager.getActiveDoc();

        MockDocModifiedHandler handler = createMockDocModifiedHandler(
                JOptionPane.YES_OPTION);

        docManager.setDocModifiedHandler(handler);
        final MockFileSelectorService fileSelectorService =
                MockFileSelectorService.createSaveService(
                        null,
                        false,
                        false);
        docManager.setFileSelectorService(fileSelectorService);
        docManager.onNewDocument();
        handler.verify();
        fileSelectorService.verify();
        assertSame(activeDoc, docManager.getActiveDoc());
    }


    public void testDocProposedToSaveWhenModifiedAndExitYesOptionNoPathProvided() {
        commonDocCreatedAndNotSavedSetup();
        final Document activeDoc = docManager.getActiveDoc();

        MockDocModifiedHandler handler = createMockDocModifiedHandler(
                JOptionPane.YES_OPTION);

        docManager.setDocModifiedHandler(handler);
        final MockFileSelectorService fileSelectorService =
                MockFileSelectorService.createSaveService(
                        null,
                        false,
                        false);
        docManager.setFileSelectorService(fileSelectorService);
        docManager.onExit();
        handler.verify();
        fileSelectorService.verify();
        assertSame(activeDoc, docManager.getActiveDoc());
    }


    private void commonDocCreatedAndNotSavedSetup() {
        docManager.setStorageFormatManager(new ConExpStorageFormatManager());
        docManager.createNewDocument();
        assertFalse(docManager.getActiveDoc().isModified());
        docManager.getActiveDoc().markDirty();
    }


    private static MockDocModifiedHandler createMockDocModifiedHandler(
            int option) {
        MockDocModifiedHandler handler = new MockDocModifiedHandler();
        handler.setResponse(option);
        handler.setExpected(1);
        return handler;
    }


    private static class MockDocModifiedHandler implements DocModifiedHandler {
        private final ExpectationCounter counter;

        public MockDocModifiedHandler() {
            this.counter = new ExpectationCounter("Calls to handler");
        }

        int response;

        public void setResponse(int newResponse) {
            this.response = newResponse;
        }

        public int getSaveIfModifiedResponse() {
            counter.inc();
            return response;
        }

        public void setExpected(int i) {
            counter.setExpected(i);
        }

        public void verify() {
            counter.verify();
        }
    }

    private static class MockAppErrorHandler implements AppErrorHandler {

        private static void failWithException(Throwable exception) {
            fail(exception.getMessage() +
                    StringUtil.stackTraceToString(exception));
        }

        public void reportAppErrorMessage(String messageKey, Throwable exception) {
            failWithException(exception);
        }

        public void reportInternalError(String messageKey, Throwable exception) {
            failWithException(exception);
        }
    }
}

/*
 * User: sergey
 * Date: Oct 20, 2001
 * Time: 5:03:19 PM
 */
package conexp.frontend.tests;

import conexp.frontend.*;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import util.DataFormatException;
import util.StringUtil;
import util.gui.MostRecentUrlListManager;
import util.testing.SimpleMockPropertyChangeListener;

import javax.swing.*;
import java.awt.Component;
import java.io.File;
import java.io.IOException;

public class ContextDocManagerTest extends TestCase {
    private static final Class THIS = ContextDocManagerTest.class;
    private ContextDocManager docManager;

    public static Test suite() {
        return new TestSuite(THIS);
    }


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

    private ContextDocManager makeDocManager() {
        return new ContextDocManager(new JFrame(), new MockOptionPaneSupplier());
    }

    public void testLoadingOfDocumentWithAbsentLoader() {
        assertTrue(docManager.getStorageFormatManager().isEmpty());
        try {
            docManager.doLoadDocumentFromFile(new File("File.notSupportedExtension"));
        } catch (IOException e) {
            fail("Unexpected exception "+StringUtil.stackTraceToString(e));
        } catch (DataFormatException e) {
            assertTrue("Expect exception about absense of loader",true);
        }
    }

    static class MockDocument implements Document{
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

        public JMenuBar getMenuBar() {
            return null;
        }

        public JToolBar getToolBar() {
            return null;
        }
    };

    public void testMruList(){
        //MRU - when file should be added to MRU - after close with write;
        MostRecentUrlListManager mruManager = docManager.getMruManager();
        assertTrue(mruManager.getMostRecentUrlList().isEmpty());
        final String DOC_PATH = "One";
        DocumentRecord firstDocRec = makeDocRecordAndAddItToManager(DOC_PATH, true);
        assertEquals(0,mruManager.getMostRecentUrlList().size());
        docManager.removeDocument(firstDocRec);

        assertTrue(mruManager.getMostRecentUrlList().contains(DOC_PATH));
        assertEquals(1, mruManager.getMostRecentUrlList().size());

        makeDocRecordAndAddItToManager(DOC_PATH, true);
        assertEquals(0, mruManager.getMostRecentUrlList().size());

        //When file should be removed from MRU  - when
    }

    public void testNonAdditionToMRUofDocumentsWithoutCorrespondingFiles(){
        MostRecentUrlListManager mruManager = docManager.getMruManager();
        assertTrue(mruManager.getMostRecentUrlList().isEmpty());
        DocumentRecord docRecord = makeDocRecordAndAddItToManager("", false);
        assertEquals(0, mruManager.getMostRecentUrlList().size());
        docManager.removeDocument(docRecord);
        assertEquals(0, mruManager.getMostRecentUrlList().size());
    }

    private DocumentRecord makeDocRecordAndAddItToManager(final String DOC_PATH, boolean expHasCorrespondingFile) {
        final DocumentRecord firstDocRec = new DocumentRecord(new MockDocument(), DOC_PATH);
        assertEquals(expHasCorrespondingFile, firstDocRec.hasCorrespondingFile());
        docManager.addDocument(firstDocRec);
        return firstDocRec;
    }
}

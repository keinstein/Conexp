/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend;

import conexp.core.LocalizedMessageSupplier;
import conexp.frontend.io.UIDataFormatErrorHandler;
import conexp.frontend.ui.DefaultMenuSite;
import conexp.frontend.ui.MenuSite;
import conexp.frontend.util.*;
import util.*;
import util.gui.MostRecentUrlListManager;
import util.gui.fileselector.FileSelectorService;
import util.gui.fileselector.GenericFileFilter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ResourceBundle;


public class ContextDocManager extends BasePropertyChangeSupplier implements ActionChainBearer, DocManagerMessages, DocManager {
    //-----------------------------------------------------------
    private ActionMap actionChain = new ActionMap();

    private Action[] actions = {
        new ExitAppAction(), new OpenDocAction(),
        new NewDocAction(), new SaveDocAction(),
        new SaveDocAsAction(), new AboutAppAction()
    };

    public ActionMap getActionChain() {
        return actionChain;
    }

    public JTree getDocumentTree() {
        return getActiveDoc().getTree();

    }

    class ExitAppAction extends AbstractAction {
        ExitAppAction() {
            super("exitApp");
        }

        public void actionPerformed(ActionEvent e) {
            onExit();
        }
    }

    class OpenDocAction extends AbstractAction {
        OpenDocAction() {
            super("openDoc");
        }

        public void actionPerformed(ActionEvent e) {
            onOpen();
        }
    }

    class NewDocAction extends AbstractAction {
        NewDocAction() {
            super("newDoc");
        }

        public void actionPerformed(ActionEvent e) {
            onNewDocument();
        }
    }

    class SaveDocAction extends AbstractAction {
        SaveDocAction() {
            super("saveDoc");
        }

        public void actionPerformed(ActionEvent e) {
            onSave();
        }
    }

    class SaveDocAsAction extends AbstractAction {
        SaveDocAsAction() {
            super("saveDocAs");
        }

        public void actionPerformed(ActionEvent e) {
            onSaveAs();
        }
    }

    class AboutAppAction extends AbstractAction {

        public AboutAppAction() {
            super("aboutApp");
        }

        public void actionPerformed(ActionEvent e) {
            onAboutApp();
        }

    }

    private static final String DOCUMENT_MANAGER_SECTION = "DocumentManager";
    private static final String MOST_RECENT_URLS_KEY = "MostRecentUrls";
    private MostRecentUrlListManager mruManager = new MostRecentUrlListManager();

    public MostRecentUrlListManager getMruManager() {
        return mruManager;
    }

    private StorageFormatManager storageFormatManager;

    public void setStorageFormatManager(StorageFormatManager storageFormatManager) {
        this.storageFormatManager = storageFormatManager;
    }

    public StorageFormatManager getStorageFormatManager() {
        if (null == storageFormatManager) {
            storageFormatManager = new StorageFormatManager();
        }
        return storageFormatManager;
    }

    private ConfigurationManager configManager;

    private ConfigurationManager getConfigManager() {
        if (null == configManager) {
            configManager = new ConfigurationManager("Empty Config");
        }
        return configManager;
    }

    public void setConfigManager(ConfigurationManager configManager) {
        this.configManager = configManager;
        loadConfiguration();
    }

    private void loadConfiguration() {
        try {
            getConfigManager().loadConfiguration();
        } catch (IOException e) {
            System.out.println("No configuration for loading");
        }
        mruManager.setMostRecentUrlList(getConfigManager().fetchStringList(DOCUMENT_MANAGER_SECTION, MOST_RECENT_URLS_KEY, MostRecentUrlListManager.getMaximalNumberOfFilesInMRUList()));

    }

    private void saveConfiguration() {
        getConfigManager().storeStringList(DOCUMENT_MANAGER_SECTION, MOST_RECENT_URLS_KEY, mruManager.getMostRecentUrlList());
        try {
            getConfigManager().saveConfiguration();
        } catch (IOException e) {
            showMessage("Failed to write configuration " + StringUtil.stackTraceToString(e));
        }
    }


    private MenuSite menuSite;

    public ContextDocManager(JFrame mainFrame, OptionPaneSupplier optionPaneSupplier) {
        super();
        this.appMainWindow = mainFrame;
        appMainWindow.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onExit();
            }
        });
        this.optionPaneSupplier = optionPaneSupplier;
        ActionChainUtil.putActions(actionChain, actions);
        mruManager.setOpenCallback(new MostRecentUrlListManager.OpenCallback() {
            public void openUrl(String url) {
                ContextDocManager.this.openUrl(url);
            }
        });
        this.menuSite = new DefaultMenuSite(appMainWindow);
        menuSite.addMenu(this, createMenu());
        menuSite.addHelpMenu(this, createHelpMenu());
    }


    private OptionPaneSupplier optionPaneSupplier;

    private JComponent getOptionsPane() {
        return optionPaneSupplier.getOptionsPane();
    }

    private void onNewDocument() {
        //single eventSupplier interface
        if (null != docRecord) {
            /* if (docs.isModified()) {
                 switch (JOptionPane.showConfirmDialog(null, getSaveFileBeforeClosingMsg())) {
                     case JOptionPane.CANCEL_OPTION:
                         return;
                     case JOptionPane.YES_OPTION:
                         saveDoc(docs);
                         break;
                     case JOptionPane.NO_OPTION:
                         // nothing to do
                         break;
                     default:
                         Assert.isTrue(false);
                         break;
                 } // end of switch ()
             }*/
            createNewDocument();
        } // end of if ()
    }

    public void createNewDocument() {
        ContextDocument document = new ContextDocument();
        addDocument(new DocumentRecord(document, ""));
    }

    private OptionPaneViewChangeListener getOptionPaneViewChangeListener() {
        if (null == optionPaneViewChangeListener) {
            optionPaneViewChangeListener = new OptionPaneViewChangeListener(getOptionsPane());
        }
        return optionPaneViewChangeListener;
    }

    private OptionPaneViewChangeListener optionPaneViewChangeListener;

    private DocumentRecord docRecord;


    public void addDocument(DocumentRecord docRec) {
        closeDocument();

        getOptionPaneViewChangeListener().cleanUp();
        Document document = docRec.getDocument();
        document.addViewChangeListener(getOptionPaneViewChangeListener());
        document.setParentActionChain(getActionChain());
        document.activateViews();

        docRecord = docRec;
        mruManager.removeFromMRUList(docRec.getDocumentPath());

        fireActiveDocChanged();
    }

    public void removeDocument(DocumentRecord docRecord) {
        if (docRecord != null) {
            docRecord.getDocument().removeViewChangeListener(getOptionPaneViewChangeListener());
            if (docRecord.hasCorrespondingFile()) {
                mruManager.addToMRUList(docRecord.getDocumentPath());
            }
        }
        //element specific
        this.docRecord = null;
    }

    private void closeDocument() {
        removeDocument(docRecord);
    }

    private void fireActiveDocChanged() {
        getPropertyChangeSupport().firePropertyChange(ACTIVE_DOC_CHANGED, null, null);
    }

    private void fireActiveDocInfoChanged() {
        getPropertyChangeSupport().firePropertyChange(ACTIVE_DOC_INFO_CHANGED, null, null);
    }

    private DocumentLoader getLoader(String extension) {
        return getStorageFormatManager().getLoader(extension);
    }

    private void loadDocument(File f) throws IOException, DataFormatException {
        if (f.isFile()) {
            doLoadDocumentFromFile(f);
        }
    }

    /*@test_public*/

    public void doLoadDocumentFromFile(File f) throws IOException, DataFormatException {

        String extension = StringUtil.getExtension(f.getCanonicalPath());
        DocumentLoader loader = getLoader(extension);
        if (null == loader) {
            String msg = makeLocalizedMessageWithOneParam("NoLoaderForFileWithExtension", extension);
            throw new DataFormatException(msg);
        }
        //loader.setLocalizedMessageSupplier(); TODO
        FileReader fileReader = null;
        ContextDocument d = null;
        try {
            fileReader = new FileReader(f);
            //todo: set proper data format error handler
            d = loader.loadDocument(fileReader, new UIDataFormatErrorHandler(getMainAppWindow()));

        } finally {
            if (null != fileReader) {
                fileReader.close();
            }
        }
        addDocument(new DocumentRecord(d, f.getCanonicalPath()));
    }


//-------------------------------------------

    private boolean canExit() {
        return true;
    }

    private void onExit() {
        if (canExit()) {
            closeDocument(); //will be changed later on
            saveConfiguration();
            System.exit(0);
        }
    }

    private static ResourceBundle resources;

    static {
        resources = ResourceLoader.getResourceBundle("conexp/frontend/resources/ContextDocManager");  //$NON-NLS-1$
    }
    //------------------------------------------------------------

    private static ResourceBundle getResources() {
        return resources;
    }

    private LocalizedMessageSupplier getLocalizedMessageSupplier() {
        return messageSupplier;
    }

    private LocalizedMessageSupplier messageSupplier = new ContextDocManagerMessageSupplier();

    private String getLocalizedMessage(String key) {
        String message = getLocalizedMessageSupplier().getMessage(key);
        Assert.notNull(message);
        return message;
    }


//-----------------------------------------------------------
    private void onOpen() {
        FileSelectorService fileSelector =
                ServiceRegistry.fileSelectorService();
        if (fileSelector.performOpenService(getMainAppWindow(),
                getLocalizedMessageSupplier().getMessage("OpenFileMsg"),
                null, //default start dir
                getLoadFilters())) {

            openUrl(fileSelector.getSelectedPath());
        }
    }

    private void openUrl(final String url) {
        try {
            File f = new File(url);
            if (f.exists()) {
                loadDocument(f);
            }
        } catch (IOException e) {
            handleReadWriteException(e);
        } catch (DataFormatException e) {
            handleReadWriteException(e);
        }
    }

    private void handleReadWriteException(Throwable e) {
        showMessage(e.getMessage());
    }

    private GenericFileFilter[] getLoadFilters() {
        return getStorageFormatManager().getLoadFilters();
    }

    private JFrame appMainWindow;

    public JFrame getMainAppWindow() {
        return appMainWindow;
    }

    private String getDocDir() {
        return docRecord.getDocumentDirectory();
    }

    private String getDocPath() {
        return docRecord.getDocumentPath();
    }

    private String getFileName() {
        return docRecord.getDocumentFileName();
    }

    private void onSave() {
        if (getDocPath() == null) {
            onSaveAs();
            return;
        }
        try {
            File file = new File(getDocPath());
            saveDocument(file);
        } catch (IOException ex) {
            String msg = makeLocalizedMessageWithOneParam("FileSaveErrorMsg", ex.getMessage());
            showMessage(msg);
        }
    }

    private String makeLocalizedMessageWithOneParam(String messageKey, String msgParam) {
        String msg = MessageFormat.format(getLocalizedMessage(messageKey),
                new Object[]{msgParam});
        return msg;
    }


    private void onAboutApp() {
        (new AboutConExpDialog(getMainAppWindow(), getLocalizedMessage("AboutAppMsg"), true)).show();
    }


    private void onSaveAs() {
        FileSelectorService fileSelector = ServiceRegistry.fileSelectorService();
        if (fileSelector
                .performSaveService(getMainAppWindow(),
                        getLocalizedMessage("SaveFileAsMsg"),
                        getDocDir(),
                        getFileName(),
                        getSaveFilters())) {
            try {
                File f = new File(fileSelector.getSelectedPath());
                if (StringUtil.isEmpty(StringUtil.getExtension(f.getCanonicalPath()))) {
                    f = new File(f.getAbsolutePath() + getStorageFormatManager().getDefaultExtension());
                }
                final String extension = StringUtil.getExtension(f.getCanonicalPath());
                if (!isFormatSupported(extension)) {
                    throw new IOException("Do not support format with extension:" + extension);
                }
                if (f.exists()) {
                    String msg = makeLocalizedMessageWithOneParam("FileExistsConfirmOverwriteMsg", f.getName());

                    if (JOptionPane.YES_OPTION
                            != JOptionPane.showConfirmDialog(getMainAppWindow(),
                                    msg,
                                    getLocalizedMessage("ConfirmMsg"),
                                    JOptionPane.YES_NO_OPTION)) {
                        return;
                    }
                }

                saveDocumentAndUpdateDocumentInfo(f);

            } catch (Exception ex) {
                String msg = makeLocalizedMessageWithOneParam("FileSaveErrorMsg", ex.getMessage());
                showMessage(msg);
            }
        }
    }

    private boolean isFormatSupported(String extension) {
        return getWriter(extension) != null;
    }

    private GenericFileFilter[] getSaveFilters() {
        return getStorageFormatManager().getStoreFilters();
    }

    private void saveDocumentAndUpdateDocumentInfo(File f) throws IOException {
        saveDocument(f);

        docRecord.setDocumentUrl(f.getPath());

        fireActiveDocInfoChanged();
    }

    private void showMessage(String msg) {
        JOptionPane.showMessageDialog(getMainAppWindow(), msg);
    }

    private DocumentWriter getWriter(String extension) {
        //todo:should return default writer, when extension is unknown
        return getStorageFormatManager().getWriter(extension);
    }


    private void saveDocument(File f) throws IOException {
        final String extension = StringUtil.getExtension(f.getCanonicalPath());
        DocumentWriter documentWriter = getWriter(extension);
        if (documentWriter == null) {
            throw new IOException("Not supported extension " + extension);
        }

        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(f);
            documentWriter.storeDocument(getActiveDoc(), fileWriter);
        } finally {
            if (null != fileWriter) {
                fileWriter.close();
            }
        }
    }

    private Document getActiveDoc() {
        return docRecord.getDocument();
    }

    private JMenu createMenu() {
        ToolBuilder toolBuilder = new ToolBuilder(getResourceManager(), getActionChain());
        JMenu menu = toolBuilder.createMenu();
        int reopenIndex = MenuUtil.findIndexOfMenuComponentWithName(menu, "reopen");
        if (reopenIndex >= 0) {
            JMenu mruMenu = (JMenu) menu.getMenuComponent(reopenIndex);
            mruManager.setMruMenu(mruMenu);
        }
        return menu;
    }

    private JMenu createHelpMenu() {
        ToolBuilder toolBuilder = new ToolBuilder(getResourceManager(), getActionChain());
        return toolBuilder.createHelpMenu();
    }

    public static ResourceManager getResourceManager() {
        return new ResourceManager(getResources());
    }


    // INTERFACE TO FRAME
    // SHOULD BE REVIEWED

    public Component getActiveDocComponent() {
        return getActiveDoc().getDocComponent();
    }

    public JToolBar getActiveDocToolBar() {
        return getActiveDoc().getToolBar();
    }


    public void updateDocumentTree() {
        getActiveDoc().setFileName(getFileName());
    }


    private static class ContextDocManagerMessageSupplier implements LocalizedMessageSupplier {
        public String getMessage(String key) {
            return resources.getString(key);
        }
    }


}

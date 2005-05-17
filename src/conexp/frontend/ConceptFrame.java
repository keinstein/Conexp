/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend;

import util.ConfigurationManager;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


public class ConceptFrame extends JFrame {

    public ConceptFrame() {
        init();
    }

    private JSplitPane jSplitPaneMain = new JSplitPane();
    private JTabbedPane docTreeTabPane = new JTabbedPane();
    private JSplitPane jSplitPane1 = new JSplitPane();
    private JTree documentTree;

    private void createMainPane() {
        jSplitPane1.setPreferredSize(new Dimension(SizeOptions.getProjectPaneWidth(), 150));
        jSplitPane1.setOrientation(JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.setOneTouchExpandable(true);


        int sizeTab = 100;


        getContentPane().add(jSplitPaneMain, BorderLayout.CENTER);
        jSplitPaneMain.setOneTouchExpandable(true);
        jSplitPaneMain.add(jSplitPane1, JSplitPane.LEFT);

        docTreeTabPane.setPreferredSize(new Dimension(SizeOptions.getProjectPaneWidth(), sizeTab));
        docTreeTabPane.setMinimumSize(new Dimension(SizeOptions.getProjectPaneWidth(), sizeTab));

        jSplitPane1.add(docTreeTabPane, JSplitPane.TOP);
        jSplitPane1.add(optionPane, JSplitPane.BOTTOM);
        jSplitPane1.setDividerLocation(sizeTab);

        jSplitPaneMain.setDividerLocation(SizeOptions.getProjectPaneWidth());

    }

    private JLabel statusBar = new JLabel();


    private JPanel optionPane = new JPanel();

    private ContextDocManager manager;


    //Component initialization
    private void init() {
        setSize(new Dimension(SizeOptions.getMainFrameWidth(), SizeOptions.getMainFrameHeight()));
        getContentPane().setLayout(new BorderLayout());
        setTitle("Concept Explorer");

        optionPane.setLayout(new BorderLayout());
        constructDocumentManager();
        createMainPane();


        jSplitPaneMain.add(manager.getActiveDocComponent(), JSplitPane.RIGHT);
        // docTreeTabPane.add(scrollBrowserPane, "Diagram Browser");
        setToolBar(manager.getActiveDocToolBar());

        statusBar.setText(" ");
        getContentPane().add(statusBar, BorderLayout.SOUTH);
    }

    private void constructDocumentManager() {
        manager = makeDocManager();
        manager.addPropertyChangeListener(DocManagerMessages.ACTIVE_DOC_CHANGED, new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                onActiveDocChanged();
            }
        });
        manager.addPropertyChangeListener(DocManagerMessages.ACTIVE_DOC_INFO_CHANGED, new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                onActiveDocInfoChanged();
            }
        });
        // should be done here because after the creation of the document the tree is referred

        manager.createNewDocument();

        documentTree = doMakeTree();
        setDocumentTree(documentTree);
        docTreeTabPane.setTitleAt(0, "Document");
    }

    private JTree doMakeTree() {
        return manager.getDocumentTree();
    }

    private ContextDocManager makeDocManager() {
        OptionPaneSupplier optionPaneSupplier = new OptionPaneSupplier() {
            public JComponent getOptionsPane() {
                return optionPane;
            }
        };
        ContextDocManager contextDocManager = new ContextDocManager(this, optionPaneSupplier);
        contextDocManager.setStorageFormatManager(new ConExpStorageFormatManager());
		
        //new StorageFormatLoader(contextDocManager.getResources()).loadStorageFormats(contextDocManager.getStorageFormatManager());
       
        contextDocManager.setConfigManager(new ConfigurationManager("ConExp.prop", "ConExp configuration"));
        return contextDocManager;
    }

    private void updateDocumentTree(ContextDocManager manager) {
        setDocumentTree(manager.getDocumentTree());
        manager.updateDocumentTree();
    }

    private void setDocumentTree(Component documentTree) {
        JScrollPane wrapper = new JScrollPane(documentTree, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        if (docTreeTabPane.getComponentCount() == 0) {
            docTreeTabPane.add(wrapper);
        } else {
            docTreeTabPane.setComponentAt(0, wrapper);
        }
    }

//-----------------------------------------------------------------------
    private void onActiveDocChanged() {
        updateDocumentTree(manager);
        setToolBar(manager.getActiveDocToolBar());
        setDocComponent(manager.getActiveDocComponent());
    }

    private void onActiveDocInfoChanged() {
        updateDocumentTree(manager);
    }

    private Component docComponent;

    private void setDocComponent(Component comp) {
        if (null != docComponent) {
            jSplitPaneMain.remove(docComponent);
        } // end of if ()

        if (null != comp) {
            jSplitPaneMain.add(comp, JSplitPane.RIGHT);
        } // end of if ()
        docComponent = comp;
    }

    private JToolBar toolBar = null;

    private void setToolBar(JToolBar tool) {
        if (null != toolBar) {
            getContentPane().remove(toolBar);
        }
        if (null != tool) {
            getContentPane().add(tool, BorderLayout.NORTH);
        }
        getContentPane().validate();
        toolBar = tool;
    }

}

package conexp.frontend;

import util.ConfigurationManager;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 *  Description of the Class
 *
 *@author     Sergey
 *@created    8 N=L 2000 3.
 */
public class ConceptFrame extends JFrame {

    public ConceptFrame() {
        init();
    }

    JSplitPane jSplitPaneMain = new JSplitPane();
    JTabbedPane jTabbedPane1 = new JTabbedPane();
    JSplitPane jSplitPane1 = new JSplitPane();

    JTree contextTree;


    private void createMainPane() {
        jSplitPane1.setPreferredSize(new Dimension(SizeOptions.getProjectPaneWidth(), 150));
        jSplitPane1.setOrientation(JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.setOneTouchExpandable(true);



        int sizeTab = 100;


        getContentPane().add(jSplitPaneMain, BorderLayout.CENTER);
        jSplitPaneMain.setOneTouchExpandable(true);
        jSplitPaneMain.add(jSplitPane1, JSplitPane.LEFT);

        jTabbedPane1.setPreferredSize(new Dimension(SizeOptions.getProjectPaneWidth(), sizeTab));
        jTabbedPane1.setMinimumSize(new Dimension(SizeOptions.getProjectPaneWidth(), sizeTab));

        jSplitPane1.add(jTabbedPane1, JSplitPane.TOP);
        jSplitPane1.add(optionPane, JSplitPane.BOTTOM);
        jSplitPane1.setDividerLocation(sizeTab);


        contextTree = makeTree();
        jTabbedPane1.add(contextTree, "Contexts");
        // jTabbedPane1.add(scrollBrowserPane, "Diagram Browser");
        jSplitPaneMain.setDividerLocation(SizeOptions.getProjectPaneWidth());

    }

    JLabel statusBar = new JLabel();


    JPanel optionPane = new JPanel();

    ContextDocManager manager;


    //Component initialization
    private void init() {
        setSize(new Dimension(SizeOptions.getMainFrameWidth(), SizeOptions.getMainFrameHeight()));
        getContentPane().setLayout(new BorderLayout());
        setTitle("Concept Explorer");

        optionPane.setLayout(new BorderLayout());
        createMainPane();

        constructDocumentManager();


        jSplitPaneMain.add(manager.getActiveDocComponent(), JSplitPane.RIGHT);

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
        manager.createNewDocument();
    }

    private ContextDocManager makeDocManager() {
        OptionPaneSupplier optionPaneSupplier = new OptionPaneSupplier() {
            public JComponent getOptionsPane() {
                return optionPane;
            }
        };
        ContextDocManager contextDocManager = new ContextDocManager(this, optionPaneSupplier);
        contextDocManager.setStorageFormatManager(new ConExpStorageFormatManager());
        contextDocManager.setConfigManager(new ConfigurationManager("ConExp.prop","ConExp configuration"));
        return contextDocManager;
    }


    OidNode cxtName;
    TreeModel model;
    TreePath treePath;
    Object node[];


    public JTree makeTree() {
        node = new Object[2];
//		DefaultMutableTreeNode top = new DefaultMutableTreeNode(new OidNode(0, "Landscape"));
//		node[0] = top;
//		DefaultMutableTreeNode parent = top;
        DefaultMutableTreeNode contexts = new DefaultMutableTreeNode(new OidNode("Contexts"));
        node[0] = contexts;
        DefaultMutableTreeNode parent = contexts;
//      DefaultMutableTreeNode scales = new DefaultMutableTreeNode(new OidNode(2, "Scales"));
//		parent.add(contexts);
//		parent.add(scales);

        cxtName = new OidNode("Not Empty String"); //if string is empty, it doesn't work :-((
        node[1] = new DefaultMutableTreeNode(cxtName);

        parent.add((DefaultMutableTreeNode) node[1]);

//		parent = scales;
//		parent.add(new DefaultMutableTreeNode(new OidNode(5, "Not yet implemented")));

        treePath = new TreePath(node);
        model = new DefaultTreeModel(contexts);
        JTree ret = new JTree(model);

        ret.setSelectionPath(treePath);
        ret.setShowsRootHandles(true);
        ret.setEditable(false);

        return ret;
    }

    void showContextName(String name) {
        cxtName.setName(name);
        contextTree.invalidate();
        jTabbedPane1.invalidate();
        jTabbedPane1.repaint();

    }

//-----------------------------------------------------------------------
    public void onActiveDocChanged() {
        showContextName(manager.getFileName());
        setToolBar(manager.getActiveDocToolBar());
        setDocComponent(manager.getActiveDocComponent());
    }

    public void onActiveDocInfoChanged() {
        showContextName(manager.getFileName());
    }

    private Component component;

    private void setDocComponent(Component comp) {
        if (null != component) {
            jSplitPaneMain.remove(component);
        } // end of if ()

        if (null != comp) {
            jSplitPaneMain.add(comp, JSplitPane.RIGHT);
        } // end of if ()
        component = comp;
    }

    private JToolBar toolBar;

    private void setToolBar(JToolBar tool) {
        if (null != toolBar) {
            getContentPane().remove(toolBar);
        } // end of if ()
        if (null != tool) {
            getContentPane().add(tool, BorderLayout.NORTH);
        } // end of if ()
        toolBar = tool;
    }

}
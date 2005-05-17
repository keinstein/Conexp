/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend;

import com.gargoylesoftware.base.collections.NotificationListEvent;
import com.gargoylesoftware.base.collections.NotificationListListener;
import com.visibleworkings.trace.Trace;
import conexp.core.*;
import conexp.core.attrexplorationimpl.AttributeExplorerImplementation;
import conexp.frontend.attributeexploration.AttributeExplorationUserCallbackImplementation;
import conexp.frontend.components.LatticeComponent;
import conexp.frontend.components.LatticeSupplierAndCalculator;
import conexp.frontend.contexteditor.ContextViewPanel;
import conexp.frontend.latticeeditor.CEDiagramEditorPanel;
import conexp.frontend.latticeeditor.LatticeDrawing;
import conexp.frontend.ruleview.*;
import conexp.frontend.ui.ViewManager;
import conexp.frontend.ui.ViewManagerException;
import conexp.frontend.ui.tree.ITreeObject;
import conexp.frontend.ui.tree.IconCellRenderer;
import conexp.frontend.ui.tree.IconData;
import conexp.frontend.util.ActionChainUtil;
import conexp.frontend.util.ResourceManager;
import conexp.frontend.util.ToolBuilder;
import util.Assert;
import util.FormatUtil;
import util.StringUtil;
import util.BasePropertyChangeSupplier;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.util.Collection;
import java.util.ResourceBundle;
import java.util.TooManyListenersException;
import java.util.Iterator;


public class ContextDocument implements ActionChainBearer, Document {
    //todo: refactor me
    // problems with setting lattice component( you can't set for now lattice component,
    // because there is no way to notify views about change ).
    //todo:
    // problems with setting context (if lattice component was created and context was set after it, than lattice component
    // will not know about context
    private static ResourceBundle resContextDocument;
    private static final String CONTEXT_EDITOR_TYPE = "CONTEXT_EDITOR";
    private static final String LINE_DIAGRAM_EDITOR_TYPE = "LINE_DIAGRAM_EDITOR";
    private static final String IMPLICATION_VIEW_TYPE = "IMPLICATION_VIEW";
    private static final String ASSOCIATIONS_VIEW_TYPE = "ASSOCIATIONS_VIEW";

/*
    private static final String NESTED_LINE_DIAGRAM_TYPE = "NESTED_LINE_DIAGRAM_VIEW";
*/
    private static final String DIAGRAM_CREATOR_TYPE = "DIAGRAM_CREATOR_VIEW";

    private TreeNodeBase contextName = new TreeNodeBase("Not Empty String"); //if string is empty, it doesn't work :-((;
    private JTree contentTree;
    private DefaultMutableTreeNode contextTreeRoot;
    private static final String CONTEXT_IMAGE = "resources/contextIcon.gif";
    private static final String DOMAIN_IMAGE = "resources/dataIcon.gif";
    private static final String LATTICE_IMAGE = "resources/latticeIcon.gif";
    private static final String IMPLICATION_IMAGE = "resources/implicationIcon.gif";
    private static final String ASSOCIATIONS_IMAGE = "resources/associationRuleIcon.gif";
    public static final ImageIcon LATTICE_ICON = ResourceLoader.getIcon(LATTICE_IMAGE);
    public static final ImageIcon CONTEXT_ICON = ResourceLoader.getIcon(CONTEXT_IMAGE);
    public static final ImageIcon IMPLICATION_ICON = ResourceLoader.getIcon(IMPLICATION_IMAGE);
    public static final ImageIcon ASSOCIATIONS_ICON = ResourceLoader.getIcon(ASSOCIATIONS_IMAGE);
    public static final ImageIcon DOMAIN_ICON = ResourceLoader.getIcon(DOMAIN_IMAGE);


    private LatticeCollectionDecorator latticeCollectionDecorator;
    public static final String ASSOCIATIONS_NODE_NAME = "Associations";


    static {
        resContextDocument = ResourceLoader.getResourceBundle("conexp/frontend/resources/ContextDocument");  //$NON-NLS-1$
    }
    //------------------------------------------------------------

    private static ResourceBundle getResources() {
        return resContextDocument;
    }

    private LocalizedMessageSupplier messageSupplier = new LocalizedMessageSupplier() {
        public String getMessage(String key) {
            return resContextDocument.getString(key);
        }
    };

    private LocalizedMessageSupplier getLocalizedMessageSupplier() {
        return messageSupplier;
    }

    private String getLocalizedMessage(String key) {
        return getLocalizedMessageSupplier().getMessage(key);
    }


    //--------------------------------------------
    private DocManager docManager = null;

    public void setDocManager(DocManager docManager) {
        this.docManager = docManager;
    }

    private JFrame getMainFrame() {
        if (null != docManager) {
            return docManager.getMainAppWindow();
        } else {
            return null;
        }
    }

    //--------------------------------------------
    private boolean showMessages = true;

    public void setShowMessages(boolean newShowMessages) {
        showMessages = newShowMessages;
    }

    private void showMessage(String msg) {
        if (showMessages) {
            JOptionPane.showMessageDialog(getMainFrame(), msg);
        }
    }

    private ActionMap actionChain = new ActionMap();
    //------------------------------------------------------------
    /**
     * Gets the ActionMap attribute of the ContextDocument object
     *
     * @return The ActionMap value
     */
    public ActionMap getActionChain() {
        return actionChain;
    }

    //------------------------------------------------------------
    //------------------------------------------------------------
    private Action[] actions = {
        new CalcConceptCountDS(),
        new BuildLatticeDS(),
        new AttributeExploration(),
        new CalcImplicationsNCS(),
        new CalcAssociationRules(),
//        new BuildNestedLineDiagram(),
        new ShowDiagramEditor(),
        new CreateLatticeViewAction()
    };

    public Collection getLatticeCollection() {
        return contextDocumentModel.getLatticeComponents();
    }

    public int getActiveLatticeComponentID() {
        return latticeCollectionDecorator.getActiveLatticeComponentID();
    }

    void setActiveLatticeComponent(LatticeComponent latticeComponent) {
        int id = contextDocumentModel.findLatticeComponent(latticeComponent);
        latticeCollectionDecorator.setActiveLatticeComponentID(id);
    }

    public void makeLatticeSnapshot() {
        final int activeLatticeComponentIndex = getActiveLatticeComponentID();
        final int size = contextDocumentModel.getLatticeComponents().size();
        contextDocumentModel.makeLatticeSnapshot(activeLatticeComponentIndex);
        latticeCollectionDecorator.setActiveLatticeComponentID(size);
    }


    public LatticeComponent getLatticeComponent(int index) {
        return contextDocumentModel.getLatticeComponent(index);
    }

    private DefaultMutableTreeNode getContextTreeRoot() {
        if (null == contextTreeRoot) {
            contextTreeRoot = makeContextTreeNode();
        }
        return contextTreeRoot;
    }


    class CalcConceptCountDS extends AbstractAction {

        CalcConceptCountDS() {
            super("calcConceptsDS");//$NON-NLS-1$
        }

        public void actionPerformed(ActionEvent e) {

            final String message = FormatUtil.format(getLocalizedMessageSupplier().getMessage("ConceptNumMsg"), //$NON-NLS-1$
                    contextDocumentModel.getConceptCount());
            showMessage(message);
        }


    }


    private ContextDocumentModel contextDocumentModel;

    //----------------------------------------
    public Context getContext() {
        return contextDocumentModel.getContext();
    }

    //------------------------------------------------

    public void setContext(Context cxt) {
        contextDocumentModel.setContext(cxt);
    }


    private View makeContextTableView() {
        ContextViewPanel panel = new ContextViewPanel(getContext());
        panel.setParentActionMap(getActionChain());
        return new ToolbarComponentDecorator(panel, false);
    }

    //LATTICE
    class BuildLatticeDS extends AbstractAction {

        BuildLatticeDS() {
            super("buildLatticeDS");//$NON-NLS-1$
        }

        public void actionPerformed(ActionEvent e) {
            calculateAndLayoutLattice();
            activateView(VIEW_LATTICE);
        }

    }

    public void calculateFullLattice() {
        getOrCreateDefaultLatticeComponent().calculateLattice();
    }

    public void calculateAndLayoutLattice() {
        getOrCreateDefaultLatticeComponent().calculateAndLayoutPartialLattice();
    }

    public synchronized LatticeComponent getOrCreateDefaultLatticeComponent() {
        if (0 == getLatticeCollection().size()) {
            contextDocumentModel.addLatticeComponent();
        }
        return getLatticeComponent(0);
    }

    public void resetLatticeComponent() {
        contextDocumentModel.resetLatticeComponent();
    }


    class LatticeCollectionDecorator extends BasePropertyChangeSupplier implements LatticeSupplierAndCalculator {
        private PropertyChangeListener propertyChangeRetranslator= new PropertyChangeListener(){
                                    public void propertyChange(PropertyChangeEvent evt) {
                                       firePropertyChange(evt.getPropertyName(),
                                               evt.getOldValue(), evt.getNewValue());
                                    }
                                };

        public LatticeCollectionDecorator() {
            contextDocumentModel.addLatticeComponentsListener(new NotificationListListener(){
                public void listElementsAdded(NotificationListEvent event) {
                    addPropertyChangeListener(event);
                }

                public void listElementsRemoved(NotificationListEvent event) {
                    removePropertyChangeListener(event);
                }

                public void listElementsChanged(NotificationListEvent event) {
                    removePropertyChangeListener(event);
                    addPropertyChangeListener(event);
                }
            });
        }

        private void removePropertyChangeListener(NotificationListEvent event) {
            java.util.List oldValues = event.getOldValues();
            for (Iterator iterator = oldValues.iterator(); iterator.hasNext();) {
                LatticeComponent latticeComponent = (LatticeComponent) iterator.next();
                latticeComponent.removePropertyChangeListener(propertyChangeRetranslator);
            }
        }

        private void addPropertyChangeListener(NotificationListEvent event) {
            java.util.List newValues = event.getNewValues();
            for (Iterator iterator = newValues.iterator(); iterator.hasNext();) {
                LatticeComponent latticeComponent = (LatticeComponent) iterator.next();
                latticeComponent.addPropertyChangeListener(propertyChangeRetranslator);
            }
        }

        private int activeLatticeComponentId;

        private void setActiveLatticeComponentID(int newId) {
            if (activeLatticeComponentId != newId) {
                activeLatticeComponentId = newId;
                getActiveLatticeComponent().fireLatticeRecalced();
            }
        }

        public int getActiveLatticeComponentID() {
            return activeLatticeComponentId;
        }

        LatticeSupplierAndCalculator getActiveLatticeSupplierAndCalculator() {
            return getActiveLatticeComponent();
        }

        private LatticeComponent getActiveLatticeComponent() {
            return getLatticeComponent(getActiveLatticeComponentID());
        }

        public void calculateAndLayoutPartialLattice() {
            getActiveLatticeSupplierAndCalculator().calculateAndLayoutPartialLattice();
        }

        public SetProvidingEntitiesMask getAttributeMask() {
            return getActiveLatticeSupplierAndCalculator().getAttributeMask();
        }

        public SetProvidingEntitiesMask getObjectMask() {
            return getActiveLatticeSupplierAndCalculator().getObjectMask();
        }

        public LatticeDrawing getDrawing() {
            return getActiveLatticeSupplierAndCalculator().getDrawing();
        }

        public void cleanUp() {
            Assert.notImplemented();
        }
    }

    //------------------------------------------------------------
    private View makeLatticeView() {
        LatticeAndEntitiesMaskSplitPane latticeSplitPane = new LatticeAndEntitiesMaskSplitPane(latticeCollectionDecorator, getActionChain());
        latticeSplitPane.restorePreferences();
        return new ToolbarComponentDecorator(latticeSplitPane, false);
    }

    //----------------------------------------------------------

/*
    //Nested line diagram testing
    class BuildNestedLineDiagram extends AbstractAction {
        public BuildNestedLineDiagram() {
            super("buildNestedDiagram");
        }

        public void actionPerformed(ActionEvent e) {
            doBuildNestedLineDiagram();
        }
    }
*/


    class ShowDiagramEditor extends AbstractAction {
        public ShowDiagramEditor() {
            super("showDiagramEditor");
        }

        public void actionPerformed(ActionEvent e) {
            activateView(VIEW_DIAGRAM_CREATOR);
        }
    }

    //----------------------------------------------------------

    //IMPLICATIONS
    class CalcImplicationsNCS extends AbstractAction {

        CalcImplicationsNCS() {
            super("calcImplNCS");//$NON-NLS-1$
        }

        public void actionPerformed(ActionEvent e) {
            getImplicationBaseCalculator().findDependencies();
            if (implicationsTreeNode == null)
                addImplicationsNodeToTree();
            activateView(VIEW_IMPLICATIONS);
        }
    }


    class AttributeExploration extends AbstractAction {
        public AttributeExploration() {
            super("attributeExploration");
        }

        public void actionPerformed(ActionEvent e) {
            doAttributeExploration();
        }
    }
/*
    private void doBuildNestedLineDiagram() {
        activateView(VIEW_NESTED);
    }
*/


    private void doAttributeExploration() {
        AttributeExplorer explorer = new AttributeExplorerImplementation();
        explorer.setContext(getContext());
        explorer.setImplicationSet(getImplicationBaseCalculator().getImplications());

        AttributeExplorationUserCallbackImplementation attrExplCallback = new AttributeExplorationUserCallbackImplementation(getMainFrame());
        attrExplCallback.setAttributeInformationSupplier(getContext());
        explorer.setUserCallback(attrExplCallback);
        explorer.performAttributeExploration();
        showMessage(getLocalizedMessage("AttributeExplorationFinished"));
    }


    static class DependencySetRecalcPolicy extends DefaultContextListener {
        final DependencySetCalculator supplier;

        public DependencySetRecalcPolicy(DependencySetCalculator supplier) {
            this.supplier = supplier;
        }

        public void contextStructureChanged() {
            supplier.clearDependencySet();
        }

        public void relationChanged() {
            supplier.clearDependencySet();
        }
    }

    private ImplicationBaseCalculator implicationBaseCalculator;

    public ImplicationBaseCalculator getImplicationBaseCalculator() {
        if (null == implicationBaseCalculator) {
            implicationBaseCalculator = new ImplicationBaseCalculator(getContext(),
                    NextClosedSetImplicationCalculatorFactory.getInstance());
            getContext().addContextListener(getImplicationBaseRecalcPolicy());

        }
        return implicationBaseCalculator;
    }

    private ContextListener implicationBaseRecalcPolicy;

    private ContextListener getImplicationBaseRecalcPolicy() {
        if (null == implicationBaseRecalcPolicy) {
            implicationBaseRecalcPolicy = new DependencySetRecalcPolicy(getImplicationBaseCalculator());
        }
        return implicationBaseRecalcPolicy;
    }

    //------------------------------------------------------------
    private View makeImplicationsView() {
        DependencySetSupplier supplier = getImplicationBaseCalculator();

        ImplicationsView implicationView = new ImplicationsView(supplier, getActionChain());

        BaseDependencySetRecalcPolicy dependencySetRecalcPolicy = new BaseDependencySetRecalcPolicy(supplier) {
            protected void registerEventProcessors() {
                registerEventProcessor(DependencySetSupplier.RULE_SET_PROPERTY, updateDependencySetAction);
                registerEventProcessor(DependencySetSupplier.RULE_SET_CLEARED, updateDependencySetAction);
            }
        };
        try {
            dependencySetRecalcPolicy.addDependencySetConsumer(implicationView);
        } catch (TooManyListenersException ex) {
            Assert.isTrue(false);
        }
        //changed due to bad behaviour inside scroll pane
        return new ToolbarComponentDecorator(implicationView, false);
    }

    // ASSOCIATION RULES

    class CalcAssociationRules extends AbstractAction {

        CalcAssociationRules() {
            super("calcAssociationRules");//$NON-NLS-1$
        }

        public void actionPerformed(ActionEvent e) {
            findAssociations();
            addAssociationsNodeToTree();
            activateView(VIEW_ASSOCIATIONS);
        }
    }

    //CONTROLLER STUFF
    private AssociationRuleCalculator associationMiner;

    //------------------------------------------------------------
    public AssociationRuleCalculator getAssociationMiner() {
        if (null == associationMiner) {
            associationMiner = new AssociationRuleCalculator(getContext());
            getContext().addContextListener(getAssociationRulesRecalcPolicy());
        }
        return associationMiner;
    }

    private ContextListener associationsContextListener;


    private ContextListener getAssociationRulesRecalcPolicy() {
        if (null == associationsContextListener) {
            associationsContextListener = new DependencySetRecalcPolicy(getAssociationMiner());
        }
        return associationsContextListener;
    }


    //------------------------------------------------------------
    public DependencySet getAssociationRules() {
        return getAssociationMiner().getDependencySet();
    }

    //------------------------------------------------------------
    public void findAssociations() {
        getAssociationMiner().findDependencies();
    }

    //------------------------------------------------------------
    private View makeAssociationsRuleView() {

        DependencySetSupplier supplier = getAssociationMiner();
        AssociationRulesView associationRules = new AssociationRulesView(supplier, getActionChain());
        BaseDependencySetRecalcPolicy dependencySetRecalcPolicy = new BaseDependencySetRecalcPolicy(supplier) {
            protected void registerEventProcessors() {
                registerEventProcessor(DependencySetSupplier.RULE_SET_PROPERTY, updateDependencySetAction);
                registerEventProcessor(DependencySetSupplier.RULE_SET_CLEARED, updateDependencySetAction);
            }
        };
        try {
            dependencySetRecalcPolicy.addDependencySetConsumer(associationRules);
        } catch (TooManyListenersException ex) {
            Assert.isTrue(false);
        }
        return new ToolbarComponentDecorator(associationRules, false);
    }
    //------------------------------------------------------------
//VIEW MANAGEMENT


    private ViewFactory viewFactory;
    private ViewManager viewManager;

    public static final String VIEW_CONTEXT = "Context";//$NON-NLS-1$
    public static final String IMPLICATIONS_NODE_NAME = "Implications";
    public static final String VIEW_IMPLICATIONS = IMPLICATIONS_NODE_NAME;//$NON-NLS-1$
    public static final String VIEW_ASSOCIATIONS = "Associations";//$NON-NLS-1$
    public static final String VIEW_LATTICE = "Lattice";
//    public static final String VIEW_NESTED = "NestedLineDiagram";
    private static final String VIEW_DIAGRAM_CREATOR = "DiagramCreator";


    private void registerViews() {
        try {
            viewManager.registerView(VIEW_CONTEXT, CONTEXT_EDITOR_TYPE, resContextDocument.getString("ContextEditorCaption"));//$NON-NLS-1$
            viewManager.registerView(VIEW_LATTICE, LINE_DIAGRAM_EDITOR_TYPE, resContextDocument.getString("LatticeViewCaption"));//$NON-NLS-1$
            viewManager.registerView(VIEW_IMPLICATIONS, IMPLICATION_VIEW_TYPE, resContextDocument.getString("ImplViewCaption"));
            viewManager.registerView(VIEW_ASSOCIATIONS, ASSOCIATIONS_VIEW_TYPE, resContextDocument.getString("AssociationRulesViewCaption"));//$NON-NLS-1$

//            viewManager.registerView(VIEW_NESTED, NESTED_LINE_DIAGRAM_TYPE, "Test for nested line diagram");
            viewManager.registerView(VIEW_DIAGRAM_CREATOR, DIAGRAM_CREATOR_TYPE, "Diagram creator");


        } catch (ViewManagerException ex) {
            Assert.isTrue(false, "registration of view types failed");
        }
    }

    private ViewFactory getViewFactory() {
        if (null == viewFactory) {
            viewFactory = new ViewFactory() {
                public View makeView(String type) {
                    if (CONTEXT_EDITOR_TYPE.equals(type)) {
                        return makeContextTableView();
                    }
                    if (LINE_DIAGRAM_EDITOR_TYPE.equals(type)) {
                        return makeLatticeView();
                    }
                    if (IMPLICATION_VIEW_TYPE.equals(type)) {
                        return makeImplicationsView();
                    }
                    if (ASSOCIATIONS_VIEW_TYPE.equals(type)) {
                        return makeAssociationsRuleView();
                    }

/*
                    if (NESTED_LINE_DIAGRAM_TYPE.equals(type)) {
                        return makeNestedLineDiagramView();
                    }
*/

                    if (DIAGRAM_CREATOR_TYPE.equals(type)) {
                        return makeDiagramCreatorView();
                    }
                    return null;
                }
            };
        }
        return viewFactory;
    }

    static class JPanelView extends JPanel implements View {
        public void initialUpdate() {
            repaint();
        }
    }


    private View makeDiagramCreatorView() {
        CEDiagramEditorPanel diagramEditorPanel = new CEDiagramEditorPanel();
        diagramEditorPanel.setDocument(this);
        JScrollPane pane = new JScrollPane(diagramEditorPanel);
        JPanelView main = new JPanelView();
        main.setLayout(new BorderLayout());
        main.add(pane, BorderLayout.CENTER);
        JToolBar toolbar = new JToolBar();
        Action[] actions = diagramEditorPanel.getActions();
        for (int i = 0; i < actions.length; i++) {
            toolbar.add(actions[i]);
        }
        main.add(toolbar, BorderLayout.NORTH);

        return main;
    }

/*
    private View makeNestedLineDiagramView() {
        //this is simply mock data for know, only for purpose of development
        Context cxt = SetBuilder.makeContext(new int[][]{
            {1, 0, 1, 1},
            {1, 0, 1, 1},
            {1, 1, 0, 1},
            {1, 1, 1, 0}
        });

        final LatticeComponent outer = new LatticeComponent(cxt);
        outer.setLayoutEngine(new SimpleLayoutEngine());
        outer.setLayouterProvider(new MinIntersectionLayouterProvider());
        outer.getAttributeMask().setSelected(2, false);
        outer.getAttributeMask().setSelected(3, false);

        outer.calculateAndLayoutPartialLattice();

        LatticeComponent inner = new LatticeComponent(cxt);
        inner.setLayoutEngine(new SimpleLayoutEngine());
        inner.setLayouterProvider(new MinIntersectionLayouterProvider());
        inner.getAttributeMask().setSelected(0, false);
        inner.getAttributeMask().setSelected(1, false);
        inner.calculateAndLayoutPartialLattice();

        //it can be also the set
        ConceptsCollection concepts = FCAEngineRegistry.buildLattice(cxt);

        NestedLineDiagramView view = new NestedLineDiagramView();
        view.getDrawing().buildNestedDrawing(outer.getDrawing(), inner.getDrawing(), concepts);


        return view;
    }
*/

    private void activateView(String viewName) {
        try {
            getViewManager().activateView(viewName);
        } catch (ViewManagerException ex) {
            Assert.isTrue(false, "activation of view with id=" + viewName + " failed");
        }
    }

    public ViewManager getViewManager() {
        if (null == viewManager) {
            viewManager = new JTabPaneViewManager();
            viewManager.setViewFactory(getViewFactory());
            Assert.isTrue(null != viewManager);
            registerViews();
        }
        return viewManager;
    }

    /**
     * @test_public
     */
    public Container getViewContainer() {
        return ((JTabPaneViewManager) getViewManager()).getViewContainer();
    }


    public void addViewChangeListener(ViewChangeListener listener) {
        getViewManager().addViewChangeListener(listener);
    }

    public void removeViewChangeListener(ViewChangeListener listener) {
        getViewManager().removeViewChangeListener(listener);

    }

    // DOC Visible component
    public Component getDocComponent() {
        return getViewContainer();
    }


    private boolean hasAtLeastOneLattice() {
        return !getLatticeCollection().isEmpty();
    }


// document tree and connected actions
    class CreateLatticeViewAction extends AbstractAction {
        public CreateLatticeViewAction() {
            super("createLatticeView");
        }

        public void actionPerformed(ActionEvent e) {
            makeLatticeSnapshot();
        }
    }

    public JTree getTree() {
        if (null == contentTree) {
            contentTree = makeTree();
        }
        return contentTree;
    }

    private JTree makeTree() {
        final JTree ret = new JTree(getDocumentTreeModel());
        ret.setCellRenderer(new IconCellRenderer());
        ret.setSelectionPath(getTreePath());
        ret.setShowsRootHandles(true);
        ret.setEditable(false);
        ret.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                TreePath path = ret.getPathForLocation(x, y);
                if (path == null) {
                    return;
                }
                final int pathCount = path.getPathCount();
                DefaultMutableTreeNode activeItem = (DefaultMutableTreeNode) path.getPathComponent(pathCount - 1);
                IconData iconData = (IconData) activeItem.getUserObject();
                Object actualData = iconData.getObject();
                if (actualData instanceof ITreeObject) {
                    ITreeObject treeObject = (ITreeObject) actualData;
                    if (e.isPopupTrigger()) {
                        JPopupMenu popupMenu = new JPopupMenu();
                        treeObject.fillPopupMenu(popupMenu);
                        if (popupMenu.getComponentCount() > 0) {
                            popupMenu.show(e.getComponent(), x, y);
                        }
                    } else {
                        if (e.getClickCount() == 1) {
                            treeObject.navigate();
                        }
                    }
                }
            }
        });
        return ret;
    }

    private TreePath treePath;

    private TreePath getTreePath() {
        return treePath;
    }


    private DefaultTreeModel documentTreeModel;

    private DefaultTreeModel getDocumentTreeModel() {
        if (null == documentTreeModel) {
            documentTreeModel = makeTreeModel();
            contextDocumentModel.addLatticeComponentsListener(new NotificationListListener() {
                public void listElementsAdded(NotificationListEvent event) {
                    int size = contextDocumentModel.getLatticeComponents().size();
                    getContextTreeRoot().add(makeLatticeTreeNode(size - 1));
                    getDocumentTreeModel().reload();
                }

                public void listElementsRemoved(NotificationListEvent event) {
                    Assert.notImplemented();
                }

                public void listElementsChanged(NotificationListEvent event) {
                    Assert.notImplemented();
                }
            });
        }
        return documentTreeModel;
    }


    private DefaultTreeModel makeTreeModel() {
        DefaultMutableTreeNode domain = new DefaultMutableTreeNode(new IconData(DOMAIN_ICON, contextName));
        domain.add(getContextTreeRoot());

        if (hasAtLeastOneLattice()) {
            final int bound = contextDocumentModel.getLatticeComponents().size();
            for (int i = 0; i < bound; i++) {
                getContextTreeRoot().add(makeLatticeTreeNode(i));
            }
        }
        if (implicationSetIsComputed()) {
            addImplicationsNodeToTree();
        }
        if (associationSetIsComputed()) {
            addAssociationsNodeToTree();
        }

        DefaultMutableTreeNode[] node = new DefaultMutableTreeNode[2];
        node[0] = domain;
        node[1] = getContextTreeRoot();
        treePath = new TreePath(node);

        return new DefaultTreeModel(domain);
    }

    private void addAssociationsNodeToTree() {
        getContextTreeRoot().add(getAssociationsTreeNode());
        if (null != documentTreeModel) {
            documentTreeModel.reload();
        }
    }

    private void addImplicationsNodeToTree() {
        getContextTreeRoot().add(getImplicationsTreeNode());
        if (null != documentTreeModel) {
            documentTreeModel.reload();
        }
    }

    private boolean implicationSetIsComputed() {
        return getImplicationBaseCalculator().isComputed();
    }

    private boolean associationSetIsComputed() {
        return getAssociationMiner().isComputed();
    }

    class GenericComponentTreeObject implements ITreeObject {
        String data;
        String viewName;

        public GenericComponentTreeObject(String data, String viewName) {
            this.data = data;
            this.viewName = viewName;
        }

        public void fillPopupMenu(JPopupMenu popupMenu) {

        }

        public String toString() {
            return data;
        }

        public void navigate() {
            activateView(viewName);
        }
    }


    private DefaultMutableTreeNode makeContextTreeNode() {
        return new DefaultMutableTreeNode(new IconData(CONTEXT_ICON, new GenericComponentTreeObject("Context", VIEW_CONTEXT)));
    }

    private DefaultMutableTreeNode implicationsTreeNode;

    MutableTreeNode getImplicationsTreeNode() {
        if (null == implicationsTreeNode) {
            implicationsTreeNode = makeImplicationTreeNode();
        }
        return implicationsTreeNode;
    }

    private DefaultMutableTreeNode makeImplicationTreeNode() {
        return new DefaultMutableTreeNode(new IconData(IMPLICATION_ICON, new GenericComponentTreeObject(IMPLICATIONS_NODE_NAME, VIEW_IMPLICATIONS)));
    }

    private DefaultMutableTreeNode associationsTreeNode;

    MutableTreeNode getAssociationsTreeNode() {
        if (null == associationsTreeNode) {
            associationsTreeNode = makeAssociationsTreeNode();
        }
        return associationsTreeNode;
    }

    private DefaultMutableTreeNode makeAssociationsTreeNode() {
        return new DefaultMutableTreeNode(new IconData(ASSOCIATIONS_ICON, new GenericComponentTreeObject(ASSOCIATIONS_NODE_NAME, VIEW_ASSOCIATIONS)));
    }

    private DefaultMutableTreeNode makeLatticeTreeNode(int index) {
        class LatticeComponentTreeObject implements ITreeObject {

            final LatticeComponent latticeComponent;
            String name;

            public LatticeComponentTreeObject(String name, LatticeComponent activeComponent) {
                latticeComponent = activeComponent;
                this.name = name;
            }

            public void fillPopupMenu(JPopupMenu popupMenu) {
                JMenuItem renameMenuItem = new JMenuItem("Rename");
                renameMenuItem.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        showMessage("Not Yet Implemented");
                    }
                });
                popupMenu.add(renameMenuItem);
            }

            public void navigate() {
                setActiveLatticeComponent(latticeComponent);
                activateView(VIEW_LATTICE);
            }

            public String toString() {
                return name;
            }
        }
        ;
        final String name = "Lattice " + (index + 1);
        return new DefaultMutableTreeNode(new IconData(LATTICE_ICON,
                new LatticeComponentTreeObject(name, contextDocumentModel.getLatticeComponent(index))),
                false);
    }

    public void setFileName(String fileName) {
        contextName.setName(fileName);
        getTree().invalidate();
    }


// MENUS and TOOLBARS
    private JToolBar toolBar;

    private JToolBar createToolBar() {
        ToolBuilder toolBuilder = new ToolBuilder(getResourceManager(), getActionChain());
        return toolBuilder.createToolBar(JToolBar.HORIZONTAL);
    }

    private static ResourceManager getResourceManager() {
        return new ResourceManager(getResources());
    }

    public JToolBar getToolBar() {
        if (null == toolBar) {
            toolBar = createToolBar();
        }
        return toolBar;
    }

    private final static int defaultCxtSize = 15;

    private static Context makeDefaultContext() {
        return FCAEngineRegistry.makeContext(defaultCxtSize, defaultCxtSize);
    }

    public void activateViews() {
        activateView(VIEW_CONTEXT);
        if (hasAtLeastOneLattice()) {
            addView(VIEW_LATTICE);
        }
    }

    public ContextDocument() {
        this(makeDefaultContext());
    }

    public ContextDocument(Context cxt) {
        ActionChainUtil.putActions(actionChain, actions);
        contextDocumentModel = new ContextDocumentModel(cxt);
        latticeCollectionDecorator =  new LatticeCollectionDecorator();       
    }

    public void setParentActionChain(ActionMap chain) {
        actionChain.setParent(chain);
    }

    private void addView(String name) {
        try {
            getViewManager().addView(name);
        } catch (ViewManagerException e) {
            Trace.gui.debugm(StringUtil.stackTraceToString(e));
        }
    }

}

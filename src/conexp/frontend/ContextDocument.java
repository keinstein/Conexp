/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend;

import canvas.Figure;
import canvas.FigureDrawingCanvas;
import canvas.figures.IFigureWithCoords;
import com.gargoylesoftware.base.collections.NotificationListEvent;
import com.gargoylesoftware.base.collections.NotificationListListener;
import conexp.core.AttributeExplorer;
import conexp.core.Context;
import conexp.core.DependencySet;
import conexp.core.FCAEngineRegistry;
import conexp.core.LocalizedMessageSupplier;
import conexp.core.attrexplorationimpl.AttributeExplorerImplementation;
import conexp.frontend.attributeexploration.AttributeExplorationUserCallbackImplementation;
import conexp.frontend.components.LatticeComponent;
import conexp.frontend.components.LatticeSupplier;
import conexp.frontend.contexteditor.ContextViewPanel;
import conexp.frontend.latticeeditor.CEDiagramEditorPanel;
import conexp.frontend.latticeeditor.LatticePainterPanel;
import conexp.frontend.ruleview.AssociationRulesView;
import conexp.frontend.ruleview.ImplicationBaseCalculator;
import conexp.frontend.ruleview.ImplicationsView;
import conexp.frontend.ui.ConExpViewManager;
import conexp.frontend.ui.IViewInfo;
import conexp.frontend.ui.ViewInfo;
import conexp.frontend.ui.tree.ITreeObject;
import conexp.frontend.ui.tree.IconCellRenderer;
import conexp.frontend.ui.tree.IconData;
import conexp.frontend.util.ActionChainUtil;
import conexp.frontend.util.ResourceManager;
import conexp.frontend.util.ToolBuilder;
import conexp.util.gui.strategymodel.StrategyValueItem;
import util.Assert;
import util.FormatUtil;
import util.collection.CollectionFactory;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TooManyListenersException;


public class ContextDocument implements ActionChainBearer, Document {
    //todo: refactor me
    // problems with setting lattice component( you can't set for now lattice component,
    // because there is no way to notify views about change ).
    //todo:
    // problems with setting context (if lattice component was created and context was set after it, than lattice component
    // will not know about context
    private static ResourceBundle resContextDocument;
    private JComboBox recalculatePolicyCombo;

    static {
        resContextDocument =
                ResourceLoader.getResourceBundle(
                        "conexp/frontend/resources/ContextDocument");  //$NON-NLS-1$
    }

    private static String getResourceString(String key) {
        String string = resContextDocument.getString(key);
        //assert string != null;
        return string;
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


    //@test_public
    public void activateView(String viewName) {
        IViewInfo viewInfo = getViewInfoFromID(viewName);
        //assert null != viewInfo;
        activateView(viewInfo);
    }

    private ExtendedViewInfo getViewInfoFromID(String viewName) {
        if (VIEW_LATTICE.equals(viewName)) {
            if (!latticeViewMap.isEmpty()) {
                return (ExtendedViewInfo) latticeViewMap.values().toArray()[0];
            }
            return null;
        } else {
            return (ExtendedViewInfo) viewInfoMap.get(viewName);
        }
    }


    public ConExpViewManager getViewManager() {
        return newViewManager;
    }


    public void initViewManager() {
        newViewManager = new ConExpViewManager();
        buildViewInfoMap();
        newViewManager.addViewChangeListener(new ViewChangeListener() {
            public void viewChanged(JComponent oldView, JComponent newView) {
                if (newView == null) {
                    return;
                }
                ExtendedViewInfo viewInfo = (ExtendedViewInfo) newViewManager.getViewInfo(
                        (View) newView);
                selectAndExpandViewNodeInTree(viewInfo.getViewTreeNode());
            }

            public void cleanUp() {

            }
        });
    }

    Map viewInfoMap;

    private void buildViewInfoMap() {
        viewInfoMap = CollectionFactory.createDefaultMap();
        viewInfoMap.put(VIEW_CONTEXT, new ContextViewInfo());
        viewInfoMap.put(VIEW_ASSOCIATIONS, new AssociationViewInfo());
        viewInfoMap.put(VIEW_IMPLICATIONS, new ImplicationViewInfo());
        viewInfoMap.put(VIEW_DIAGRAM_CREATOR, new DiagramCreatorViewInfo());
    }

    ConExpViewManager newViewManager;

    /**
     * @test_public
     */
    public Container getViewContainer() {
        return getViewManager().getViewContainer();
    }


    public void addViewChangeListener(ViewChangeListener listener) {
        getViewManager().addViewChangeListener(listener);
    }

    public void removeViewChangeListener(ViewChangeListener listener) {
        getViewManager().removeViewChangeListener(listener);

    }


    private static final String DOMAIN_IMAGE = "resources/dataIcon.gif";
    public static final ImageIcon DOMAIN_ICON = ResourceLoader.getIcon(
            DOMAIN_IMAGE);

    public static final String VIEW_CONTEXT = "Context";//$NON-NLS-1$
    private static final String CONTEXT_IMAGE = "resources/contextIcon.gif";
    public static final ImageIcon CONTEXT_ICON = ResourceLoader.getIcon(
            CONTEXT_IMAGE);

    public void markClean() {
        contextDocumentModel.markClean();
    }

    private JComboBox getRecalculatePolicyCombo() {
        if (null == recalculatePolicyCombo) {
            recalculatePolicyCombo = makeRecalculationPolicyCombo();

        }
        return recalculatePolicyCombo;
    }

    private JComboBox makeRecalculationPolicyCombo() {
        StrategyValueItem recalculationPolicy = contextDocumentModel.getRecalculationPolicy();
        JComboBox combo = new JComboBox(recalculationPolicy.getDescription());
        combo.addActionListener(recalculationPolicy);
        return combo;
    }

    public ContextDocumentModel getContextDocumentModel() {
        return contextDocumentModel;
    }

    public static abstract class ExtendedViewInfo extends ViewInfo {
        public ExtendedViewInfo(String viewPlace, String viewCaption) {
            super(viewPlace, viewCaption);
        }

        DefaultMutableTreeNode viewTreeNode;

        public DefaultMutableTreeNode getViewTreeNode() {
            if (null == viewTreeNode) {
                viewTreeNode = makeViewTreeNode();
            }
            return viewTreeNode;
        }

        public boolean hasViewTreeNode() {
            return viewTreeNode != null;
        }

        abstract DefaultMutableTreeNode makeViewTreeNode();
    }

    class ContextViewInfo extends ExtendedViewInfo {
        public ContextViewInfo() {
            super(VIEW_CONTEXT, getResourceString("ContextEditorCaption"));
        }

        public View createView() {
            return makeContextTableView();
        }

        DefaultMutableTreeNode makeViewTreeNode() {
            return new DefaultMutableTreeNode(
                    new IconData(CONTEXT_ICON,
                            new GenericComponentTreeObject("Context",
                                    VIEW_CONTEXT)));
        }
    }

    private static final String LATTICE_IMAGE = "resources/latticeIcon.gif";
    public static final ImageIcon LATTICE_ICON = ResourceLoader.getIcon(
            LATTICE_IMAGE);
    public static final String VIEW_LATTICE = "Lattice";

    class LatticeViewInfo extends ExtendedViewInfo {
        LatticeSupplier supplier;

        public LatticeViewInfo(LatticeSupplier supplier) {
            super(VIEW_LATTICE, getResourceString("LatticeViewCaption"));
            this.supplier = supplier;
        }

        public View createView() {
            return makeLatticeView(supplier);
        }

        private View makeLatticeView(LatticeSupplier latticeSupplier) {
            LatticeAndEntitiesMaskSplitPane latticeSplitPane = new LatticeAndEntitiesMaskSplitPane(
                    latticeSupplier, getActionChain());
            latticeSplitPane.restorePreferences();
            return new ToolbarComponentDecorator(latticeSplitPane, false);
        }

        DefaultMutableTreeNode makeViewTreeNode() {
            int index = contextDocumentModel.getLatticeComponents().indexOf(
                    supplier);
            return makeLatticeTreeNode(index);
        }

        private DefaultMutableTreeNode makeLatticeTreeNode(int index) {
            class LatticeComponentTreeObject implements ITreeObject {

                final LatticeComponent latticeComponent;
                String name;

                public LatticeComponentTreeObject(String name,
                                                  LatticeComponent activeComponent) {
                    latticeComponent = activeComponent;
                    this.name = name;
                }

                public void fillPopupMenu(JPopupMenu popupMenu) {
/*
                    addToPopupMenu(popupMenu, "Rename", new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            showMessage("Not Yet Implemented");
                        }
                    });
*/
                    addToPopupMenu(popupMenu, "Remove", new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            removeLatticeComponent(latticeComponent);
                        }
                    });
                }


                public void navigate() {
                    setActiveLatticeComponent(latticeComponent);
                    activateView(
                            getViewInfoForLatticeComponent(latticeComponent));
                }

                public String toString() {
                    return name;
                }
            }
            ;
            final String name = "Lattice " + (index + 1);
            final LatticeComponent latticeComponent = contextDocumentModel.getLatticeComponent(
                    index);
            final DefaultMutableTreeNode ret = new DefaultMutableTreeNode(new IconData(
                    LATTICE_ICON,
                    new LatticeComponentTreeObject(name, latticeComponent)),
                    false);
            return ret;
        }

    }

    public static final String IMPLICATIONS_NODE_NAME = "Implications"; //$NON-NLS-1$
    public static final String VIEW_IMPLICATIONS = IMPLICATIONS_NODE_NAME;
    private static final String IMPLICATION_IMAGE = "resources/implicationIcon.gif";
    public static final ImageIcon IMPLICATION_ICON = ResourceLoader.getIcon(
            IMPLICATION_IMAGE);

    class ImplicationViewInfo extends ExtendedViewInfo {
        public ImplicationViewInfo() {
            super(VIEW_IMPLICATIONS, getResourceString("ImplViewCaption"));
        }

        public View createView() {
            return makeImplicationsView();
        }


        DefaultMutableTreeNode makeViewTreeNode() {
            return new DefaultMutableTreeNode(
                    new IconData(IMPLICATION_ICON,
                            new GenericComponentTreeObject(
                                    IMPLICATIONS_NODE_NAME, VIEW_IMPLICATIONS)));
        }
    }

    public static final String VIEW_ASSOCIATIONS = "Associations";//$NON-NLS-1$

    private static final String ASSOCIATIONS_IMAGE = "resources/associationRuleIcon.gif";
    public static final ImageIcon ASSOCIATIONS_ICON = ResourceLoader.getIcon(
            ASSOCIATIONS_IMAGE);

    public class AssociationViewInfo extends ExtendedViewInfo {
        public AssociationViewInfo() {
            super(VIEW_ASSOCIATIONS,
                    getResourceString("AssociationRulesViewCaption"));
        }

        public static final String ASSOCIATIONS_NODE_NAME = "Associations";

        public View createView() {
            return makeAssociationsRuleView();
        }

        DefaultMutableTreeNode makeViewTreeNode() {
            return new DefaultMutableTreeNode(
                    new IconData(ASSOCIATIONS_ICON,
                            new GenericComponentTreeObject(
                                    ASSOCIATIONS_NODE_NAME, VIEW_ASSOCIATIONS)));
        }
    }

//    public static final String VIEW_NESTED = "NestedLineDiagram";
    private static final String VIEW_DIAGRAM_CREATOR = "DiagramCreator";

    class DiagramCreatorViewInfo extends ExtendedViewInfo {
        public DiagramCreatorViewInfo() {
            super(VIEW_DIAGRAM_CREATOR, "Diagram creator");
        }

        public View createView() {
            return makeDiagramCreatorView();
        }

        DefaultMutableTreeNode makeViewTreeNode() {
            Assert.notImplemented();
            return null;
        }
    }

/*
    private static final String NESTED_LINE_DIAGRAM_TYPE = "NESTED_LINE_DIAGRAM_VIEW";
*/


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

    int activeLatticeComponentId = -1;

    public int getActiveLatticeComponentID() {
        return activeLatticeComponentId;
    }

    void setActiveLatticeComponent(LatticeComponent latticeComponent) {
        int id = contextDocumentModel.findLatticeComponent(latticeComponent);
        setActiveLatticeComponentID(id);
    }

    public void removeLatticeComponent(LatticeComponent component) {
        contextDocumentModel.removeLatticeComponent(component);
        getViewManager().removeView(getViewInfoForLatticeComponent(component));
        removeViewInfoForLattice(component);
        if (!hasAtLeastOneLattice()) {
            activateView(VIEW_CONTEXT);
        }
    }

    public void makeLatticeSnapshot() {
        final int oldId = getActiveLatticeComponentID();
        final int newId = contextDocumentModel.makeLatticeSnapshot(oldId);
        FigureDrawingCanvas canvas = getViewForLatticeComponent(oldId);
        final Collection selection = canvas.getSelection();
        FigureDrawingCanvas newCanvas = getViewForLatticeComponent(newId);
        copySelection(selection, newCanvas);
        setActiveLatticeComponentID(newId);
    }

    private static void copySelection(final Collection selection,
                                      FigureDrawingCanvas newCanvas) {
        Collection newSelection = CollectionFactory.createDefaultList();
        for (Iterator iterator = selection.iterator(); iterator.hasNext();) {
            Figure figure = (Figure) iterator.next();
            if (figure instanceof IFigureWithCoords) {
                IFigureWithCoords figureWithCoords = (IFigureWithCoords) figure;
                final Figure newFigure = newCanvas.findFigureInReverseOrderToDrawing(
                        figureWithCoords.getCenterX(),
                        figureWithCoords.getCenterY());
                if (newFigure != null) {
                    newSelection.add(newFigure);
                }
            }
        }
        newCanvas.setSelection(newSelection);
    }

    private FigureDrawingCanvas getViewForLatticeComponent(final int oldId) {
        final LatticeComponent latticeComponent = getLatticeComponent(oldId);
        return getViewForLatticeComponent(latticeComponent);
    }

    public LatticePainterPanel getViewForLatticeComponent(
            final LatticeSupplier latticeComponent) {
        final View view = getViewManager().getView(
                getViewInfoForLatticeComponent(latticeComponent));
        ToolbarComponentDecorator decorator = (ToolbarComponentDecorator) view;
        LatticeAndEntitiesMaskSplitPane latticeView = (LatticeAndEntitiesMaskSplitPane) decorator.getInner();
        return latticeView.getInnerComponent();
    }

    private void setActiveLatticeComponentID(int newId) {
        activeLatticeComponentId = newId;
        activateView(
                getViewInfoForLatticeComponent(getLatticeComponent(newId)));
    }


    public LatticeComponent getLatticeComponent(int index) {
        return contextDocumentModel.getLatticeComponent(index);
    }

    class CalcConceptCountDS extends AbstractAction {

        CalcConceptCountDS() {
            super("calcConceptsDS");//$NON-NLS-1$
        }

        public void actionPerformed(ActionEvent e) {

            final String message = FormatUtil.format(
                    getLocalizedMessageSupplier().getMessage("ConceptNumMsg"), //$NON-NLS-1$
                    contextDocumentModel.getConceptCount());
            showMessage(message);
        }


    }


    protected final ContextDocumentModel contextDocumentModel;

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

    Map latticeViewMap = CollectionFactory.createDefaultMap();

    public ExtendedViewInfo getViewInfoForLatticeComponent(
            LatticeSupplier component) {
        ExtendedViewInfo viewInfo = (ExtendedViewInfo) latticeViewMap.get(
                component);
        if (null == viewInfo) {
            viewInfo = new LatticeViewInfo(component);
            latticeViewMap.put(component, viewInfo);
        }
        return viewInfo;
    }

    private void removeViewInfoForLattice(LatticeSupplier component) {
        latticeViewMap.remove(component);
    }

    public static final String BUILD_LATTICE_COMMAND = "buildLatticeDS";
    public static final String MAKE_LATTICE_SNAPSHOT_COMMAND = "createLatticeView";
    public static final String CALCULATE_IMPLICATIONS_COMMAND = "calcImplNCS";
    public static final String CALCULATE_ASSOCIATIONS_COMMAND = "calcAssociationRules";


    //LATTICE
    class BuildLatticeDS extends AbstractAction {


        BuildLatticeDS() {
            super(BUILD_LATTICE_COMMAND);//$NON-NLS-1$
        }

        public void actionPerformed(ActionEvent e) {
            if (ContextDocument.VIEW_LATTICE.equals(
                    getViewManager().getActiveViewId())) {
                getLatticeComponent(getActiveLatticeComponentID())
                        .calculateAndLayoutLattice();
            } else {
                if (hasAtLeastOneLattice()) {
                    int latticeComponentsCount = contextDocumentModel.getLatticeComponentsCount();
                    for (int i = 0; i < latticeComponentsCount; i++) {
                        getLatticeComponent(i).calculateAndLayoutLattice();
                    }
                } else {
                    addLatticeComponent().calculateAndLayoutLattice();
                }
                final LatticeComponent latticeComponent = getLatticeComponent(
                        0);
                final ExtendedViewInfo viewInfo = getViewInfoForLatticeComponent(
                        latticeComponent);
                activateView(viewInfo);
                //       selectAndExpandViewNodeInTree(viewInfo.getViewTreeNode());

            }
        }

    }

    private void activateView(IViewInfo viewInfo) {
        getViewManager().activateView(viewInfo);
    }


    public void calculateAndLayoutLattice() {
        getOrCreateDefaultLatticeComponent().calculateAndLayoutLattice();
    }

    public synchronized LatticeComponent getOrCreateDefaultLatticeComponent() {
        final int index = 0;
        if (index == getLatticeComponentCount()) {
            return addLatticeComponent();
        }
        return getLatticeComponent(index);
    }

    public int getLatticeComponentCount() {
        return contextDocumentModel.getLatticeComponentsCount();
    }

    public synchronized LatticeComponent addLatticeComponent() {
        final int newIndex = getLatticeComponentCount();
        if (newIndex == 0) {
            activeLatticeComponentId = 0;
        }
        contextDocumentModel.addLatticeComponent();
        return getLatticeComponent(newIndex);
    }

    public void resetLatticeComponent() {
        contextDocumentModel.resetLatticeComponents();
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
            super(CALCULATE_IMPLICATIONS_COMMAND);//$NON-NLS-1$
        }

        public void actionPerformed(ActionEvent e) {
            calculateImplications();
            if (!getViewInfoFromID(VIEW_IMPLICATIONS).hasViewTreeNode()) {
                addImplicationsNodeToTree(true);
            } else {
                selectAndExpandViewNodeInTree(getImplicationsTreeNode());
            }
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
        explorer.setImplicationSet(
                getImplicationBaseCalculator().getImplications());

        AttributeExplorationUserCallbackImplementation attrExplCallback = new AttributeExplorationUserCallbackImplementation(
                getMainFrame());
        attrExplCallback.setAttributeInformationSupplier(getContext());
        explorer.setUserCallback(attrExplCallback);
        explorer.performAttributeExploration();
        showMessage(getLocalizedMessage("AttributeExplorationFinished"));
    }

    public void calculateImplications() {
        contextDocumentModel.findImplications();
    }

    public DependencySet getImplications() {
        //todo: move to COntextDOcumentModel
        return getImplicationBaseCalculator().getImplications();
    }

    private ImplicationBaseCalculator getImplicationBaseCalculator() {
        return contextDocumentModel.getImplicationBaseCalculator();
    }


    //------------------------------------------------------------
    private View makeImplicationsView() {
        DependencySetSupplier supplier = getImplicationBaseCalculator();

        ImplicationsView implicationView = new ImplicationsView(supplier,
                getActionChain());

        BaseDependencySetRecalcPolicy dependencySetRecalcPolicy = new BaseDependencySetRecalcPolicy(
                supplier) {
            protected void registerEventProcessors() {
                registerEventProcessor(DependencySetSupplier.RULE_SET_PROPERTY,
                        updateDependencySetAction);
                registerEventProcessor(DependencySetSupplier.RULE_SET_CLEARED,
                        updateDependencySetAction);
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
            super(CALCULATE_ASSOCIATIONS_COMMAND);//$NON-NLS-1$
        }

        public void actionPerformed(ActionEvent e) {
            findAssociations();
            if (!getViewInfoFromID(VIEW_ASSOCIATIONS).hasViewTreeNode()) {
                addAssociationsNodeToTree(true);
            } else {
                selectAndExpandViewNodeInTree(getAssociationsTreeNode());
            }
            activateView(VIEW_ASSOCIATIONS);
        }
    }

    //------------------------------------------------------------
    public DependencySet getAssociationRules() {
        return contextDocumentModel.getAssociationRules();
    }

    //------------------------------------------------------------
    public void findAssociations() {
        contextDocumentModel.findAssociations();
    }

    //------------------------------------------------------------
    private View makeAssociationsRuleView() {

        DependencySetSupplier supplier = contextDocumentModel.getAssociationMiner();
        AssociationRulesView associationRules = new AssociationRulesView(
                supplier, getActionChain());
        BaseDependencySetRecalcPolicy dependencySetRecalcPolicy = new BaseDependencySetRecalcPolicy(
                supplier) {
            protected void registerEventProcessors() {
                registerEventProcessor(DependencySetSupplier.RULE_SET_PROPERTY,
                        updateDependencySetAction);
                registerEventProcessor(DependencySetSupplier.RULE_SET_CLEARED,
                        updateDependencySetAction);
            }
        };
        try {
            dependencySetRecalcPolicy.addDependencySetConsumer(
                    associationRules);
        } catch (TooManyListenersException ex) {
            Assert.isTrue(false);
        }
        return new ToolbarComponentDecorator(associationRules, false);
    }

    //------------------------------------------------------------
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

//VIEW MANAGEMENT


    // DOC Visible component
    public Component getDocComponent() {
        return getViewContainer();
    }


    private boolean hasAtLeastOneLattice() {
        return getLatticeComponentCount() > 0;
    }

// document tree and connected actions

    class CreateLatticeViewAction extends AbstractAction {

        public CreateLatticeViewAction() {
            super(MAKE_LATTICE_SNAPSHOT_COMMAND);
        }

        public void actionPerformed(ActionEvent e) {
            makeLatticeSnapshot();
        }
    }

    private TreeNodeBase contextName = new TreeNodeBase("Not Empty String"); //if string is empty, it doesn't work :-((;
    private JTree contentTree;

    public DefaultMutableTreeNode getContextTreeRoot() {
        return getViewInfoFromID(VIEW_CONTEXT).getViewTreeNode();
    }

    public JTree getTree() {
        if (null == contentTree) {
            contentTree = makeTree();
        }
        return contentTree;
    }

    private JTree makeTree() {
        final JTree ret = new JTree(getDocumentTreeModel());
        ret.getSelectionModel().setSelectionMode(
                TreeSelectionModel.SINGLE_TREE_SELECTION);
        ret.setCellRenderer(new IconCellRenderer());
        ret.setSelectionPath(getTreePath());
        ret.setShowsRootHandles(true);
        ret.setEditable(false);
        ret.setExpandsSelectedPaths(true);
        ret.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                TreePath path = ret.getPathForLocation(x, y);
                if (path == null) {
                    return;
                }
                final int pathCount = path.getPathCount();
                DefaultMutableTreeNode activeItem = (DefaultMutableTreeNode)
                        path.getPathComponent(pathCount - 1);
                Object actualData = getActualDataFromTreeNode(activeItem);
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

    private static Object getActualDataFromTreeNode(
            DefaultMutableTreeNode activeItem) {
        IconData iconData = (IconData) activeItem.getUserObject();
        Object actualData = iconData.getObject();
        return actualData;
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
                    final List newValues = event.getNewValues();
                    for (Iterator iterator = newValues.iterator();
                         iterator.hasNext();) {
                        LatticeSupplier supplier = (LatticeSupplier) iterator.next();
                        doAddDirectChildToTree(
                                getViewInfoForLatticeComponent(supplier)
                                        .getViewTreeNode(),
                                true);
                    }
                }

                public void listElementsRemoved(NotificationListEvent event) {
                    LatticeSupplier component = (LatticeSupplier) event.getOldValues()
                            .get(0);
                    updateTreeWhenRemovingComponent(component);
                }

                public void listElementsChanged(NotificationListEvent event) {
                    Assert.notImplemented();
                }
            });
        }
        return documentTreeModel;
    }

    private void updateTreeWhenRemovingComponent(LatticeSupplier component) {
        getContextTreeRoot().remove(
                getViewInfoForLatticeComponent(component).getViewTreeNode());
        documentTreeModel.reload();
    }


    private DefaultTreeModel makeTreeModel() {
        DefaultMutableTreeNode domain = new DefaultMutableTreeNode(
                new IconData(DOMAIN_ICON, contextName));
        domain.add(getContextTreeRoot());

        if (hasAtLeastOneLattice()) {
            final int bound = contextDocumentModel.getLatticeComponents().size();
            for (int i = 0; i < bound; i++) {
                doAddDirectChildToTree(
                        getViewInfoForLatticeComponent(
                                contextDocumentModel.getLatticeComponent(i))
                                .getViewTreeNode(), false
                );
            }
        }
        if (implicationSetIsComputed()) {
            addImplicationsNodeToTree(false);
        }
        if (associationSetIsComputed()) {
            addAssociationsNodeToTree(false);
        }

        DefaultMutableTreeNode[] node = new DefaultMutableTreeNode[2];
        node[0] = domain;
        node[1] = getContextTreeRoot();
        treePath = new TreePath(node);

        return new DefaultTreeModel(domain);
    }

    private void doAddDirectChildToTree(final MutableTreeNode childTreeNode,
                                        boolean navigateToNode) {
        final DefaultMutableTreeNode contextTreeRoot = getContextTreeRoot();
        contextTreeRoot.add(childTreeNode);
        if (null == documentTreeModel) {
            return;
        }
        documentTreeModel.reload();
        if (navigateToNode) {
            selectAndExpandViewNodeInTree(childTreeNode);
        }
    }

    private void selectAndExpandViewNodeInTree(
            final MutableTreeNode childTreeNode) {
        Object[] path;
        if (childTreeNode == getContextTreeRoot()) {
            path =
                    new Object[]{getDocumentTreeModel().getRoot(),
                            childTreeNode};
        } else {
            path =
                    new Object[]{getDocumentTreeModel().getRoot(),
                            getContextTreeRoot(), childTreeNode};
        }
        final TreePath treePath = new TreePath(path);
        getTree().setSelectionPath(treePath);
        //getTree().expandPath(treePath);
    }

    private void addAssociationsNodeToTree(boolean navigateToNode) {
        doAddDirectChildToTree(getAssociationsTreeNode(), navigateToNode);
    }

    private void addImplicationsNodeToTree(boolean navigateToNode) {
        doAddDirectChildToTree(getImplicationsTreeNode(), navigateToNode);
    }

    private boolean implicationSetIsComputed() {
        return getImplicationBaseCalculator().isComputed();
    }

    private boolean associationSetIsComputed() {
        return contextDocumentModel.getAssociationMiner().isComputed();
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


    public MutableTreeNode getImplicationsTreeNode() {
        return getViewInfoFromID(VIEW_IMPLICATIONS).getViewTreeNode();

    }

    public MutableTreeNode getAssociationsTreeNode() {
        return getViewInfoFromID(VIEW_ASSOCIATIONS).getViewTreeNode();
    }

    private static void addToPopupMenu(JPopupMenu popupMenu, final String text,
                                       final ActionListener actionListener) {
        JMenuItem menuItem = new JMenuItem(text);
        menuItem.addActionListener(actionListener);
        popupMenu.add(menuItem);
    }

    Map latticeComponentToTreeObjectMap = CollectionFactory.createDefaultMap();


    public void setFileName(String fileName) {
        contextName.setName(fileName);
        getTree().invalidate();
    }

// MENUS and TOOLBARS
    private JToolBar toolBar;

    private JToolBar createToolBar() {
        ToolBuilder toolBuilder = new ToolBuilder(getResourceManager(),
                getActionChain());
        JToolBar toolBar = toolBuilder.createToolBar(JToolBar.HORIZONTAL);
        toolBar.add(new JLabel(" Update:"));

        toolBar.add(getRecalculatePolicyCombo());
        toolBar.add(Box.createHorizontalGlue());
        toolBar.add(Box.createHorizontalGlue());
        return toolBar;
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
            getViewManager().addView(
                    getViewInfoForLatticeComponent(
                            getOrCreateDefaultLatticeComponent()));
        }
    }

    public ContextDocument() {
        this(makeDefaultContext());
    }

    public ContextDocument(Context cxt) {
        ActionChainUtil.putActions(actionChain, actions);
        contextDocumentModel = new ContextDocumentModel(cxt);
        initViewManager();
    }

    public void setParentActionChain(ActionMap chain) {
        actionChain.setParent(chain);
    }

    public View getViewForType(String expectedView) {
        IViewInfo viewInfoFromID = getViewInfoFromID(expectedView);
        return getViewManager().doGetView(viewInfoFromID);
    }

    public boolean isModified() {
        return contextDocumentModel.isModified();
    }

    public void markDirty() {
        contextDocumentModel.markDirty();
    }

}

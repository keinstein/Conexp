/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend;

import com.visibleworkings.trace.Trace;
import conexp.core.*;
import conexp.core.attrexplorationimpl.AttributeExplorerImplementation;
import conexp.core.calculationstrategies.DepthSearchCalculator;
import conexp.core.enumcallbacks.ConceptNumCallback;
import conexp.frontend.attributeexploration.AttributeExplorationUserCallbackImplementation;
import conexp.frontend.components.AttributeMaskChangeController;
import conexp.frontend.components.LatticeComponent;
import conexp.frontend.contexteditor.ContextViewPanel;
import conexp.frontend.ruleview.AssociationRulesView;
import conexp.frontend.ruleview.ImplicationBaseCalculator;
import conexp.frontend.ruleview.ImplicationsView;
import conexp.frontend.ui.ViewManager;
import conexp.frontend.ui.ViewManagerException;
import conexp.frontend.util.ActionChainUtil;
import conexp.frontend.util.ResourceManager;
import conexp.frontend.util.ToolBuilder;
import util.Assert;
import util.FormatUtil;
import util.StringUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.util.ResourceBundle;
import java.util.TooManyListenersException;


public class ContextDocument implements ActionChainBearer, Document {
    //todo: refactor me
    // problems with setting lattice component( you can't set for now lattice component,
    // because there is no way to notify views about change ).
    //todo:
    // problems with setting context (if lattice component was created and context was set after it, than lattice component
    // will not know about context
    private static java.util.ResourceBundle resContextDocument;
    private static final String CONTEXT_EDITOR_TYPE = "CONTEXT_EDITOR";
    private static final String LINE_DIAGRAM_EDITOR_TYPE = "LINE_DIAGRAM_EDITOR";
    private static final String IMPLICATION_VIEW_TYPE = "IMPLICATION_VIEW";
    private static final String ASSOCIATIONS_VIEW_TYPE = "ASSOCIATIONS_VIEW";

/*
    private static final String NESTED_LINE_DIAGRAM_TYPE = "NESTED_LINE_DIAGRAM_VIEW";
    private static final String DIAGRAM_CREATOR_TYPE = "DIAGRAM_CREATOR_VIEW";
*/


    static {
        resContextDocument = ResourceLoader.getResourceBundle("conexp/frontend/resources/ContextDocument");  //$NON-NLS-1$
    }
    //------------------------------------------------------------

    ResourceBundle getResources() {
        return resContextDocument;
    }

    LocalizedMessageSupplier messageSupplier = new LocalizedMessageSupplier() {
        public String getMessage(String key) {
            return resContextDocument.getString(key);
        }
    };

    protected LocalizedMessageSupplier getLocalizedMessageSupplier() {
        return messageSupplier;
    }

    protected String getLocalizedMessage(String key) {
        return getLocalizedMessageSupplier().getMessage(key);
    }


    //--------------------------------------------
    DocManager docManager = null;

    public void setDocManager(DocManager docManager) {
        this.docManager = docManager;
    }

    protected JFrame getMainFrame() {
        if (null != docManager) {
            return docManager.getMainAppWindow();
        } else {
            return null;
        }
    }

    //--------------------------------------------
    boolean showMessages = true;

    public void setShowMessages(boolean newShowMessages) {
        showMessages = newShowMessages;
    }

    public void showMsg(String msg) {
        if (showMessages) {
            JOptionPane.showMessageDialog(getMainFrame(), msg);
        }
    }

    private ActionMap actionChain = new ActionMap();
    //------------------------------------------------------------
    /**
     *  Gets the ActionMap attribute of the ContextDocument object
     *
     *@return    The ActionMap value
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
//        new ShowDiagramEditor()
    };


    class CalcConceptCountDS extends AbstractAction {

        CalcConceptCountDS() {
            super("calcConceptsDS");//$NON-NLS-1$
        }

        public void actionPerformed(ActionEvent e) {

            JOptionPane.showMessageDialog(null, FormatUtil.format(
                    getLocalizedMessageSupplier().getMessage("ConceptNumMsg"),
                    actualContextDocument.getConceptCount())); //$NON-NLS-1$
        }

    }

    static class ActualContextDocument {
        Context context;

        ActualContextDocument(Context cxt) {
            setContext(cxt);
        }

        public void setContext(Context cxt) {
            this.context = cxt;
            cxt.setArrowCalculator(FCAEngineRegistry.makeArrowCalculator());
        }

        public Context getContext() {
            return context;
        }

        //----------------------------------------
        public int getConceptCount() {
            DepthSearchCalculator calc = new DepthSearchCalculator();
            return doCalculateConceptCount(calc);
        }

        //----------------------------------------
        protected int doCalculateConceptCount(ConceptCalcStrategy calc) {
            calc.setRelation(getContext().getRelation());
            ConceptNumCallback cnt = new ConceptNumCallback();
            calc.setCallback(cnt);
            calc.calculateConceptSet();
            return cnt.getConceptCount();
        }
    }

    ActualContextDocument actualContextDocument;

    //----------------------------------------
    public Context getContext() {
        return actualContextDocument.getContext();
    }

    //------------------------------------------------

    public void setContext(Context cxt) {
        actualContextDocument.setContext(cxt);
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
            calculateLattice();
            activateView(VIEW_LATTICE);
        }


    }

    public void calculateLattice() {
        getLatticeComponent().calculateAndLayoutPartialLattice();

    }

    LatticeComponent latticeComponent;

    public LatticeComponent getLatticeComponent() {
        if (null == latticeComponent) {
            resetLatticeComponent();
            getContext().addContextListener(getLatticeRecalcPolicy());
        }
        return latticeComponent;
    }

    public void resetLatticeComponent() {
        latticeComponent = makeLatticeComponentForDoc();
    }

    private LatticeComponent makeLatticeComponentForDoc() {
        return new LatticeComponent(getContext());
    }

    protected ContextListener latticeContextListener;

    private ContextListener getLatticeRecalcPolicy() {
        if (null == latticeContextListener) {
            latticeContextListener = new DefaultContextListener() {
                public void contextStructureChanged() {
                    getLatticeComponent().clearLattice();
                }

                public void relationChanged() {
                    getLatticeComponent().clearLattice();
                }

                public void contextReplaced(PropertyChangeEvent evt) {
                    getLatticeComponent().clearLattice();
                }
            };
        }
        return latticeContextListener;
    }

    //------------------------------------------------------------
    private View makeLatticeView() {
        LatticeComponent latticeSupplier = getLatticeComponent();
        latticeSupplier.getAttributeMask().addPropertyChangeListener(new AttributeMaskChangeController(latticeSupplier));
        LatticeAndAttributeMaskSplitPane latticeSplitPane = new LatticeAndAttributeMaskSplitPane(latticeSupplier, getActionChain());
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

/*
    class ShowDiagramEditor extends AbstractAction {
        public ShowDiagramEditor() {
            super("showDiagramEditor");
        }

        public void actionPerformed(ActionEvent e) {
            activateView(VIEW_DIAGRAM_CREATOR);
        }
    }
*/

    //----------------------------------------------------------

    //IMPLICATIONS
    class CalcImplicationsNCS extends AbstractAction {

        CalcImplicationsNCS() {
            super("calcImplNCS");//$NON-NLS-1$
        }

        public void actionPerformed(ActionEvent e) {
            getImplicationBaseCalculator().findDependencies();
//            showMsg(getLocalizedMessage("ImplBaseBuildedMsg")); //$NON-NLS-1$
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


    public void doAttributeExploration() {
        AttributeExplorer explorer = new AttributeExplorerImplementation();
        explorer.setContext(getContext());
        explorer.setImplicationSet(getImplicationBaseCalculator().getImplications());

        AttributeExplorationUserCallbackImplementation attrExplCallback = new AttributeExplorationUserCallbackImplementation(getMainFrame());
        attrExplCallback.setAttributeInformationSupplier(getContext());
        explorer.setUserCallback(attrExplCallback);
        explorer.performAttributeExploration();
        showMsg(getLocalizedMessage("AttributeExplorationFinished"));
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

    ImplicationBaseCalculator implicationBaseCalculator;

    public ImplicationBaseCalculator getImplicationBaseCalculator() {
        if (null == implicationBaseCalculator) {
            implicationBaseCalculator = new ImplicationBaseCalculator(getContext());
            getContext().addContextListener(getImplicationBaseRecalcPolicy());

        }
        return implicationBaseCalculator;
    }

    ContextListener implicationBaseRecalcPolicy;

    public ContextListener getImplicationBaseRecalcPolicy() {
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
            activateView(VIEW_ASSOCIATIONS);
        }
    }

    //CONTROLLER STUFF
    protected conexp.frontend.ruleview.AssociationRuleCalculator associationMiner;

    //------------------------------------------------------------
    public conexp.frontend.ruleview.AssociationRuleCalculator getAssociationMiner() {
        if (null == associationMiner) {
            associationMiner = new conexp.frontend.ruleview.AssociationRuleCalculator(getContext());
            getContext().addContextListener(getAssociationRulesRecalcPolicy());
        }
        return associationMiner;
    }

    protected ContextListener associationsContextListener;


    private ContextListener getAssociationRulesRecalcPolicy() {

        if (null == associationsContextListener) {
            associationsContextListener = new DependencySetRecalcPolicy(getAssociationMiner());
        }

        return associationsContextListener;
    }


    //------------------------------------------------------------
    public conexp.core.DependencySet getAssociationRules() {
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


    protected ViewFactory viewFactory;
    protected ViewManager viewManager;

    public static final String VIEW_CONTEXT = "Context";//$NON-NLS-1$
    public static final String VIEW_IMPLICATIONS = "Implications";//$NON-NLS-1$
    public static final String VIEW_ASSOCIATIONS = "Associations";//$NON-NLS-1$
    public static final String VIEW_LATTICE = "Lattice";
//    public static final String VIEW_NESTED = "NestedLineDiagram";
//    public static final String VIEW_DIAGRAM_CREATOR = "DiagramCreator";


    protected void registerViews() {
        try {
            viewManager.registerView(VIEW_CONTEXT, CONTEXT_EDITOR_TYPE, resContextDocument.getString("ContextEditorCaption"));//$NON-NLS-1$
            viewManager.registerView(VIEW_LATTICE, LINE_DIAGRAM_EDITOR_TYPE, resContextDocument.getString("LatticeViewCaption"));//$NON-NLS-1$
            viewManager.registerView(VIEW_IMPLICATIONS, IMPLICATION_VIEW_TYPE, resContextDocument.getString("ImplViewCaption"));
            viewManager.registerView(VIEW_ASSOCIATIONS, ASSOCIATIONS_VIEW_TYPE, resContextDocument.getString("AssociationRulesViewCaption"));//$NON-NLS-1$

//            viewManager.registerView(VIEW_NESTED, NESTED_LINE_DIAGRAM_TYPE, "Test for nested line diagram");
//            viewManager.registerView(VIEW_DIAGRAM_CREATOR, DIAGRAM_CREATOR_TYPE, "Diagram creator");


        } catch (ViewManagerException ex) {
            util.Assert.isTrue(false, "registration of view types failed");
        }
    }

    public ViewFactory getViewFactory() {
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
/*
                    if (DIAGRAM_CREATOR_TYPE.equals(type)) {
                        return makeDiagramCreatorView();
                    }
*/

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


/*
    private View makeDiagramCreatorView() {
        CEDiagramEditorPanel diagramEditorPanel = new CEDiagramEditorPanel();
        diagramEditorPanel.setDocument(this);
        JScrollPane pane = new JScrollPane(diagramEditorPanel);
        JPanelView main = new JPanelView();
        main.setLayout(new BorderLayout());
        main.add(pane, BorderLayout.CENTER);
        JToolBar toolbar = new JToolBar();
        Action actions[] = diagramEditorPanel.getActions();
        for(int i=0; i<actions.length; i++){
            toolbar.add(actions[i]);
        }
        main.add(toolbar, BorderLayout.NORTH);

        return main;
    }
*/

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
        outer.getAttributeMask().setAttributeSelected(2, false);
        outer.getAttributeMask().setAttributeSelected(3, false);

        outer.calculateAndLayoutPartialLattice();

        LatticeComponent inner = new LatticeComponent(cxt);
        inner.setLayoutEngine(new SimpleLayoutEngine());
        inner.setLayouterProvider(new MinIntersectionLayouterProvider());
        inner.getAttributeMask().setAttributeSelected(0, false);
        inner.getAttributeMask().setAttributeSelected(1, false);
        inner.calculateAndLayoutPartialLattice();

        //it can be also the set
        ConceptsCollection concepts = FCAEngineRegistry.buildLattice(cxt);

        NestedLineDiagramView view = new NestedLineDiagramView();
        view.getDrawing().buildNestedDrawing(outer.getDrawing(), inner.getDrawing(), concepts);


        return view;
    }
*/

    protected void activateView(String viewName) {
        try {
            getViewManager().activateView(viewName);
        } catch (ViewManagerException ex) {
            util.Assert.isTrue(false, "activation of view with id=" + viewName + " failed");
        }
    }

    public ViewManager getViewManager() {
        if (null == viewManager) {
            viewManager = new JTabPaneViewManager();
            viewManager.setViewFactory(getViewFactory());
            util.Assert.isTrue(null != viewManager);
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

// MENUS and TOOLBARS
    private JToolBar toolBar;

    private JToolBar createToolBar() {
        ToolBuilder toolBuilder = new ToolBuilder(getResourceManager(), getActionChain());
        return toolBuilder.createToolBar(JToolBar.HORIZONTAL);
    }

    private ResourceManager getResourceManager() {
        return new ResourceManager(getResources());
    }

    public JToolBar getToolBar() {
        if (null == toolBar) {
            toolBar = createToolBar();
        }
        return toolBar;
    }

    protected final static int defaultCxtSize = 15;

    private static Context makeDefaultContext() {
        return FCAEngineRegistry.makeContext(defaultCxtSize, defaultCxtSize);
    }

    public void activateViews() {
        activateView(VIEW_CONTEXT);
        if (!getLatticeComponent().isEmpty()) {
            addView(VIEW_LATTICE);
        }
    }

    public ContextDocument() {
        this(makeDefaultContext());
    }

    public ContextDocument(Context cxt) {
        ActionChainUtil.putActions(actionChain, actions);
        actualContextDocument = new ActualContextDocument(cxt);
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

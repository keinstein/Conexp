/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.tests;


import conexp.core.Context;
import conexp.core.DependencySet;
import conexp.core.Lattice;
import conexp.core.LatticeElement;
import conexp.core.tests.ContextReductionTest;
import conexp.core.tests.SetBuilder;
import conexp.frontend.*;
import conexp.frontend.components.LatticeComponent;
import conexp.frontend.components.LatticeSupplier;
import conexp.frontend.latticeeditor.LatticeDrawing;
import conexp.frontend.latticeeditor.figures.AbstractConceptCorrespondingFigure;
import conexp.frontend.ui.ConExpViewManager;
import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;
import util.testing.SwingTestUtil;

import javax.swing.*;
import javax.swing.tree.TreePath;
import javax.swing.tree.DefaultMutableTreeNode;
import java.util.Collection;
import java.util.Iterator;

public class ContextDocumentTest extends TestCase {
    private ContextDocument doc;

    protected void setUp() {
        doc = new ContextDocument();
    }

    public static Test suite(){
        return new SimpleLayoutTestSetup(new TestSuite(ContextDocumentTest.class));
    }

    private void performCommand(String command) {
        doc.getActionChain().get(command).actionPerformed(null);
    }


    public void testFindAssociations() {
        Context cxt = SetBuilder.makeContext(new int[][]{{1, 1, 1},
                                                         {0, 1, 1}});
        doc = new ContextDocument(cxt);
        doc.getAssociationMiner().getDependencySet(); //initialization
        doc.getContext().removeAttribute(0);
        doc.getAssociationMiner().findDependencies();
        assertSame(cxt, doc.getAssociationMiner().getDependencySet().getAttributesInformation());
    }



    static interface DependencySetSupplier {
        public void calcDependencySet(ContextDocument doc);

        public DependencySet getDependencySet(ContextDocument doc);
    }


    public void testChangeOfImplicationAfterContextModification() {
        DependencySetSupplier supplier = new DependencySetSupplier() {
            public void calcDependencySet(ContextDocument doc) {
                doc.getImplicationBaseCalculator().findDependencies();
            }

            public DependencySet getDependencySet(ContextDocument doc) {
                return doc.getImplicationBaseCalculator().getDependencySet();
            }
        };

        int[][] rel = new int[][]{{1, 1, 0},
                                  {1, 0, 1}};

        testDependencySetUpdateOnRelation(rel, supplier);
    }

    private void testDependencySetUpdateOnRelation(int[][] rel, DependencySetSupplier supplier) {
        Context cxt = SetBuilder.makeContext(rel);
        doc = new ContextDocument(cxt);
        supplier.calcDependencySet(doc);
        assertTrue(supplier.getDependencySet(doc).getSize() > 0);
        doc.getContext().removeAttribute(0);
// here different decision can be done.
// we can empty set of dependencies or recalculate it

//now we think about emptyng the implication set
        assertEquals(0, supplier.getDependencySet(doc).getSize());
    }

    public void testChangeOfAssociationRulesAfterContextModification() {
        int[][] rel = new int[][]{{1, 1, 0},
                                  {1, 0, 1}};

        DependencySetSupplier supplier = new DependencySetSupplier() {
            public void calcDependencySet(ContextDocument doc) {
                doc.findAssociations();
            }

            public DependencySet getDependencySet(ContextDocument doc) {
                return doc.getAssociationRules();
            }
        };

        testDependencySetUpdateOnRelation(rel, supplier);
    }

    public void testViewContainer() {
        assertNotNull(doc.getViewContainer());
    }


    private void expectViewActivationAfterCommand(String command, String expectedView) {
        performCommand(command);
        assertEquals("Unexpected view activated for command " + command, doc.getViewForType(expectedView), doc.getViewManager().getActiveView());
    }

    public void testGetViewManager() {
        doc.getViewManager();
    }

    public void testViewActivation() {
        doc.setShowMessages(false);
        expectViewActivationAfterCommand(ContextDocument.CALCULATE_ASSOCIATIONS_COMMAND, ContextDocument.VIEW_ASSOCIATIONS);
        expectViewActivationAfterCommand(ContextDocument.CALCULATE_IMPLICATIONS_COMMAND, ContextDocument.VIEW_IMPLICATIONS);
        expectViewActivationAfterCommand(ContextDocument.BUILD_LATTICE_COMMAND, ContextDocument.VIEW_LATTICE);
    }


    public void testViewsOptions() {
        Context cxt = SetBuilder.makeContext(new int[][]{{0, 1, 1},
                                                         {1, 0, 1},
                                                         {1, 1, 0}});
        doc = new ContextDocument(cxt);

        //createAllViews

        doc.setShowMessages(false);
        performCommand(ContextDocument.BUILD_LATTICE_COMMAND);
        performCommand(ContextDocument.CALCULATE_ASSOCIATIONS_COMMAND);
        performCommand(ContextDocument.CALCULATE_IMPLICATIONS_COMMAND);
        final Collection views = doc.getViewManager().getViews();

        for (Iterator iterator = views.iterator(); iterator.hasNext();) {
            Object o = iterator.next();
            assertTrue("view " + o.getClass().getName() + " should implement View", o instanceof View);
            assertTrue("view " + o.getClass().getName() + " should implement OptionPaneProvider", o instanceof OptionPaneProvider);

            OptionPaneProvider view = (OptionPaneProvider) o;
            assertTrue("Options shouln't be null for view ", null != view.getViewOptions());

//            assertTrue("View should have a resources", null != view.getResourceManager());
        }
    }

    public void testContextArrowCalculator() {
        doc.getContext().getUpArrow();
    }

    public void testBuildLattice() {
        Context cxt = SetBuilder.makeContext(new int[][]{{0, 1, 1},
                                                         {1, 0, 1},
                                                         {1, 1, 0}});
        doc = new ContextDocument(cxt);

        doc.calculateAndLayoutLattice();
        LatticeComponent latticeComponent = doc.getLatticeComponent(0);
        assertEquals(latticeComponent.getLattice().conceptsCount(), 8);
        latticeComponent.setUpLatticeRecalcOnMasksChange();
        latticeComponent.getAttributeMask().setSelected(2, false);
        assertEquals(4, latticeComponent.getLattice().conceptsCount());
        doc.calculateAndLayoutLattice();
        assertEquals(4, latticeComponent.getLattice().conceptsCount());
    }

    public void testReduceContextCommand() throws Exception {
        Context cxt = SetBuilder.makeContext(ContextReductionTest.BURMEISTER__EXAMPLE);
        doc = new ContextDocument(cxt);
        doc.activateView(ContextDocument.VIEW_CONTEXT);
        ToolbarComponentDecorator decorator = (ToolbarComponentDecorator) doc.getViewForType(ContextDocument.VIEW_CONTEXT);
        ViewChangeInterfaceWithConfig contextView = decorator.getInner();
        Action reduceObjects = contextView.getActionChain().get("reduceObj");
        reduceObjects.actionPerformed(null);
        assertEquals(SetBuilder.makeContext(ContextReductionTest.BURMEISTER_EXAMPLE_REDUCED).getRelation(),
                doc.getContext().getRelation());

    }

    public static void testIcons() {

        checkIcon(ContextDocument.CONTEXT_ICON);
        checkIcon(ContextDocument.LATTICE_ICON);

    }

    private static void checkIcon(ImageIcon contextIcon) {
        assertTrue("width is " + contextIcon.getIconWidth(), contextIcon.getIconWidth() > 0);
        assertTrue("height is " + contextIcon.getIconHeight(), contextIcon.getIconHeight() > 0);
    }

    public void testSnapshotLattice() {
        LatticeComponentFactory.configureTest();
        Context cxt = SetBuilder.makeContext(new int[][]{{0, 1, 1},
                                                         {1, 0, 1},
                                                         {1, 1, 0}});
        doc = new ContextDocument(cxt);

        assertEquals(true, doc.getLatticeCollection().isEmpty());
        doc.calculateAndLayoutLattice();

        assertEquals(1, doc.getLatticeCollection().size());
        final LatticeComponent first = doc.getLatticeComponent(0);

        final LatticeDrawing oldDrawing = first.getDrawing();
        LatticeDrawing oldDrawingCopy = oldDrawing.makeSetupCopy();
        oldDrawingCopy.setLattice(first.getLattice().makeCopy());
        first.copyConceptFigureCoordinatesToDrawing(oldDrawingCopy);

        doc.makeLatticeSnapshot();
        assertEquals(2, doc.getLatticeCollection().size());
        final LatticeSupplier second = doc.getLatticeComponent(1);
        assertNotSame(first, second);
        final LatticeDrawing firstDrawing = first.getDrawing();
        final LatticeDrawing secondDrawing = second.getDrawing();
        final Lattice secondLattice = secondDrawing.getLattice();
        first.getLattice().forEach(new Lattice.LatticeElementVisitor() {
            public void visitNode(LatticeElement node) {
                final AbstractConceptCorrespondingFigure firstFigure = firstDrawing.getFigureForConcept(node);
                LatticeElement otherNode = secondLattice.findLatticeElementFromOne(node.getAttribs());
                final AbstractConceptCorrespondingFigure secondFigure = secondDrawing.getFigureForConcept(otherNode);
                assertEquals(firstFigure.getCenter(), secondFigure.getCenter());
            }
        });

// assertEquals(first, second);
    }

    public void testTreeAddingLatticeNodeAfterLatticeBuilding() {
        Context cxt = SetBuilder.makeContext(new int[][]{{0, 1, 1},
                                                         {1, 0, 1},
                                                         {1, 1, 0}});
        doc = new ContextDocument(cxt);

        assertEquals(true, doc.getLatticeCollection().isEmpty());
        JTree tree = doc.getTree();
        assertEquals(2, SwingTestUtil.sizeOfTheTree(tree));
        assertEquals(0, doc.getLatticeCollection().size());

        doc.calculateAndLayoutLattice();
        assertEquals(1, doc.getLatticeCollection().size());
        assertEquals(3, SwingTestUtil.sizeOfTheTree(tree));
        doc.makeLatticeSnapshot();
        assertEquals(4, SwingTestUtil.sizeOfTheTree(tree));
    }

    public void testTreeNavigation(){
        Context cxt = SetBuilder.makeContext(new int[][]{{0, 1, 1},
                                                         {1, 0, 1},
                                                         {1, 1, 0}});
        doc = new ContextDocument(cxt);
        doc.activateViews();

        assertEquals(true, doc.getLatticeCollection().isEmpty());
        JTree tree = doc.getTree();
        assertEquals(2, SwingTestUtil.sizeOfTheTree(tree));
        assertEquals(0, doc.getLatticeCollection().size());
        final ConExpViewManager viewManager = doc.getViewManager();
        assertEquals(ContextDocument.VIEW_CONTEXT, viewManager.getActiveViewId());
        performCommand(ContextDocument.BUILD_LATTICE_COMMAND);
        assertEquals(ContextDocument.VIEW_LATTICE, viewManager.getActiveViewId());
        assertEquals(3, SwingTestUtil.sizeOfTheTree(tree));
        assertEquals(0, doc.getActiveLatticeComponentID());
        doc.makeLatticeSnapshot();
        assertEquals(4, SwingTestUtil.sizeOfTheTree(tree));
        assertEquals(1, doc.getActiveLatticeComponentID());
    }

    public void testOneNodeInTreeForImplications(){
        testOneNodeInTree(ContextDocument.CALCULATE_IMPLICATIONS_COMMAND, ContextDocument.IMPLICATIONS_NODE_NAME);
    }

    public void testOneNodeInTreeForAssociations(){
        testOneNodeInTree(ContextDocument.CALCULATE_ASSOCIATIONS_COMMAND, ContextDocument.AssociationViewInfo.ASSOCIATIONS_NODE_NAME);
    }

    public void testRemoveLatticeComponent() {
        Context cxt = SetBuilder.makeContext(new int[][]{{1, 1, 1},
                                                         {0, 1, 1}});
        doc = new ContextDocument(cxt);
        doc.activateViews();
        JTree tree = doc.getTree();
        assertEquals(2, SwingTestUtil.sizeOfTheTree(tree));
        performCommand(ContextDocument.BUILD_LATTICE_COMMAND);
        checkDocView(ContextDocument.VIEW_LATTICE);
        assertEquals(3, SwingTestUtil.sizeOfTheTree(tree));
        doc.removeLatticeComponent(doc.getLatticeComponent(0));
        assertEquals(2, SwingTestUtil.sizeOfTheTree(tree));
        checkDocView(ContextDocument.VIEW_CONTEXT);
    }

    private void checkDocView(final String expectedView) {
        assertEquals(doc.getViewForType(expectedView), doc.getViewManager().getActiveView());
    }


    private void testOneNodeInTree(final String command, final String nodeName) {
        Context cxt = SetBuilder.makeContext(new int[][]{{0, 1, 0},
                                                         {1, 0, 1},
                                                         {1, 1, 0}});
        doc = new ContextDocument(cxt);
        doc.activateViews();
        JTree tree = doc.getTree();
        performCommand(command);
        assertTrue(SwingTestUtil.treeContainsNode(tree, nodeName));
        assertEquals(1, SwingTestUtil.nodeOccurenceInTree(tree, nodeName));
        performCommand(command);
        assertEquals(1, SwingTestUtil.nodeOccurenceInTree(tree, nodeName));
    }

    public void testSelectionAndExpansionOfTree(){
        Context cxt = SetBuilder.makeContext(new int[][]{{0, 1, 0},
                                                         {1, 0, 1},
                                                         {1, 1, 0}});
        doc = new ContextDocument(cxt);


        final JTree tree = doc.getTree();
        TreePath selectionPath = tree.getSelectionPath();
        assertEquals(2, selectionPath.getPathCount());
        assertSame(doc.getContextTreeRoot(), selectionPath.getPathComponent(1));
        performCommand(ContextDocument.BUILD_LATTICE_COMMAND);
        selectionPath = tree.getSelectionPath();
        assertEquals(3, selectionPath.getPathCount());
        final DefaultMutableTreeNode treeNodeForLatticeComponent = doc.getViewInfoForLatticeComponent(doc.getLatticeComponent(0)).getViewTreeNode();
        assertTrue(SwingTestUtil.isSelectedAndExpanded(doc.getTree(),
                treeNodeForLatticeComponent));
        performCommand(ContextDocument.CALCULATE_IMPLICATIONS_COMMAND);
        assertTrue(SwingTestUtil.isSelectedAndExpanded(tree, doc.getImplicationsTreeNode()));
        performCommand(ContextDocument.CALCULATE_ASSOCIATIONS_COMMAND);
        assertTrue(SwingTestUtil.isSelectedAndExpanded(tree, doc.getAssociationsTreeNode()));
        performCommand(ContextDocument.CALCULATE_IMPLICATIONS_COMMAND);
        assertTrue(SwingTestUtil.isSelectedAndExpanded(tree, doc.getImplicationsTreeNode()));


        doc.activateView(ContextDocument.VIEW_ASSOCIATIONS);
        assertTrue(SwingTestUtil.isSelectedAndExpanded(tree, doc.getAssociationsTreeNode()));
        doc.activateView(ContextDocument.VIEW_IMPLICATIONS);
        assertTrue(SwingTestUtil.isSelectedAndExpanded(tree, doc.getImplicationsTreeNode()));


        doc.activateView(ContextDocument.VIEW_CONTEXT);
        assertTrue(SwingTestUtil.isSelectedAndExpanded(tree, doc.getContextTreeRoot()));

    }

}

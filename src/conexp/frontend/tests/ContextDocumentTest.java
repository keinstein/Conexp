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
import conexp.frontend.ui.ViewManager;
import junit.framework.TestCase;
import util.testing.SwingTestUtil;

import javax.swing.*;
import java.util.Collection;
import java.util.Iterator;

public class ContextDocumentTest extends TestCase {
    private ContextDocument doc;

    protected void setUp() {
        doc = new ContextDocument();
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
        assertEquals("Unexpected view activated for command " + command, doc.getViewManager().getView(expectedView), doc.getViewManager().getActiveView());
    }

    public void testGetViewManager() {
        doc.getViewManager();
    }

    public void testViewActivation() {
        doc.setShowMessages(false);
        expectViewActivationAfterCommand("calcAssociationRules", ContextDocument.VIEW_ASSOCIATIONS);
        expectViewActivationAfterCommand("calcImplNCS", ContextDocument.VIEW_IMPLICATIONS);
        expectViewActivationAfterCommand("buildLatticeDS", ContextDocument.VIEW_LATTICE);
    }


    public void testViewsOptions() {
        Context cxt = SetBuilder.makeContext(new int[][]{{0, 1, 1},
                                                         {1, 0, 1},
                                                         {1, 1, 0}});
        doc = new ContextDocument(cxt);

        //createAllViews

        doc.setShowMessages(false);
        performCommand("buildLatticeDS");
        performCommand("calcAssociationRules");
        performCommand("calcImplNCS");
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
        doc.getViewManager().addView(ContextDocument.VIEW_CONTEXT);
        ToolbarComponentDecorator decorator = (ToolbarComponentDecorator) doc.getViewManager().getView(ContextDocument.VIEW_CONTEXT);
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
        Context cxt = SetBuilder.makeContext(new int[][]{{0, 1, 1},
                                                         {1, 0, 1},
                                                         {1, 1, 0}});
        doc = new ContextDocument(cxt);

        assertEquals(true, doc.getLatticeCollection().isEmpty());
        doc.calculateAndLayoutLattice();
        assertEquals(1, doc.getLatticeCollection().size());
        doc.makeLatticeSnapshot();
        assertEquals(2, doc.getLatticeCollection().size());
        final LatticeComponent first = doc.getLatticeComponent(0);
        final LatticeSupplier second = doc.getLatticeComponent(1);
        assertNotSame(first, second);
        final LatticeDrawing firstDrawing = first.getDrawing();
        final LatticeDrawing secondDrawing = second.getDrawing();
        first.getLattice().forEach(new Lattice.LatticeElementVisitor() {
            public void visitNode(LatticeElement node) {
                final AbstractConceptCorrespondingFigure firstFigure = firstDrawing.getFigureForConcept(node);
                final AbstractConceptCorrespondingFigure secondFigure = secondDrawing.getFigureForConcept(node);
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
        final ViewManager viewManager = doc.getViewManager();
        assertEquals(ContextDocument.VIEW_CONTEXT, viewManager.getActiveViewId());
        performCommand("buildLatticeDS");
        assertEquals(ContextDocument.VIEW_LATTICE, viewManager.getActiveViewId());
        assertEquals(3, SwingTestUtil.sizeOfTheTree(tree));
        assertEquals(0, doc.getActiveLatticeComponentID());
        doc.makeLatticeSnapshot();
        assertEquals(4, SwingTestUtil.sizeOfTheTree(tree));
        assertEquals(1, doc.getActiveLatticeComponentID());
    }

    public void testOneNodeInTreeForImplications(){
        testOneNodeInTree("calcImplNCS", ContextDocument.IMPLICATIONS_NODE_NAME);
    }

    public void testOneNodeInTreeForAssociations(){
        testOneNodeInTree("calcAssociationRules", ContextDocument.ASSOCIATIONS_NODE_NAME);
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

}

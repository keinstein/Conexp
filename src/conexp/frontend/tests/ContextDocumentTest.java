/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/

package conexp.frontend.tests;


import conexp.core.Context;
import conexp.core.DependencySet;
import conexp.core.tests.SetBuilder;
import conexp.frontend.ContextDocument;
import conexp.frontend.components.AttributeMaskChangeController;
import conexp.frontend.components.LatticeComponent;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ContextDocumentTest extends TestCase {

    public static Test suite() {
        return new TestSuite(ContextDocumentTest.class);
    }

    ContextDocument doc;

    protected void setUp() {
        doc = new ContextDocument();
    }

    /**
     * Insert the method's description here.
     * Creation date: (21.11.00 0:47:38)
     */
    public void testViewContainer() {
        assertNotNull(doc.getViewContainer());
    }

    /**
     * Insert the method's description here.
     * Creation date: (21.11.00 0:15:19)
     */
    public void testViewsOptions() {
        //TODO - move this test to test View Factory
/*	contextComponent.getDocComponent();
	for(int i=contextComponent.views.size(); --i>=0;){
		isTrue(i+"th view should implement ViewChangeInterface", contextComponent.views.get(i) instanceof ViewChangeInterface);
		isTrue("Options shouln't be null for "+i+"th view ",null!=((ViewChangeInterface)contextComponent.views.get(i)).getViewOptions());
		isTrue(i+"th view should have a resources",null!=((ViewChangeInterface)contextComponent.views.get(i)).getResources());
	}*/
    }

    /**
     * Insert the method's description here.
     * Creation date: (14.05.01 23:36:03)
     */
    public void expectViewActivationAfterCommand(String command, String expectedView) {
        performCommand(command);
        assertEquals("Unexpected view activated for command " + command, doc.getViewManager().getView(expectedView), doc.getViewManager().getActiveView());
    }


    /**
     * Insert the method's description here.
     * Creation date: (14.05.01 23:11:34)
     */
    protected void performCommand(String command) {
        doc.getActionChain().get(command).actionPerformed(null);
    }


    /**
     * Insert the method's description here.
     * Creation date: (08.05.01 21:36:47)
     */
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


    /**
     * Insert the method's description here.
     * Creation date: (15.05.01 0:10:24)
     */
    public void testGetViewManager() {
        doc.getViewManager();
    }

    /**
     * Insert the method's description here.
     * Creation date: (14.05.01 23:33:07)
     */
    public void testViewActivation() {
        doc.setShowMessages(false);
        expectViewActivationAfterCommand("calcAssociationRules", doc.VIEW_ASSOCIATIONS);
        expectViewActivationAfterCommand("calcImplNCS", doc.VIEW_IMPLICATIONS);
        expectViewActivationAfterCommand("buildLatticeDS", doc.VIEW_LATTICE);
    }

    public void testContextArrowCalculator() {
        doc.getContext().getUpArrow();
    }

    public void testBuildLattice() {
        Context cxt = SetBuilder.makeContext(new int[][]{{0, 1, 1},
                                                         {1, 0, 1},
                                                         {1, 1, 0}});
        doc = new ContextDocument(cxt);

        doc.calculateLattice();
        LatticeComponent latticeComponent = doc.getLatticeComponent();
        assertEquals(latticeComponent.getLattice().conceptsCount(), 8);
        latticeComponent.getAttributeMask().addPropertyChangeListener(new AttributeMaskChangeController(latticeComponent));
        latticeComponent.getAttributeMask().setAttributeSelected(2, false);
        assertEquals(4, latticeComponent.getLattice().conceptsCount());
        doc.calculateLattice();
        assertEquals(4, latticeComponent.getLattice().conceptsCount());
    }

}

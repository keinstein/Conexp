/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.io.tests;

import canvas.figures.IFigureWithCoords;
import conexp.core.Context;
import conexp.core.ExtendedContextEditingInterface;
import conexp.core.Lattice;
import conexp.core.LatticeElement;
import conexp.core.tests.SetBuilder;
import conexp.frontend.ContextDocument;
import conexp.frontend.ContextDocumentModel;
import conexp.frontend.DocumentLoader;
import conexp.frontend.DocumentWriter;
import conexp.frontend.SetProvidingEntitiesMask;
import conexp.frontend.components.LatticeComponent;
import conexp.frontend.io.ConExpXMLReader;
import conexp.frontend.io.ConExpXMLWriter;
import conexp.frontend.latticeeditor.LatticeCanvas;
import conexp.frontend.latticeeditor.LatticeDrawing;
import conexp.frontend.latticeeditor.figures.AbstractConceptCorrespondingFigure;
import conexp.frontend.latticeeditor.labelingstrategies.LabelingStrategiesKeys;
import conexp.frontend.tests.SimpleLayoutTestSetup;
import conexp.util.gui.strategymodel.StrategyValueItem;
import junit.framework.Test;
import junit.framework.TestSuite;
import util.testing.SwingTestUtil;
import util.testing.TestUtil;

import javax.swing.JTree;
import java.awt.geom.Point2D;
import java.beans.PropertyVetoException;

public class ConExpXMLReaderWriterTest extends ContextReaderWriterPairTest {
    private static final int[][] TEST_RELATION_2x3 = new int[][]{{0, 1},
            {1, 0},
            {1, 1}};
    static final int[][] TWO_ELEMENT_CHAIN_CONTEXT = new int[][]{{0}, {1}};
    static final int[][] TWO_BY_TWO_CHAIN_CONTEXT = new int[][]{{0, 1}, {1, 1}};

    public static Test suite() {
        return new SimpleLayoutTestSetup(new TestSuite(ConExpXMLReaderWriterTest.class));
    }

    protected DocumentLoader makeDocumentLoader() {
        return new ConExpXMLReader();
    }

    protected DocumentWriter makeDocumentWriter() {
        return new ConExpXMLWriter();
    }

    private Context cxt;
    private ContextDocument doc;

    private void setUpFullLatticeCase() {
        cxt = SetBuilder.makeContext(TWO_ELEMENT_CHAIN_CONTEXT);
        doc = new ContextDocument(cxt);
        doc.addLatticeComponent().calculateLattice();
    }

    private void setUpPartialLatticeCase() {
        cxt = SetBuilder.makeContext(new int[][]{{0, 1},
                {1, 0}});
        doc = new ContextDocument(cxt);
        final LatticeComponent latticeComponent = doc.addLatticeComponent();
        final SetProvidingEntitiesMask attributeMask =
                latticeComponent.getAttributeMask();
        assertEquals(2, attributeMask.getCount());
        attributeMask.setSelected(1, false);
        latticeComponent.calculateLattice();
    }

    private void setUpPartialObjectLatticeCase() {
        cxt = SetBuilder.makeContext(TEST_RELATION_2x3);
        doc = new ContextDocument(cxt);
        final LatticeComponent latticeComponent = doc.addLatticeComponent();
        final SetProvidingEntitiesMask attributeMask =
                latticeComponent.getAttributeMask();
        assertEquals(2, attributeMask.getCount());
        attributeMask.setSelected(1, false);
        SetProvidingEntitiesMask objectMask = latticeComponent.getObjectMask();
        assertEquals(3, objectMask.getCount());
        objectMask.setSelected(1, false);

        latticeComponent.getDrawing().setObjectLabelingStrategyKey(
                LabelingStrategiesKeys.ALL_OBJECTS_LABELING_STRATEGY);

        latticeComponent.calculateLattice();
    }


    private void setUpTwoLatticeCase() {
        cxt = SetBuilder.makeContext(TEST_RELATION_2x3);
        doc = new ContextDocument(cxt);
        LatticeComponent latticeComponent = doc.addLatticeComponent();
        latticeComponent.getDrawing().setAttributeLabelingStrategyKey(
                LabelingStrategiesKeys.ALL_ATTRIBS_LABELING_STRATEGY_KEY);


        latticeComponent.calculateAndLayoutLattice();

        latticeComponent = doc.addLatticeComponent();
        final SetProvidingEntitiesMask attributeMask =
                latticeComponent.getAttributeMask();
        assertEquals(2, attributeMask.getCount());
        attributeMask.setSelected(1, false);
        SetProvidingEntitiesMask objectMask = latticeComponent.getObjectMask();
        assertEquals(3, objectMask.getCount());
        objectMask.setSelected(1, false);

        latticeComponent.getDrawing().setObjectLabelingStrategyKey(
                LabelingStrategiesKeys.ALL_OBJECTS_LABELING_STRATEGY);

        latticeComponent.calculateLattice();
    }

    public void testCaseWithTwoLattices() {
        setUpTwoLatticeCase();
        doTestWriteAndReadForDocWithLattice(doc);
    }


    public void testContextAndLineDiagramWithObjectAndAttributesLabelsFullLattice() {
        setUpFullLatticeCase();
        doTestWriteAndReadForContextDocWithLineDiagramWithObjectsAndAttributeLabels(
                doc, cxt);
    }

    public void testContextAndLineDiagramWithConceptLabelsFullLattice() {
        setUpFullLatticeCase();
        doTestWriteAndReadForDocumentWithConceptsLabels(doc, cxt);
    }

    public void testContextAndLineDiagramWithObjectAndAttributesLabelsPartialLattice() {
        setUpPartialLatticeCase();
        doTestWriteAndReadForContextDocWithLineDiagramWithObjectsAndAttributeLabels(
                doc, cxt);
    }

    public void testContextAndLineDiagramWithConceptLabelsPartialLattice() {
        setUpPartialLatticeCase();
        doTestWriteAndReadForDocumentWithConceptsLabels(doc, cxt);
    }

    public void testLoadSavePartialLattice() {
        setUpPartialLatticeCase();
        doTestWriteAndReadForDocWithLattice(doc);
    }

    public void testLoadSaveFullLattice() {
        setUpFullLatticeCase();
        doTestWriteAndReadForDocWithLattice(doc);
    }

    public void testLoadSaveLabelsSize() {
        setUpFullLatticeCase();
        try {
            doc.getOrCreateDefaultLatticeComponent().getDrawing()
                    .getPainterOptions().getLabelsFontSizeValue().setValue(16);
        } catch (PropertyVetoException e) {
            TestUtil.reportUnexpectedException(e);
        }
        ContextDocument loadedDoc = doTestWriteAndReadForDocWithLattice(doc);
        assertEquals(16, loadedDoc.getOrCreateDefaultLatticeComponent()
                .getDrawing().getPainterOptions()
                .getLabelsFontSizeValue().getValue());
    }


    public void testLoadSavePartialObjectLattice() {
        setUpPartialObjectLatticeCase();
        doTestWriteAndReadForDocWithLattice(doc);
    }

    private void doTestWriteAndReadForContextDocWithLineDiagramWithObjectsAndAttributeLabels(
            ContextDocument doc, ExtendedContextEditingInterface cxt) {
        final LatticeComponent defaultLatticeComponent =
                doc.getOrCreateDefaultLatticeComponent();
        Lattice lattice = defaultLatticeComponent.getLattice();

        LatticeDrawing drawing = defaultLatticeComponent.getDrawing();
        drawing.getFigureForConcept(lattice.getZero()).setCoords(10, 10);
        drawing.getFigureForConcept(lattice.getOne()).setCoords(10, 300);
        assertTrue(drawing.setAttributeLabelingStrategyKey(
                LabelingStrategiesKeys.ALL_ATTRIBS_LABELING_STRATEGY_KEY));
        assertTrue(drawing.setObjectLabelingStrategyKey(
                LabelingStrategiesKeys.ALL_OBJECTS_LABELING_STRATEGY));

        drawing.getLabelForAttribute(cxt.getAttribute(0)).setCoords(15, 20);
        drawing.getLabelForObject(cxt.getObject(0)).setCoords(30, 40);

        ContextDocument loadedDoc = writeAndReadContextDoc(doc);
        ExtendedContextEditingInterface loadedContext = loadedDoc.getContext();
        assertEquals(cxt, loadedContext);

        final LatticeComponent loadedLatticeComponent =
                loadedDoc.getOrCreateDefaultLatticeComponent();
        Lattice loadedLattice = loadedLatticeComponent.getLattice();
        assertTrue("Lattice should be restored", !loadedLattice.isEmpty());
        assertTrue("Lattice should be equal to saved",
                lattice.isEqual(loadedLattice));

        final LatticeDrawing loadedDrawing =
                loadedLatticeComponent.getDrawing();
        assertEquals(drawing.getAttributeLabelingStrategyKey(),
                loadedDrawing.getAttributeLabelingStrategyKey());
        assertEquals(drawing.getObjectLabelingStrategyKey(),
                loadedDrawing.getObjectLabelingStrategyKey());

        assertEquals(new Point2D.Double(10, 10), loadedDrawing
                .getFigureForConcept(loadedLattice.getZero()).getCenter());
        assertEquals(new Point2D.Double(10, 300), loadedDrawing
                .getFigureForConcept(loadedLattice.getOne()).getCenter());

        assertEquals(
                drawing.getLabelForAttribute(cxt.getAttribute(0)).getCenter(),
                loadedDrawing.getLabelForAttribute(
                        loadedContext.getAttribute(0)).getCenter());
        assertEquals(drawing.getLabelForObject(cxt.getObject(0)).getCenter(),
                loadedDrawing.getLabelForObject(
                        loadedContext.getObject(0)).getCenter());
    }


    public void testStoringSelection() {
        setUpFullLatticeCase();
        LatticeComponent latticeComponent = doc.getLatticeComponent(0);
        LatticeCanvas view = doc.getViewForLatticeComponent(latticeComponent);
        LatticeElement zero = latticeComponent.getLattice().getZero();
        AbstractConceptCorrespondingFigure figureForConcept =
                view.getFigureForConcept(zero);
        view.selectFigure(figureForConcept);
        assertTrue(view.hasSelection());
        ContextDocument loadedDoc = writeAndReadContextDoc(doc);
        LatticeComponent loadedLatticeComponent =
                loadedDoc.getLatticeComponent(0);
        LatticeCanvas loadedView =
                loadedDoc.getViewForLatticeComponent(loadedLatticeComponent);
        assertTrue(loadedView.hasSelection());
    }

    private void doTestWriteAndReadForDocumentWithConceptsLabels(
            ContextDocument doc, ExtendedContextEditingInterface cxt) {
        final LatticeComponent defaultLatticeComponent =
                doc.getOrCreateDefaultLatticeComponent();
        Lattice lattice = defaultLatticeComponent.getLattice();

        LatticeDrawing drawing = defaultLatticeComponent.getDrawing();
        drawing.getFigureForConcept(lattice.getZero()).setCoords(10, 10);
        drawing.getFigureForConcept(lattice.getOne()).setCoords(10, 300);

        assertTrue(drawing.setAttributeLabelingStrategyKey(
                LabelingStrategiesKeys.NO_ATTRIBS_LABELING_STRATEGY));
        assertTrue(drawing.setObjectLabelingStrategyKey(
                LabelingStrategiesKeys.OBJECTS_COUNT_LABELING_STRATEGY));

        drawing.getLabelForConcept(lattice.getZero()).setCoords(15, 20);

        ContextDocument loadedDoc = writeAndReadContextDoc(doc);
        ExtendedContextEditingInterface loadedContext = loadedDoc.getContext();
        assertEquals(cxt, loadedContext);

        final LatticeComponent loadedLatticeComponent =
                loadedDoc.getOrCreateDefaultLatticeComponent();
        Lattice loadedLattice = loadedLatticeComponent.getLattice();
        assertTrue("Lattice should be restored", !loadedLattice.isEmpty());
        assertTrue("Lattice should be equal to saved",
                lattice.isEqual(loadedLattice));

        final LatticeDrawing loadedDrawing =
                loadedLatticeComponent.getDrawing();
        assertEquals(drawing.getAttributeLabelingStrategyKey(),
                loadedDrawing.getAttributeLabelingStrategyKey());
        assertEquals(drawing.getObjectLabelingStrategyKey(),
                loadedDrawing.getObjectLabelingStrategyKey());

        assertEquals(new Point2D.Double(10, 10), loadedDrawing
                .getFigureForConcept(loadedLattice.getZero()).getCenter());
        assertEquals(new Point2D.Double(10, 300), loadedDrawing
                .getFigureForConcept(loadedLattice.getOne()).getCenter());

        assertEquals(drawing.getLabelForConcept(lattice.getZero()).getCenter(),
                loadedDrawing.getLabelForConcept(
                        loadedLattice.getZero()).getCenter());
    }

    private ContextDocument doTestWriteAndReadForDocWithLattice(
            ContextDocument doc) {
        ContextDocument loadedDoc = writeAndReadContextDoc(doc);
        checkDocumentEquality(doc, loadedDoc);
        return loadedDoc;
    }

    private static void checkDocumentEquality(ContextDocument document,
                                              ContextDocument loadedDoc) {
        assertEquals("Context in original and loaded document differ",
                document.getContext(),
                loadedDoc.getContext());
        assertEquals(
                "Lattice count in stored and loaded documents should be equal",
                document.getLatticeComponentCount(),
                loadedDoc.getLatticeComponentCount());

        for (int i = 0; i < document.getLatticeComponentCount(); i++) {
            Lattice lattice = document.getLatticeComponent(i).getLattice();


            Lattice loadedLattice =
                    loadedDoc.getLatticeComponent(i).getLattice();
            assertTrue("Lattice should be restored", !loadedLattice.isEmpty());
            assertTrue("Lattice should be equal to saved",
                    lattice.isEqual(loadedLattice));
        }
    }


    public void testStoringRecalcPolicy() {
        cxt = SetBuilder.makeContext(TWO_ELEMENT_CHAIN_CONTEXT);
        doc = new ContextDocument(cxt);
        ContextDocumentModel contextDocumentModel =
                doc.getContextDocumentModel();
        contextDocumentModel.setRecomputeDependentRecalcPolicy();
        ContextDocument loadedDoc = writeAndReadContextDoc(doc);
        assertEquals("Recalculation policy value differs",
                doc.getContextDocumentModel().getRecalculationPolicy(),
                loadedDoc.getContextDocumentModel().getRecalculationPolicy());
    }

    public void testStoringLayoutMode() {
        cxt = SetBuilder.makeContext(TWO_ELEMENT_CHAIN_CONTEXT);
        doc = new ContextDocument(cxt);
        ContextDocumentModel documentModel = doc.getContextDocumentModel();
        documentModel.addLatticeComponent();
        LatticeComponent latticeComponent =
                documentModel.getLatticeComponent(0);
        latticeComponent.calculateAndLayoutLattice();
        LatticeDrawing drawing = latticeComponent.getDrawing();
        StrategyValueItem layoutStrategyItem = drawing.getPainterOptions()
                .getLatticePainterDrawStrategyContext().getLayoutStrategyItem();
        assertTrue("Layout was not set",
                layoutStrategyItem.setValueByKey("FreeseLayout"));
        ContextDocument loadedDoc = writeAndReadContextDoc(doc);
        StrategyValueItem loadedLayoutStrategyItem =
                loadedDoc.getContextDocumentModel().
                        getLatticeComponent(0).
                        getDrawing().
                        getPainterOptions().
                        getLatticePainterDrawStrategyContext().
                        getLayoutStrategyItem();
        assertEquals(layoutStrategyItem.getStrategyKey(),
                loadedLayoutStrategyItem.getStrategyKey());

    }


    public void testStoringEdgeSizeMode() {
        cxt = SetBuilder.makeContext(TWO_ELEMENT_CHAIN_CONTEXT);
        doc = new ContextDocument(cxt);
        ContextDocumentModel documentModel = doc.getContextDocumentModel();
        documentModel.addLatticeComponent();
        LatticeComponent latticeComponent =
                documentModel.getLatticeComponent(0);
        latticeComponent.calculateAndLayoutLattice();
        LatticeDrawing drawing = latticeComponent.getDrawing();
        assertTrue(drawing.getEdgeSizeCalcStrategyItem().setValueByKey(
                "ObjectFlowEdgeSizeCalcStrategy"));
        ContextDocument loadedDoc = writeAndReadContextDoc(doc);
        LatticeDrawing loadedDrawing = loadedDoc.getContextDocumentModel().
                getLatticeComponent(0).getDrawing();
        assertEquals(drawing.getEdgeSizeCalcStrategyItem().getStrategyKey(),
                loadedDrawing.getEdgeSizeCalcStrategyItem().getStrategyKey());

    }

    public void testStoringWithMultiLabels() {
        cxt = SetBuilder.makeContext(new int[][]{{0, 0},
                {0, 0}});
        doc = new ContextDocument(cxt);
        doc.calculateAndLayoutLattice();
        LatticeDrawing drawing = doc.getLatticeComponent(0).getDrawing();
        drawing.
                setAttributeLabelingStrategyKey(LabelingStrategiesKeys.
                        ATTRIBS_MULTI_LABELING_STRATEGY_KEY);
        IFigureWithCoords upLabelForConcept =
                drawing.getUpLabelForConcept(drawing.getLattice().getZero());
        upLabelForConcept.moveBy(3, 4);

        drawing.setObjectLabelingStrategyKey(
                LabelingStrategiesKeys.NO_OBJECTS_LABELING_STRATEGY);

        ContextDocument loadedDoc = writeAndReadContextDoc(doc);
        LatticeDrawing loadedDrawing =
                loadedDoc.getLatticeComponent(0).getDrawing();
        assertEquals(drawing.getAttributeLabelingStrategyKey(),
                loadedDrawing.getAttributeLabelingStrategyKey());
        assertEquals(drawing.getObjectLabelingStrategyKey(),
                drawing.getObjectLabelingStrategyKey());
        IFigureWithCoords loadedUpLabelForConcept = loadedDrawing
                .getUpLabelForConcept(loadedDrawing.getLattice().getZero());
        assertEquals(upLabelForConcept.getCenter(),
                loadedUpLabelForConcept.getCenter());

        //"Todo: check storing AttribsMultiLineLabeling and ObjectsMultiLineLabeling
        // at the same time;

        //todo: maybe - write tests that will check interaction of every present strategy
        //with other one present
    }


    public void testRestoringLatticeStructureInTree() {
        cxt = SetBuilder.makeContext(new int[][]{{0, 1, 1},
                {1, 0, 1},
                {1, 1, 0}});
        doc = new ContextDocument(cxt);
        assertEquals(true, doc.getLatticeCollection().isEmpty());
        JTree tree = doc.getTree();
        assertEquals(2, SwingTestUtil.sizeOfTheTree(tree));
        assertEquals(0, doc.getLatticeCollection().size());

        doc.calculateAndLayoutLattice();
        assertEquals(1, doc.getLatticeCollection().size());
        assertEquals(3, SwingTestUtil.sizeOfTheTree(doc.getTree()));

        ContextDocument loadedDoc = writeAndReadContextDoc(doc);
        assertEquals(1, loadedDoc.getLatticeCollection().size());
        assertEquals(3, SwingTestUtil.sizeOfTheTree(loadedDoc.getTree()));
    }


    /*This functionality is deferred to the next release */
    public void XXXtestStoringImplication() {
        cxt = SetBuilder.makeContext(TWO_BY_TWO_CHAIN_CONTEXT);
        doc = new ContextDocument(cxt);
        doc.calculateImplications();
        assertEquals(1, doc.getImplications().dependencies().size());
        ContextDocument loadedDoc = writeAndReadContextDoc(doc);
        assertEquals(doc.getImplications(), loadedDoc.getImplications());

    }
}



package conexp.frontend.io.tests;

import conexp.core.Context;
import conexp.core.ExtendedContextEditingInterface;
import conexp.core.Lattice;
import conexp.core.tests.SetBuilder;
import conexp.frontend.ContextDocument;
import conexp.frontend.DocumentLoader;
import conexp.frontend.DocumentWriter;
import conexp.frontend.SetProvidingAttributeMask;
import conexp.frontend.components.LatticeComponent;
import conexp.frontend.io.ConExpXMLReader;
import conexp.frontend.io.ConExpXMLWriter;
import conexp.frontend.latticeeditor.LatticeDrawing;
import conexp.frontend.latticeeditor.labelingstrategies.LabelingStrategiesKeys;
import junit.framework.Test;
import junit.framework.TestSuite;

import java.awt.geom.Point2D;

public class ConExpXMLReaderWriterTest extends ContextReaderWriterPairTest {
    private static final Class THIS = ConExpXMLReaderWriterTest.class;

    public static Test suite() {
        return new TestSuite(THIS);
    }

    protected DocumentLoader makeDocumentLoader() {
        return new ConExpXMLReader();
    }

    protected DocumentWriter makeDocumentWriter() {
        return new ConExpXMLWriter();
    }

    Context cxt;
    ContextDocument doc;

    private void setUpFullLatticeCase() {
        cxt = SetBuilder.makeContext(new int[][]{{0},
                                                         {1}});
        doc = new ContextDocument();
        doc.setContext(cxt);
        doc.getLatticeComponent().calculateLattice();
    }

    private void setUpPartialLatticeCase() {
        cxt = SetBuilder.makeContext(new int[][]{{0, 1},
                                                         {1, 0}});
        doc = new ContextDocument();
        doc.setContext(cxt);
        final LatticeComponent latticeComponent = doc.getLatticeComponent();
        final SetProvidingAttributeMask attributeMask = latticeComponent.getAttributeMask();
        assertEquals(2,attributeMask.getAttributeCount());
        attributeMask.setAttributeSelected(1, false);
        latticeComponent.calculatePartialLattice();
    }

    public void testContextAndLineDiagramWithObjectAndAttributesLabelsFullLattice() {
        setUpFullLatticeCase();
        doTestWriteAndReadForCotextDocWithLineDiagramWithObjectsAndAttributeLabels(doc, cxt);
    }

    public void testContextAndLineDiagramWithConceptLabelsFullLattice() {
        setUpFullLatticeCase();
        doTestWriteAndReadForDocumentWithConceptsLabels(doc, cxt);
    }

    public void testContextAndLineDiagramWithObjectAndAttributesLabelsPartialLattice() {
        setUpPartialLatticeCase();
        doTestWriteAndReadForCotextDocWithLineDiagramWithObjectsAndAttributeLabels(doc, cxt);
    }

    public void testContextAndLineDiagramWithConceptLabelsPartialLattice() {
        setUpPartialLatticeCase();
        doTestWriteAndReadForDocumentWithConceptsLabels(doc, cxt);
    }

    public void testLoadSavePartialLattice(){
        setUpPartialLatticeCase();
        doTestWriteAndReadForDocWithLattice(doc, cxt);
    }

    public void testLoadSaveFullLattice(){
        setUpFullLatticeCase();
        doTestWriteAndReadForDocWithLattice(doc, cxt);
    }


    private void doTestWriteAndReadForCotextDocWithLineDiagramWithObjectsAndAttributeLabels(ContextDocument doc, ExtendedContextEditingInterface cxt) {
        Lattice lattice = doc.getLatticeComponent().getLattice();

        LatticeDrawing drawing = doc.getLatticeComponent().getDrawing();
        drawing.getFigureForConcept(lattice.getZero()).setCoords(10, 10);
        drawing.getFigureForConcept(lattice.getOne()).setCoords(10, 300);
        assertTrue(drawing.setAttributeLabelingStrategyKey(LabelingStrategiesKeys.ALL_ATTRIBS_LABELING_STRATEGY_KEY));
        assertTrue(drawing.setObjectLabelingStrategyKey(LabelingStrategiesKeys.ALL_OBJECTS_LABELING_STRATEGY));

        drawing.getLabelForAttribute(cxt.getAttribute(0)).setCoords(15, 20);
        drawing.getLabelForObject(cxt.getObject(0)).setCoords(30, 40);

        ContextDocument loadedDoc = writeAndReadContextDoc(doc);
        ExtendedContextEditingInterface loadedContext = loadedDoc.getContext();
        assertEquals(cxt, loadedContext);

        Lattice loadedLattice = loadedDoc.getLatticeComponent().getLattice();
        assertTrue("Lattice should be restored", !loadedLattice.isEmpty());
        assertTrue("Lattice should be equal to saved", lattice.isEqual(loadedLattice));

        final LatticeDrawing loadedDrawing = loadedDoc.getLatticeComponent().getDrawing();
        assertEquals(drawing.getAttributeLabelingStrategyKey(), loadedDrawing.getAttributeLabelingStrategyKey());
        assertEquals(drawing.getObjectLabelingStrategyKey(), loadedDrawing.getObjectLabelingStrategyKey());

        assertEquals(new Point2D.Double(10, 10), loadedDrawing.getFigureForConcept(loadedLattice.getZero()).getCenter());
        assertEquals(new Point2D.Double(10, 300), loadedDrawing.getFigureForConcept(loadedLattice.getOne()).getCenter());

        assertEquals(drawing.getLabelForAttribute(cxt.getAttribute(0)).getCenter(),
                loadedDrawing.getLabelForAttribute(loadedContext.getAttribute(0)).getCenter());
        assertEquals(drawing.getLabelForObject(cxt.getObject(0)).getCenter(),
                loadedDrawing.getLabelForObject(loadedContext.getObject(0)).getCenter());
    }

    private void doTestWriteAndReadForDocumentWithConceptsLabels(ContextDocument doc, ExtendedContextEditingInterface cxt) {
        Lattice lattice = doc.getLatticeComponent().getLattice();

        LatticeDrawing drawing = doc.getLatticeComponent().getDrawing();
        drawing.getFigureForConcept(lattice.getZero()).setCoords(10, 10);
        drawing.getFigureForConcept(lattice.getOne()).setCoords(10, 300);

        assertTrue(drawing.setAttributeLabelingStrategyKey(LabelingStrategiesKeys.NO_ATTRIBS_LABELING_STRATEGY));
        assertTrue(drawing.setObjectLabelingStrategyKey(LabelingStrategiesKeys.OBJECTS_COUNT_LABEL_STRATEGY));

        drawing.getLabelForConcept(lattice.getZero()).setCoords(15, 20);

        ContextDocument loadedDoc = writeAndReadContextDoc(doc);
        ExtendedContextEditingInterface loadedContext = loadedDoc.getContext();
        assertEquals(cxt, loadedContext);

        Lattice loadedLattice = loadedDoc.getLatticeComponent().getLattice();
        assertTrue("Lattice should be restored", !loadedLattice.isEmpty());
        assertTrue("Lattice should be equal to saved", lattice.isEqual(loadedLattice));

        final LatticeDrawing loadedDrawing = loadedDoc.getLatticeComponent().getDrawing();
        assertEquals(drawing.getAttributeLabelingStrategyKey(), loadedDrawing.getAttributeLabelingStrategyKey());
        assertEquals(drawing.getObjectLabelingStrategyKey(), loadedDrawing.getObjectLabelingStrategyKey());

        assertEquals(new Point2D.Double(10, 10), loadedDrawing.getFigureForConcept(loadedLattice.getZero()).getCenter());
        assertEquals(new Point2D.Double(10, 300), loadedDrawing.getFigureForConcept(loadedLattice.getOne()).getCenter());

        assertEquals(drawing.getLabelForConcept(lattice.getZero()).getCenter(),
                loadedDrawing.getLabelForConcept(loadedLattice.getZero()).getCenter());
    }

    private void doTestWriteAndReadForDocWithLattice(ContextDocument doc, ExtendedContextEditingInterface cxt) {
        Lattice lattice = doc.getLatticeComponent().getLattice();

        ContextDocument loadedDoc = writeAndReadContextDoc(doc);
        ExtendedContextEditingInterface loadedContext = loadedDoc.getContext();
        assertEquals(cxt, loadedContext);

        Lattice loadedLattice = loadedDoc.getLatticeComponent().getLattice();
        assertTrue("Lattice should be restored", !loadedLattice.isEmpty());
        assertTrue("Lattice should be equal to saved", lattice.isEqual(loadedLattice));
    }


}

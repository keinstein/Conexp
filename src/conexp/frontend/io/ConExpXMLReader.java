/*
 * User: sergey
 * Date: Feb 2, 2002
 * Time: 8:58:41 PM
 */
package conexp.frontend.io;

import canvas.figures.IFigureWithCoords;
import conexp.core.*;
import conexp.frontend.ContextDocument;
import conexp.frontend.DataFormatErrorHandler;
import conexp.frontend.DocumentLoader;
import conexp.frontend.SetProvidingAttributeMask;
import conexp.frontend.components.LatticeComponent;
import conexp.frontend.latticeeditor.LatticeDrawing;
import org.jdom.DataConversionException;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import util.*;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import java.util.List;

public class ConExpXMLReader implements DocumentLoader {
    public ContextDocument loadDocument(Reader reader, DataFormatErrorHandler errorHandler) throws IOException, DataFormatException {
        SAXBuilder builder = new SAXBuilder();

        Element root = null;
        try {
            Document doc = builder.build(reader);
            root = doc.getRootElement();
        } catch (final JDOMException e) {
            errorHandler.handleCriticalError(XMLHelper.makeDataFormatError(StringUtil.stackTraceToString(e)));
        }

        if (null == root ||
                !root.getName().equals(ConExpXMLElements.DOC_XML_ROOT)) {
            errorHandler.handleCriticalError(XMLHelper.makeDataFormatError("Error in xml format: bad root element"));
        }

        ContextDocument ret = null;
        try {
            ret = new ContextDocument();
            Element contextCollection = XMLHelper.safeGetElement(root, ConExpXMLElements.CONTEXTS_COLLECTION, "No contexts in data file");
            addContexts(ret, contextCollection);
        } catch (DataFormatException e) {
            errorHandler.handleCriticalError(e);
        }
        try {
            Element latticeCollection = XMLHelper.safeGetElement(root, ConExpXMLElements.LATTICE_COLLECTION, "No lattice part in data file");
            addLattices(ret, latticeCollection);
        } catch (DataFormatException e) {
            errorHandler.handleUncriticalError(e);
        }

        return ret;
    }

    private void addLattices(ContextDocument doc, Element latticeCollection) throws DataFormatException {
        Element latticeElement = latticeCollection.getChild(ConExpXMLElements.LATTICE_ELEMENT);
        if (null == latticeElement) {
            return;
        }
        try {
            loadLatticeComponent(doc.getLatticeComponent(), latticeElement);
        } catch (DataFormatException e) {
            doc.resetLatticeComponent();
            throw e;
        }
    }

    private void loadLatticeComponent(final LatticeComponent latticeComponent, Element latticeElement) throws DataFormatException {
        Element attributeMaskElement = latticeElement.getChild(ConExpXMLElements.ATTRIBUTE_MASK_ELEMENT);
        if (null == attributeMaskElement) {
            latticeComponent.calculateLattice();
        } else {
            doReadAttributeMask(latticeComponent.getAttributeMask(), attributeMaskElement);
            latticeComponent.calculatePartialLattice();
        }
        LatticeDrawing drawing = latticeComponent.getDrawing();
        Element lineDiagramElement = XMLHelper.safeGetElement(latticeElement, ConExpXMLElements.LATTICE_DIAGRAM, "Lattice always should have diagram");
        loadDrawingSettings(drawing, lineDiagramElement);
        loadConceptFigures(drawing, lineDiagramElement);
        loadAttributeLabels(drawing, lineDiagramElement);
        loadObjectLabels(drawing, lineDiagramElement);
        loadConceptLabels(drawing, lineDiagramElement);
    }

    private void doReadAttributeMask(final SetProvidingAttributeMask attributeMask, Element attributeMaskElement) throws DataFormatException {
        int attributeCount = attributeMask.getAttributeCount();
        ModifiableSet intent = ContextFactoryRegistry.createSet(attributeCount);
        doReadSet(intent, attributeMaskElement);
        for (int i = 0; i < intent.size(); i++) {
            attributeMask.setAttributeSelected(i, intent.in(i));
        }
    }

    interface FigureElementsProcessor {
        void processFigureElement(Element figureElement) throws DataFormatException;
    };

    private void loadConceptLabels(final LatticeDrawing drawing, Element lineDiagramElement) throws DataFormatException {
        if (!drawing.hasLabelsForConcepts()) {
            return;
        }
        Element labelsCollection = lineDiagramElement.getChild(ConExpXMLElements.CONCEPT_LABELS_ELEMENT);
        processLabelsFromCollectionWithType(labelsCollection,
                ConExpXMLElements.CONCEPT_LABEL_FIGURE_TYPE,
                new FigureElementsProcessor() {
                    public void processFigureElement(Element figureElement) throws DataFormatException {
                        readConceptFigure(
                                drawing,
                                figureElement,
                                new FigureConceptMapper() {
                                    public IFigureWithCoords getFigureForConcept(ItemSet concept) {
                                        return drawing.getLabelForConcept(concept);
                                    }
                                });
                        ;
                    }
                }
        );
    }

    private void loadObjectLabels(final LatticeDrawing drawing, Element lineDiagramElement) throws DataFormatException {
        if (!drawing.hasLabelsForObjects()) {
            return;
        }
        Element labelsCollection = lineDiagramElement.getChild(ConExpXMLElements.OBJECT_LABELS_ELEMENT);
        processLabelsFromCollectionWithType(labelsCollection,
                ConExpXMLElements.OBJECT_LABEL_FIGURE_TYPE,
                new FigureElementsProcessor() {
                    public void processFigureElement(Element figureElement) throws DataFormatException {
                        readObjectLabelFigure(drawing, figureElement);
                    }
                }
        );
    }

    private void readObjectLabelFigure(LatticeDrawing drawing, Element figureElement) throws DataFormatException {
        Point2D point = readFigureCoords(figureElement);
        Element entityReference = XMLHelper.safeGetElement(figureElement, ConExpXMLElements.OBJECT_REFERENCE, "Object label element should contain object reference");
        ExtendedContextEditingInterface cxt = drawing.getConceptSet().getContext();

        int attributeId = readIntAttributeFromElementAndCheckItsValidity(
                entityReference,
                ConExpXMLElements.ID_ATTRIBUTE,
                cxt.getObjectCount(),
                "Bad object reference value");
        drawing.getLabelForObject(cxt.getObject(attributeId)).setCoords(point);
    }


    private void loadAttributeLabels(final LatticeDrawing drawing, Element lineDiagramElement) throws DataFormatException {
        if (!drawing.hasLabelsForAttributes()) {
            return;
        }
        Element labelsCollection = lineDiagramElement.getChild(ConExpXMLElements.ATTRIBUTE_LABELS_ELEMENT);
        if (labelsCollection == null) {
            return;
        }

        processLabelsFromCollectionWithType(labelsCollection,
                ConExpXMLElements.ATTRIBUTE_LABEL_FIGURE_TYPE,
                new FigureElementsProcessor() {
                    public void processFigureElement(Element figureElement) throws DataFormatException {
                        readAttributeLabelFigure(drawing, figureElement);
                    }
                });
    }

    private void processLabelsFromCollectionWithType(Element labelsCollection,
                                                     String figureType,
                                                     FigureElementsProcessor processor)
            throws DataFormatException {

        List attributeLabelsElement = labelsCollection.getChildren(ConExpXMLElements.FIGURE);
        Iterator figuresIterator = attributeLabelsElement.iterator();
        while (figuresIterator.hasNext()) {
            Element figureElement = (Element) figuresIterator.next();
            if (!figureType.equals(figureElement.getAttributeValue(ConExpXMLElements.FIGURE_TYPE_ATTRIBUTE))) {
                continue;
            }
            processor.processFigureElement(figureElement);
        }
    }

    private void readAttributeLabelFigure(LatticeDrawing drawing, Element figureElement) throws DataFormatException {
        Point2D point = readFigureCoords(figureElement);
        Element attributeReference = XMLHelper.safeGetElement(figureElement, ConExpXMLElements.ATTRIBUTE_REFERENCE, "Attribute label element should contain attribute reference");
        ExtendedContextEditingInterface cxt = drawing.getConceptSet().getContext();

        int attributeId = readIntAttributeFromElementAndCheckItsValidity(
                attributeReference,
                ConExpXMLElements.ID_ATTRIBUTE,
                cxt.getAttributeCount(),
                "Bad attribute reference value");
        drawing.getLabelForAttribute(cxt.getAttribute(attributeId)).setCoords(point);
    }

    private void loadConceptFigures(final LatticeDrawing drawing, Element lineDiagramElement) throws DataFormatException {
        Element conceptFigures = XMLHelper.safeGetElement(lineDiagramElement, ConExpXMLElements.CONCEPT_FIGURES_ELEMENT, "Line drawing should always has conexp figures");

        processLabelsFromCollectionWithType(conceptFigures,
                ConExpXMLElements.CONCEPT_FIGURE_TYPE,
                new FigureElementsProcessor() {
                    public void processFigureElement(Element figureElement) throws DataFormatException {
                        readConceptFigure(
                                drawing,
                                figureElement,
                                new FigureConceptMapper() {
                                    public IFigureWithCoords getFigureForConcept(ItemSet concept) {
                                        return drawing.getFigureForConcept(concept);
                                    }
                                });
                    }
                }
        );
    }

    private void loadDrawingSettings(LatticeDrawing drawing, Element lineDiagramElement) throws DataFormatException {
        Element lineDiagramSetting = XMLHelper.safeGetElement(lineDiagramElement, ConExpXMLElements.LATTICE_DIAGRAM_SETTINGS, "Line diagram should have settings");
        Element attributeDisplayMode = XMLHelper.safeGetElement(lineDiagramSetting, ConExpXMLElements.ATTRIBUTE_LABEL_DISPLAY_MODE, "Expect attribute display mode in settings");
        if (!drawing.setAttributeLabelingStrategyKey(attributeDisplayMode.getAttributeValue(ConExpXMLElements.VALUE_ATTRIBUTE))) {
            XMLHelper.throwDataFormatError("Unspecified attribute display mode");
        }
        Element objectLabelsDisplayMode = XMLHelper.safeGetElement(lineDiagramSetting, ConExpXMLElements.OBJECT_LABEL_STRATEGY_DISPLAY_MODE, "Expect object dsplay mode in settings");
        if (!drawing.setObjectLabelingStrategyKey(objectLabelsDisplayMode.getAttributeValue(ConExpXMLElements.VALUE_ATTRIBUTE))) {
            XMLHelper.throwDataFormatError("Unspecified object display mode");
        }
    }

    interface FigureConceptMapper {
        IFigureWithCoords getFigureForConcept(ItemSet concept);
    }

    private void readConceptFigure(LatticeDrawing drawing, Element figureElement, FigureConceptMapper figureConceptMapper) throws DataFormatException {
        Point2D point = readFigureCoords(figureElement);

        Element intentElement = XMLHelper.safeGetElement(figureElement, ConExpXMLElements.INTENT, "No intent element for conexp figure");
        final Lattice lattice = drawing.getLattice();
        ModifiableSet intent = ContextFactoryRegistry.createSet(lattice.getContext().getAttributeCount());
        readIntent(intentElement, intent);

        ItemSet concept = lattice.findElementWithIntent(intent);
        if (null == concept) {
            XMLHelper.throwDataFormatError("Error in lattice drawing, specified element not existing in lattice");
        }
        figureConceptMapper.getFigureForConcept(concept).setCoords(point);
    }

    private Point2D readFigureCoords(Element figureElement) throws DataFormatException {
        Element coords = XMLHelper.safeGetElement(figureElement, ConExpXMLElements.FIGURE_COORDS, "No coordinates are provided for figure");
        Element pointElement = XMLHelper.safeGetElement(coords, XMLGeneralTypesUtil.POINT2D, "Point element is absent in coordinates");

        Point2D point = XMLGeneralTypesUtil.readPoint2D(pointElement);
        return point;
    }

    private void addContexts(ContextDocument ret, Element contextCollection) throws DataFormatException {
        Element context = XMLHelper.safeGetElement(contextCollection, ConExpXMLElements.CONTEXT_ELEMENT, "No context in context collection");
        Context cxt = loadContext(context);
        ret.setContext(cxt);
    }


    private Context loadContext(Element context) throws DataFormatException {
        Context cxt = FCAEngineRegistry.makeContext(0, 0);
        loadAttributes(context, cxt);
        loadObjects(context, cxt);

        return cxt;
    }

    private void loadObjects(Element context, Context cxt) throws DataFormatException {
        Element objectCollection = XMLHelper.safeGetElement(context, ConExpXMLElements.OBJECT_COLLECTION, "Bad XML File: object collection element absent");
        List objects = objectCollection.getChildren(ConExpXMLElements.OBJECT_ELEMENT);
        Iterator iter = objects.iterator();

        while (iter.hasNext()) {
            Element objectElement = (Element) iter.next();
            readObject(objectElement, cxt);
        }
    }

    private void readObject(Element objectElement, Context cxt) throws DataFormatException {
        Element nameElement = XMLHelper.safeGetElement(objectElement, ConExpXMLElements.ELEMENT_NAME_ELEMENT, "In context name is not specified for object");

        Element objAttrRelation = XMLHelper.safeGetElement(objectElement, ConExpXMLElements.INTENT, "Bad Xml file: missing description of attribute object relation for object");

        final int attributeCount = cxt.getAttributeCount();

        ModifiableSet intent = ContextFactoryRegistry.createSet(attributeCount);

        readIntent(objAttrRelation, intent);

        cxt.addObjectWithNameAndIntent(nameElement.getText(), intent);
    }

    private void readIntent(Element intentElement, ModifiableSet intent) throws DataFormatException {
        Assert.isTrue(intentElement.getName().equals(ConExpXMLElements.INTENT));
        doReadSet(intent, intentElement);
    }

    private void doReadSet(ModifiableSet intent, Element intentElement) throws DataFormatException {
        intent.clearSet();
        List objIntent = intentElement.getChildren(ConExpXMLElements.OBJECT_HAS_ATTRIBUTE);
        Iterator iter = objIntent.iterator();
        while (iter.hasNext()) {
            Element attr = (Element) iter.next();
            intent.put(
                    readIntAttributeFromElementAndCheckItsValidity(
                            attr,
                            ConExpXMLElements.ATTRIBUTE_ID,
                            intent.size(),
                            "Attribute index out of range for object")
            );
        }
    }

    private int readIntAttributeFromElementAndCheckItsValidity(Element attr, String attributeToRead, int maxValue, final String message) throws DataFormatException {
        int index = -1;
        try {
            index = attr.getAttribute(attributeToRead).getIntValue();
        } catch (final DataConversionException e) {
            XMLHelper.throwDataFormatError(StringUtil.stackTraceToString(e));
        }
        if (badID(maxValue, index)) {
            XMLHelper.throwDataFormatError(message);
        }
        return index;
    }

    private boolean badID(int attrCount, int attrIndex) {
        return attrIndex < 0 || attrIndex >= attrCount;
    }

    private void loadAttributes(Element context, Context cxt) throws DataFormatException {

        Element attributeCollection = XMLHelper.safeGetElement(context, ConExpXMLElements.ATTRIBUTE_COLLECTION, "Bad XML File: No attributes specified for context");
        List attributes = attributeCollection.getChildren(ConExpXMLElements.ATTRIBUTE_ELEMENT);
        Iterator iter = attributes.iterator();
        while (iter.hasNext()) {
            Element attribute = (Element) iter.next();
            Element nameElement = XMLHelper.safeGetElement(attribute, ConExpXMLElements.ELEMENT_NAME_ELEMENT, "In context name is not specified for attribute element");
            cxt.addAttribute(makeContextAttribute(nameElement.getText()));
        }
    }

    private ContextEntity makeContextAttribute(final String name) {
        return ContextEntity.createContextAttribute(name);
    }


}

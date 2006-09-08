/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.io;

import canvas.Figure;
import canvas.FigureDrawingCanvas;
import canvas.figures.IFigureWithCoords;
import conexp.core.Context;
import conexp.core.ContextEntity;
import conexp.core.ContextFactoryRegistry;
import conexp.core.ExtendedContextEditingInterface;
import conexp.core.FCAEngineRegistry;
import conexp.core.ItemSet;
import conexp.core.Lattice;
import conexp.core.ModifiableSet;
import conexp.frontend.ContextDocument;
import conexp.frontend.DataFormatErrorHandler;
import conexp.frontend.DocumentLoader;
import conexp.frontend.SetProvidingEntitiesMask;
import conexp.frontend.components.LatticeComponent;
import conexp.frontend.latticeeditor.LatticeDrawing;
import conexp.util.gui.strategymodel.StrategyValueItem;
import conexp.util.valuemodels.BoundedIntValue;
import org.jdom.DataConversionException;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import util.Assert;
import util.DataFormatException;
import util.StringUtil;
import util.XMLGeneralTypesUtil;
import util.XMLHelper;
import util.collection.CollectionFactory;

import java.awt.geom.Point2D;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.Reader;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class ConExpXMLReader implements DocumentLoader {
    public ContextDocument loadDocument(Reader reader,
                                        DataFormatErrorHandler errorHandler)
            throws IOException, DataFormatException {
        SAXBuilder builder = new SAXBuilder();

        Element root = null;
        try {
            Document doc = builder.build(reader);
            root = doc.getRootElement();
        } catch (final JDOMException e) {
            errorHandler.handleCriticalError(
                    XMLHelper.makeDataFormatError(
                            StringUtil.stackTraceToString(e)));
        }

        if (isBadRootElement(root)) {
            errorHandler.handleCriticalError(
                    XMLHelper.makeDataFormatError(
                            "Error in xml format: bad root element"));
        }

        ContextDocument ret = null;
        try {
            Element contextCollection = XMLHelper.safeGetElement(root,
                    ConExpXMLElements.CONTEXTS_COLLECTION,
                    "No contexts in data file");
            ret = addContexts(contextCollection);
        } catch (DataFormatException e) {
            errorHandler.handleCriticalError(e);
        }
        try {
            Element latticeCollection = XMLHelper.safeGetElement(root,
                    ConExpXMLElements.LATTICE_COLLECTION,
                    "No lattice part in data file");
            addLattices(ret, latticeCollection);
        } catch (DataFormatException e) {
            errorHandler.handleUncriticalError(e);
        }
        Element recalculationPolicyElement = root.getChild(
                ConExpXMLElements.RECALCULATION_POLICY);
        if (null != recalculationPolicyElement) {
            String strategyKey = recalculationPolicyElement.getAttributeValue(
                    ConExpXMLElements.VALUE_ATTRIBUTE);
            if (!ret.getContextDocumentModel().getRecalculationPolicy()
                    .setValueByKey(strategyKey)
                    ) {
                errorHandler.handleUncriticalError(
                        new DataFormatException(
                                "Bad value of recalculation policy:" +
                                        strategyKey));
            }
        }

        return ret;
    }

    private static boolean isBadRootElement(Element root) {
        return null == root ||
                !ConExpXMLElements.DOC_XML_ROOT.equals(root.getName());
    }

    private static void addLattices(ContextDocument doc,
                                    Element latticeCollection)
            throws DataFormatException {
        final List children = latticeCollection.getChildren(
                ConExpXMLElements.LATTICE_ELEMENT);
        if (children.isEmpty()) {
            return;
        }
        try {
            for (Iterator iterator = children.iterator(); iterator.hasNext();) {
                final LatticeComponent latticeComponent =
                        doc.addLatticeComponent();
                final Element latticeElement = (Element) iterator.next();
                loadLatticeComponent(latticeComponent, latticeElement);
                loadSelection(doc.getViewForLatticeComponent(latticeComponent),
                        latticeElement);
            }
        } catch (DataFormatException e) {
            doc.resetLatticeComponent();
            throw e;
        }

/*
        try {
            loadLatticeComponent(doc.getOrCreateDefaultLatticeComponent(), latticeElement);
        } catch (DataFormatException e) {
            doc.resetLatticeComponent();
            throw e;
        }
*/
    }

    private static void loadSelection(
            FigureDrawingCanvas viewForLatticeComponent,
            Element latticeElement) throws DataFormatException {
        Element selection = latticeElement.getChild(
                ConExpXMLElements.LATTICE_DIAGRAM)
                .getChild(ConExpXMLElements.SELECTION);
        if (null == selection) {
            return;
        }
        final List children = selection.getChildren();
        Collection newSelection = CollectionFactory.createDefaultList();
        for (Iterator iterator = children.iterator(); iterator.hasNext();) {
            Element figureElement = (Element) iterator.next();
            Point2D point = readPoint2DFromCoords(figureElement);
            final Figure figure =
                    viewForLatticeComponent.findFigureInReverseOrderToDrawing(
                            point.getX(), point.getY());
            if (null != figure) {
                newSelection.add(figure);
            }
        }
        if (!newSelection.isEmpty()) {
            viewForLatticeComponent.setSelection(newSelection);
        }
    }

    public static void loadLatticeComponent(
            final LatticeComponent latticeComponent, Element latticeElement)
            throws DataFormatException {

        readEntitiesMask(
                latticeElement.getChild(
                        ConExpXMLElements.ATTRIBUTE_MASK_ELEMENT),
                latticeComponent.getAttributeMask());
        readEntitiesMask(
                latticeElement.getChild(ConExpXMLElements.OBJECT_MASK_ELEMENT),
                latticeComponent.getObjectMask());
        latticeComponent.calculateLattice();
        LatticeDrawing drawing = latticeComponent.getDrawing();
        Element lineDiagramElement = XMLHelper.safeGetElement(latticeElement,
                ConExpXMLElements.LATTICE_DIAGRAM,
                "Lattice always should have diagram");
        loadDrawingSettings(drawing, lineDiagramElement);
        loadConceptFigures(drawing, lineDiagramElement);
        loadAttributeLabels(drawing, lineDiagramElement);
        loadObjectLabels(drawing, lineDiagramElement);
        loadConceptLabels(drawing, lineDiagramElement);
    }

    private static void readEntitiesMask(Element attributeMaskElement,
                                         SetProvidingEntitiesMask entitiesMask)
            throws DataFormatException {
        if (null == attributeMaskElement) {
            entitiesMask.selectAll();
        } else {
            doReadEntitiesMask(entitiesMask, attributeMaskElement);
        }
    }

    private static void doReadEntitiesMask(
            final SetProvidingEntitiesMask entityMask,
            Element entitiesMaskElement) throws DataFormatException {
        int count = entityMask.getCount();
        ModifiableSet intent = ContextFactoryRegistry.createSet(count);
        doReadSet(intent, entitiesMaskElement);
        for (int i = 0; i < intent.size(); i++) {
            entityMask.setSelected(i, intent.in(i));
        }
    }

    interface FigureElementsProcessor {
        void processFigureElement(Element figureElement)
                throws DataFormatException;
    }

    private static void loadConceptLabels(final LatticeDrawing drawing,
                                          Element lineDiagramElement)
            throws DataFormatException {
        loadDownConceptLabels(drawing, lineDiagramElement);
        loadUpConceptLabels(drawing, lineDiagramElement);
    }

    private static void doLoadConceptLabels(final LatticeDrawing drawing,
                                            Element lineDiagramElement,
                                            String elementsLabel,
                                            final FigureConceptMapper figureConceptMapper)
            throws
            DataFormatException {
        Element labelsCollection = lineDiagramElement.getChild(
                elementsLabel);
        assert labelsCollection !=
                null:"If drawing has labels for concepts, then drawing should have non zero labels collection :" + elementsLabel;

        processLabelsFromCollectionWithType(labelsCollection,
                ConExpXMLElements.CONCEPT_LABEL_FIGURE_TYPE,
                new FigureElementsProcessor() {
                    public void processFigureElement(Element figureElement)
                            throws DataFormatException {
                        readConceptFigure(drawing,
                                figureElement,
                                figureConceptMapper);
                    }
                });
    }

    private static void loadUpConceptLabels(final LatticeDrawing drawing,
                                            Element lineDiagramElement)
            throws DataFormatException {
        if (!drawing.hasUpLabelsForConcepts()) {
            return;
        }
        doLoadConceptLabels(drawing, lineDiagramElement,
                ConExpXMLElements.UP_CONCEPT_LABELS_ELEMENT,
                new FigureConceptMapper() {
                    public IFigureWithCoords getFigureForConcept(ItemSet concept) {
                        return drawing.getUpLabelForConcept(concept);
                    }
                });
    }

    private static void loadDownConceptLabels(final LatticeDrawing drawing,
                                              Element lineDiagramElement)
            throws DataFormatException {
        if (!drawing.hasDownLabelsForConcepts()) {
            return;
        }
        doLoadConceptLabels(drawing, lineDiagramElement,
                ConExpXMLElements.DOWN_CONCEPT_LABELS_ELEMENT,
                new FigureConceptMapper() {
                    public IFigureWithCoords getFigureForConcept(ItemSet concept) {
                        return drawing.getDownLabelForConcept(concept);
                    }
                });

    }

    private static void loadObjectLabels(final LatticeDrawing drawing,
                                         Element lineDiagramElement)
            throws DataFormatException {
        if (!drawing.hasLabelsForObjects()) {
            return;
        }
        Element labelsCollection = lineDiagramElement.getChild(
                ConExpXMLElements.OBJECT_LABELS_ELEMENT);
        processLabelsFromCollectionWithType(labelsCollection,
                ConExpXMLElements.OBJECT_LABEL_FIGURE_TYPE,
                new FigureElementsProcessor() {
                    public void processFigureElement(Element figureElement)
                            throws DataFormatException {
                        readObjectLabelFigure(drawing, figureElement);
                    }
                });
    }

    private static void readObjectLabelFigure(LatticeDrawing drawing,
                                              Element figureElement)
            throws DataFormatException {
        Point2D point = readFigureCoords(figureElement);
        Element entityReference = XMLHelper.safeGetElement(figureElement,
                ConExpXMLElements.OBJECT_REFERENCE,
                "Object label element should contain object reference");
        ExtendedContextEditingInterface cxt = drawing.getConceptSet()
                .getContext();

        int attributeId = readIntAttributeFromElementAndCheckItsValidity(
                entityReference,
                ConExpXMLElements.ID_ATTRIBUTE,
                cxt.getObjectCount(),
                "Bad object reference value");
        drawing.getLabelForObject(cxt.getObject(attributeId)).setCoords(point);
    }


    private static void loadAttributeLabels(final LatticeDrawing drawing,
                                            Element lineDiagramElement)
            throws DataFormatException {
        if (!drawing.hasLabelsForAttributes()) {
            return;
        }
        Element labelsCollection = lineDiagramElement.getChild(
                ConExpXMLElements.ATTRIBUTE_LABELS_ELEMENT);
        if (labelsCollection == null) {
            return;
        }

        processLabelsFromCollectionWithType(labelsCollection,
                ConExpXMLElements.ATTRIBUTE_LABEL_FIGURE_TYPE,
                new FigureElementsProcessor() {
                    public void processFigureElement(Element figureElement)
                            throws DataFormatException {
                        readAttributeLabelFigure(drawing, figureElement);
                    }
                });
    }

    private static void processLabelsFromCollectionWithType(
            Element labelsCollection,
            String figureType,
            FigureElementsProcessor processor)
            throws DataFormatException {

        List attributeLabelsElement = labelsCollection.getChildren(
                ConExpXMLElements.FIGURE);
        Iterator figuresIterator = attributeLabelsElement.iterator();
        while (figuresIterator.hasNext()) {
            Element figureElement = (Element) figuresIterator.next();
            if (!figureType.equals(
                    figureElement.getAttributeValue(
                            ConExpXMLElements.FIGURE_TYPE_ATTRIBUTE))) {
                continue;
            }
            processor.processFigureElement(figureElement);
        }
    }

    private static void readAttributeLabelFigure(LatticeDrawing drawing,
                                                 Element figureElement)
            throws DataFormatException {
        Point2D point = readFigureCoords(figureElement);
        Element attributeReference = XMLHelper.safeGetElement(figureElement,
                ConExpXMLElements.ATTRIBUTE_REFERENCE,
                "Attribute label element should contain attribute reference");
        ExtendedContextEditingInterface cxt = drawing.getConceptSet()
                .getContext();

        int attributeId = readIntAttributeFromElementAndCheckItsValidity(
                attributeReference,
                ConExpXMLElements.ID_ATTRIBUTE,
                cxt.getAttributeCount(),
                "Bad attribute reference value");
        drawing.getLabelForAttribute(cxt.getAttribute(attributeId)).setCoords(
                point);
    }

    private static void loadConceptFigures(final LatticeDrawing drawing,
                                           Element lineDiagramElement)
            throws DataFormatException {
        Element conceptFigures = XMLHelper.safeGetElement(lineDiagramElement,
                ConExpXMLElements.CONCEPT_FIGURES_ELEMENT,
                "Line drawing should always has conexp figures");

        processLabelsFromCollectionWithType(conceptFigures,
                ConExpXMLElements.CONCEPT_FIGURE_TYPE,
                new FigureElementsProcessor() {
                    public void processFigureElement(Element figureElement)
                            throws DataFormatException {
                        readConceptFigure(drawing,
                                figureElement,
                                new FigureConceptMapper() {
                                    public IFigureWithCoords getFigureForConcept(
                                            ItemSet concept) {
                                        return drawing.getFigureForConcept(
                                                concept);
                                    }
                                });
                    }
                });
    }

    private static void loadDrawingSettings(LatticeDrawing drawing,
                                            Element lineDiagramElement)
            throws DataFormatException {
        Element lineDiagramSetting = XMLHelper.safeGetElement(
                lineDiagramElement, ConExpXMLElements.LATTICE_DIAGRAM_SETTINGS,
                "Line diagram should have settings");
        Element attributeDisplayMode = XMLHelper.safeGetElement(
                lineDiagramSetting,
                ConExpXMLElements.ATTRIBUTE_LABEL_DISPLAY_MODE,
                "Expect attribute display mode in settings");
        if (!drawing.setAttributeLabelingStrategyKey(
                attributeDisplayMode.getAttributeValue(
                        ConExpXMLElements.VALUE_ATTRIBUTE))) {
            XMLHelper.throwDataFormatError(
                    "Unspecified attribute display mode");
        }
        Element objectLabelsDisplayMode = XMLHelper.safeGetElement(
                lineDiagramSetting,
                ConExpXMLElements.OBJECT_LABEL_STRATEGY_DISPLAY_MODE,
                "Expect object dsplay mode in settings");
        if (!drawing.setObjectLabelingStrategyKey(
                objectLabelsDisplayMode.getAttributeValue(
                        ConExpXMLElements.VALUE_ATTRIBUTE))) {
            XMLHelper.throwDataFormatError("Unspecified object display mode");
        }

        readBoundedIntValue(
                lineDiagramSetting.getChild(ConExpXMLElements.LABEL_FONT_SIZE),
                drawing.getPainterOptions().getLabelsFontSizeValue(),
                "Wrong value of labels font size:");
        drawing.getEditableDrawParameters().setShowCollisions(
                readBooleanValue(
                        lineDiagramSetting.getChild(
                                ConExpXMLElements.SHOW_COLLISIONS),
                        true));
        readBoundedIntValue(
                lineDiagramSetting.getChild(ConExpXMLElements.MAX_NODE_RADIUS),
                drawing.getEditableDrawParameters().getMaxNodeRadiusValue(),
                "Wrong value of maximal node radius is provided:");

        readStrategyValueItem(lineDiagramSetting,
                ConExpXMLElements.NODE_RADIUS_MODE,
                drawing.getNodeRadiusStrategyItem(),
                "Unspecified node radius mode:");

        readStrategyValueItem(lineDiagramSetting,
                ConExpXMLElements.EDGE_DISPLAY_MODE,
                drawing.getEdgeSizeCalcStrategyItem(),
                "Unspecified edge display mode:");

        readStrategyValueItem(lineDiagramSetting,
                ConExpXMLElements.HIGHLIGHT_MODE,
                drawing.getHighlightStrategyItem(),
                "Unspecified highlight mode:");

        readStrategyValueItem(lineDiagramSetting,
                ConExpXMLElements.LATTICE_LAYOUT,
                drawing.getLayoutStrategyItem(),
                "Unsupported layout mode:");

        readBoundedIntValue(
                lineDiagramSetting.getChild(ConExpXMLElements.GRID_SIZE_X),
                drawing.getEditableDrawParameters().getGridSizeXValue(),
                "Wrong value of grid size x is provided:");
        readBoundedIntValue(
                lineDiagramSetting.getChild(ConExpXMLElements.GRID_SIZE_Y),
                drawing.getEditableDrawParameters().getGridSizeYValue(),
                "Wrong value of grid size x is provided:");

    }

    private static void readStrategyValueItem(Element lineDiagramSetting,
                                              String elementName,
                                              StrategyValueItem elementValueItem,
                                              String errorMessage)
            throws DataFormatException {
        Element nodeRadiusModeElement = lineDiagramSetting.getChild(
                elementName);
        if (nodeRadiusModeElement != null) {
            String attributeValue = nodeRadiusModeElement.getAttributeValue(
                    ConExpXMLElements.VALUE_ATTRIBUTE);
            if (!elementValueItem.setValueByKey(attributeValue)) {
                XMLHelper.throwDataFormatError(
                        errorMessage + attributeValue);
            }
        }
    }

    private static void readBoundedIntValue(Element element,
                                            BoundedIntValue valueModel,
                                            String errorMessage)
            throws DataFormatException {
        if (null == element) {
            return;
        }
        String value = element.getAttributeValue(
                ConExpXMLElements.VALUE_ATTRIBUTE);
        if (StringUtil.isEmpty(value)) {
            return;
        }
        try {
            valueModel.setValue(Integer.valueOf(value).intValue());
        } catch (PropertyVetoException e) {
            XMLHelper.throwDataFormatError(errorMessage + e);
        } catch (NumberFormatException e) {
            XMLHelper.throwDataFormatError(errorMessage + e);
        }
    }


    private static boolean readBooleanValue(Element element,
                                            boolean defaultValue) {
        if (null == element) {
            return defaultValue;
        }
        String value = element.getAttributeValue(
                ConExpXMLElements.VALUE_ATTRIBUTE);
        if (StringUtil.isEmpty(value)) {
            return defaultValue;
        }
        return Boolean.valueOf(value).booleanValue();
    }

    interface FigureConceptMapper {
        IFigureWithCoords getFigureForConcept(ItemSet concept);
    }

    private static void readConceptFigure(LatticeDrawing drawing,
                                          Element figureElement,
                                          FigureConceptMapper figureConceptMapper)
            throws DataFormatException {
        Point2D point = readFigureCoords(figureElement);

        Element intentElement = XMLHelper.safeGetElement(figureElement,
                ConExpXMLElements.INTENT,
                "No intent element for conexp figure");
        final Lattice lattice = drawing.getLattice();
        ModifiableSet intent = ContextFactoryRegistry.createSet(
                lattice.getContext().getAttributeCount());
        readIntent(intentElement, intent);

        ItemSet concept = lattice.findElementWithIntent(intent);
        if (null == concept) {
            XMLHelper.throwDataFormatError(
                    "Error in lattice drawing, specified element not existing in lattice");
        }
        figureConceptMapper.getFigureForConcept(concept).setCoords(point);
    }


    private static Point2D readFigureCoords(Element figureElement)
            throws DataFormatException {
        Element coords = XMLHelper.safeGetElement(figureElement,
                ConExpXMLElements.FIGURE_COORDS,
                "No coordinates are provided for figure");
        return readPoint2DFromCoords(coords);

    }

    private static Point2D readPoint2DFromCoords(Element coords)
            throws DataFormatException {
        Element pointElement = XMLHelper.safeGetElement(coords,
                XMLGeneralTypesUtil.POINT2D,
                "Point element is absent in coordinates");

        Point2D point = XMLGeneralTypesUtil.readPoint2D(pointElement);
        return point;
    }

    private static ContextDocument addContexts(Element contextCollection)
            throws DataFormatException {
        Element context = XMLHelper.safeGetElement(contextCollection,
                ConExpXMLElements.CONTEXT_ELEMENT,
                "No context in context collection");
        return new ContextDocument(loadContext(context));
    }


    private static Context loadContext(Element context)
            throws DataFormatException {
        Context cxt = FCAEngineRegistry.makeContext(0, 0);
        loadAttributes(context, cxt);
        loadObjects(context, cxt);

        return cxt;
    }

    private static void loadObjects(Element context, Context cxt)
            throws DataFormatException {
        Element objectCollection = XMLHelper.safeGetElement(context,
                ConExpXMLElements.OBJECT_COLLECTION,
                "Bad XML File: object collection element absent");
        List objects = objectCollection.getChildren(
                ConExpXMLElements.OBJECT_ELEMENT);
        Iterator iter = objects.iterator();

        while (iter.hasNext()) {
            Element objectElement = (Element) iter.next();
            readObject(objectElement, cxt);
        }
    }

    private static void readObject(Element objectElement, Context cxt)
            throws DataFormatException {
        Element nameElement = XMLHelper.safeGetElement(objectElement,
                ConExpXMLElements.ELEMENT_NAME_ELEMENT,
                "In context name is not specified for object");

        Element objAttrRelation = XMLHelper.safeGetElement(objectElement,
                ConExpXMLElements.INTENT,
                "Bad Xml file: missing description of attribute object relation for object");

        final int attributeCount = cxt.getAttributeCount();

        ModifiableSet intent = ContextFactoryRegistry.createSet(attributeCount);

        readIntent(objAttrRelation, intent);

        cxt.addObjectWithNameAndIntent(nameElement.getText(), intent);
    }

    private static void readIntent(Element intentElement, ModifiableSet intent)
            throws DataFormatException {
        Assert.isTrue(intentElement.getName().equals(ConExpXMLElements.INTENT));
        doReadSet(intent, intentElement);
    }

    private static void doReadSet(ModifiableSet intent, Element intentElement)
            throws DataFormatException {
        intent.clearSet();
        List objIntent = intentElement.getChildren(
                ConExpXMLElements.OBJECT_HAS_ATTRIBUTE);
        Iterator iter = objIntent.iterator();
        while (iter.hasNext()) {
            Element attr = (Element) iter.next();
            intent.put(readIntAttributeFromElementAndCheckItsValidity(attr,
                    ConExpXMLElements.ATTRIBUTE_ID,
                    intent.size(),
                    "Attribute index out of range for object"));
        }
    }

    private static int readIntAttributeFromElementAndCheckItsValidity(
            Element attr, String attributeToRead, int maxValue,
            final String message) throws DataFormatException {
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

    private static boolean badID(int attrCount, int attrIndex) {
        return attrIndex < 0 || attrIndex >= attrCount;
    }

    private static void loadAttributes(Element context, Context cxt)
            throws DataFormatException {

        Element attributeCollection = XMLHelper.safeGetElement(context,
                ConExpXMLElements.ATTRIBUTE_COLLECTION,
                "Bad XML File: No attributes specified for context");
        List attributes = attributeCollection.getChildren(
                ConExpXMLElements.ATTRIBUTE_ELEMENT);
        Iterator iter = attributes.iterator();
        while (iter.hasNext()) {
            Element attribute = (Element) iter.next();
            Element nameElement = XMLHelper.safeGetElement(attribute,
                    ConExpXMLElements.ELEMENT_NAME_ELEMENT,
                    "In context name is not specified for attribute element");
            cxt.addAttribute(makeContextAttribute(nameElement.getText()));
        }
    }

    private static ContextEntity makeContextAttribute(final String name) {
        return ContextEntity.createContextAttribute(name);
    }


}

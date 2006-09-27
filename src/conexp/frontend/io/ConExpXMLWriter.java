/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.io;

import canvas.Figure;
import canvas.FigureDrawingCanvas;
import canvas.figures.IFigureWithCoords;
import conexp.core.AttributeInformationSupplier;
import conexp.core.ContextEntity;
import conexp.core.ExtendedContextEditingInterface;
import conexp.core.ItemSet;
import conexp.core.Lattice;
import conexp.core.LatticeElement;
import conexp.core.Set;
import conexp.frontend.ContextDocument;
import conexp.frontend.DocumentWriter;
import conexp.frontend.SetProvidingEntitiesMask;
import conexp.frontend.components.LatticeComponent;
import conexp.frontend.components.LatticeSupplier;
import conexp.frontend.latticeeditor.LatticeDrawing;
import conexp.frontend.latticeeditor.figures.AbstractConceptCorrespondingFigure;
import conexp.util.gui.strategymodel.StrategyValueItem;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;
import util.XMLGeneralTypesUtil;

import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.Iterator;


public class ConExpXMLWriter implements DocumentWriter {

    public void storeDocument(conexp.frontend.Document document, Writer writer) throws IOException {
        if (!(document instanceof ContextDocument)) {
            throw new IOException("Can't store this type of document:" + document);
        }
        ContextDocument contextDocument = (ContextDocument) document;
        Element root = new Element(ConExpXMLElements.DOC_XML_ROOT);
        root.addContent(storeVersion());
        root.addContent(storeContexts(contextDocument));
        root.addContent(storeRecalculationPolicy(contextDocument));
        root.addContent(storeLattices(contextDocument));
//        root.addContent(storeImplicationsSets(contextDocument));

        Document xmlDoc = new Document(root);
        XMLOutputter outputter = new XMLOutputter();
        outputter.output(xmlDoc, writer);
    }

    /*  private static Element storeImplicationsSets(ContextDocument contextDocument) {
            return null;
        }
    */
    private static Element storeRecalculationPolicy(ContextDocument contextDocument) {
        return makeSettingElementForStrategyValueItem(ConExpXMLElements.RECALCULATION_POLICY, contextDocument.getContextDocumentModel().
                getRecalculationPolicy());
    }

    static final int MAJOR_VERSION = 1;
    static final int MINOR_VERSION = 0;

    private static Element storeVersion() {
        Element version = new Element(ConExpXMLElements.VERSION_ELEMENT);
        version.setAttribute(ConExpXMLElements.VERSION_MAJOR_NUMBER_ATTRIBUTE, Integer.toString(MAJOR_VERSION));
        version.setAttribute(ConExpXMLElements.VERSION_MINOR_NUMBER_ATTRIBUTE, Integer.toString(MINOR_VERSION));
        return version;
    }

    private static Element storeContexts(ContextDocument document) {
        Element contextCollection = new Element(ConExpXMLElements.CONTEXTS_COLLECTION);
        ExtendedContextEditingInterface cxt = document.getContext();
        contextCollection.addContent(storeContext(cxt));
        return contextCollection;
    }

    private static Element storeLattices(ContextDocument document) {
        Element latticeCollection = new Element(ConExpXMLElements.LATTICE_COLLECTION);
        final Collection latticeComponents = document.getLatticeCollection();
        for (Iterator iterator = latticeComponents.iterator(); iterator.hasNext();) {
            LatticeComponent component = (LatticeComponent) iterator.next();
            storeLatticeComponent(latticeCollection, component, document);
        }
        return latticeCollection;
    }

    private static void storeLatticeComponent(Element latticeCollection, final LatticeComponent latticeComponent, ContextDocument document) {
        if (!latticeComponent.isEmpty()) {
            final Element latticeElement = makeLatticeElement(latticeComponent);
            Element latticeDrawingElement = latticeElement.getChild(ConExpXMLElements.LATTICE_DIAGRAM);
            storeSelection(document, latticeComponent, latticeDrawingElement);
            latticeCollection.addContent(latticeElement);
        }
    }

    public static Element makeLatticeElement(LatticeSupplier latticeComponent) {
        Element latticeElement = new Element(ConExpXMLElements.LATTICE_ELEMENT);
        storeFeatureMasks(latticeComponent, latticeElement);
        storeDrawing(latticeComponent, latticeElement);
        return latticeElement;
    }

    private static void storeSelection(ContextDocument document, LatticeSupplier latticeComponent, Element latticeDrawingElement) {
        final FigureDrawingCanvas viewForLatticeComponent = document.getViewForLatticeComponent(latticeComponent);
        if (viewForLatticeComponent.hasSelection()) {
            latticeDrawingElement.addContent(storeSelection(viewForLatticeComponent));
        }
    }

    private static void storeDrawing(LatticeSupplier latticeComponent, Element latticeElement) {
        latticeElement.addContent(makeDrawingElement(latticeComponent));
    }

    private static void storeFeatureMasks(LatticeSupplier latticeComponent, Element latticeElement) {
        doStoreFeatureMask(latticeElement, latticeComponent.getAttributeMask(), ConExpXMLElements.ATTRIBUTE_MASK_ELEMENT);
        doStoreFeatureMask(latticeElement, latticeComponent.getObjectMask(), ConExpXMLElements.OBJECT_MASK_ELEMENT);
    }

    private static void doStoreFeatureMask(Element latticeElement, final SetProvidingEntitiesMask entityMask, final String maskElementID) {
        if (entityMask.hasUnselected()) {
            latticeElement.addContent(makeEntityMaskElement(maskElementID, entityMask));
        }
    }

    private static Element makeEntityMaskElement(final String elementName, SetProvidingEntitiesMask mask) {
        Element entityMaskElement = new Element(elementName);
        writeSet(mask.toSet(), entityMaskElement);
        return entityMaskElement;
    }

    private static Element makeDrawingElement(LatticeSupplier latticeComponent) {
        LatticeDrawing drawing = latticeComponent.getDrawing();
        Element latticeDrawingElement = new Element(ConExpXMLElements.LATTICE_DIAGRAM);
        latticeDrawingElement.addContent(storeConceptFigures(drawing));
        latticeDrawingElement.addContent(storeDrawingSettings(drawing));
        if (drawing.hasLabelsForAttributes()) {
            latticeDrawingElement.addContent(storeAttributeLabels(drawing));
        }
        if (drawing.hasLabelsForObjects()) {
            latticeDrawingElement.addContent(storeObjectLabels(drawing));
        }
        if (drawing.hasUpLabelsForConcepts()) {
            latticeDrawingElement.addContent(storeUpConceptLabels(drawing));
        }
        if (drawing.hasDownLabelsForConcepts()) {
            latticeDrawingElement.addContent(storeDownConceptLabels(drawing));
        }
/*
        if (drawing.hasLabelsForConcepts()) {
            latticeDrawingElement.addContent(storeConceptLabels(drawing));
        }
*/
        return latticeDrawingElement;
    }

    private static Element storeDownConceptLabels(final LatticeDrawing drawing) {
        final Element downConceptLabelFigures = new Element(ConExpXMLElements.DOWN_CONCEPT_LABELS_ELEMENT);
        final Lattice lat = drawing.getLattice();
        lat.forEach(new Lattice.LatticeElementVisitor() {
            public void visitNode(LatticeElement conceptNode) {
                IFigureWithCoords conceptLabelFigure = drawing.getDownLabelForConcept(conceptNode);
                if (null != conceptLabelFigure) {
                    downConceptLabelFigures.addContent(storeFigureForConcept(ConExpXMLElements.CONCEPT_LABEL_FIGURE_TYPE,
                            conceptLabelFigure,
                            conceptNode,
                            lat.getContext()));
                }
            }
        });
        return downConceptLabelFigures;
    }

    private static Element storeUpConceptLabels(final LatticeDrawing drawing) {
        final Element upConceptLabelFigures = new Element(ConExpXMLElements.UP_CONCEPT_LABELS_ELEMENT);
        final Lattice lat = drawing.getLattice();
        lat.forEach(new Lattice.LatticeElementVisitor() {
            public void visitNode(LatticeElement conceptNode) {
                IFigureWithCoords conceptLabelFigure = drawing.getUpLabelForConcept(conceptNode);
                if (null != conceptLabelFigure) {
                    upConceptLabelFigures.addContent(storeFigureForConcept(ConExpXMLElements.CONCEPT_LABEL_FIGURE_TYPE,
                            conceptLabelFigure,
                            conceptNode,
                            lat.getContext()));
                }
            }
        });
        return upConceptLabelFigures;
    }

    private static Element storeSelection(FigureDrawingCanvas viewForLatticeComponent) {
        Element selection = new Element(ConExpXMLElements.SELECTION);
        final Collection selectionCollection = viewForLatticeComponent.getSelection();
        for (Iterator iterator = selectionCollection.iterator(); iterator.hasNext();) {
            Figure figure = (Figure) iterator.next();
            if (figure instanceof IFigureWithCoords) {
                IFigureWithCoords figureWithCoords = (IFigureWithCoords) figure;
                final Element child = storeFigureLocation(figureWithCoords);
                selection.addContent(child);
            }
        }
        return selection;
    }

    private static Element storeConceptLabels(final LatticeDrawing drawing) {
        final Element conceptLabelFigures = new Element(ConExpXMLElements.CONCEPT_LABELS_ELEMENT);
        final Lattice lat = drawing.getLattice();
        lat.forEach(new Lattice.LatticeElementVisitor() {
            public void visitNode(LatticeElement conceptNode) {
                IFigureWithCoords conceptLabelFigure = drawing.getLabelForConcept(conceptNode);
                if (null != conceptLabelFigure) {
                    conceptLabelFigures.addContent(storeFigureForConcept(ConExpXMLElements.CONCEPT_LABEL_FIGURE_TYPE,
                            conceptLabelFigure,
                            conceptNode,
                            lat.getContext()));
                }
            }
        });

        return conceptLabelFigures;
    }

    private static Element storeObjectLabels(LatticeDrawing drawing) {
        Element objectFigures = new Element(ConExpXMLElements.OBJECT_LABELS_ELEMENT);
        ExtendedContextEditingInterface cxt = drawing.getConceptSet().getContext();
        for (int i = 0; i < cxt.getObjectCount(); i++) {
            ContextEntity currObject = cxt.getObject(i);
            IFigureWithCoords objectLabel = drawing.getLabelForObject(currObject);
            if (null == objectLabel) {
                continue;
            }
            objectFigures.addContent(makeLabelFigureElement(ConExpXMLElements.OBJECT_LABEL_FIGURE_TYPE,
                    objectLabel,
                    ConExpXMLElements.OBJECT_REFERENCE,
                    i));
        }
        return objectFigures;
    }

    private static Element storeAttributeLabels(LatticeDrawing drawing) {
        Element attributesFigures = new Element(ConExpXMLElements.ATTRIBUTE_LABELS_ELEMENT);
        ExtendedContextEditingInterface cxt = drawing.getConceptSet().getContext();
        for (int i = 0; i < cxt.getAttributeCount(); i++) {
            ContextEntity currAttribute = cxt.getAttribute(i);
            IFigureWithCoords attributeLabel = drawing.getLabelForAttribute(currAttribute);
            if (null == attributeLabel) {
                continue;
            }
            attributesFigures.addContent(makeLabelFigureElement(ConExpXMLElements.ATTRIBUTE_LABEL_FIGURE_TYPE,
                    attributeLabel,
                    ConExpXMLElements.ATTRIBUTE_REFERENCE,
                    i));
        }
        return attributesFigures;
    }

    private static Element storeDrawingSettings(LatticeDrawing drawing) {
        Element settingElement = new Element(ConExpXMLElements.LATTICE_DIAGRAM_SETTINGS);
        settingElement.addContent(storeAttributeDisplayMode(drawing));
        settingElement.addContent(storeObjectDisplayMode(drawing));
        settingElement.addContent(storeLabelsSize(drawing));
        settingElement.addContent(storeShowCollision(drawing));
        settingElement.addContent(storeMaxNodeRadius(drawing));
        settingElement.addContent(storeNodeRadiusMode(drawing));
        settingElement.addContent(storeEdgeSizeMode(drawing));
        settingElement.addContent(storeHighlightMode(drawing));
        settingElement.addContent(storeGridSizeX(drawing));
        settingElement.addContent(storeGridSizeY(drawing));
        settingElement.addContent(storeLayout(drawing));
        return settingElement;
    }

    private static Element storeLayout(LatticeDrawing drawing) {
        return makeSettingElementForStrategyValueItem(
                ConExpXMLElements.LATTICE_LAYOUT,
                drawing.getLayoutStrategyItem());
    }

    private static Element storeGridSizeX(LatticeDrawing drawing) {
        return makeSettingElement(ConExpXMLElements.GRID_SIZE_X, String.valueOf(drawing.getDrawParams().getGridSizeX()));
    }

    private static Element storeGridSizeY(LatticeDrawing drawing) {
        return makeSettingElement(ConExpXMLElements.GRID_SIZE_Y, String.valueOf(drawing.getDrawParams().getGridSizeY()));
    }

    private static Element storeHighlightMode(LatticeDrawing drawing) {
        return makeSettingElementForStrategyValueItem(
                ConExpXMLElements.HIGHLIGHT_MODE,
                drawing.getHighlightStrategyItem());
    }

    private static Element makeSettingElementForStrategyValueItem(
            String highlightMode, StrategyValueItem strategyValueItem) {
        return makeSettingElement(highlightMode, strategyValueItem.getStrategyKey());
    }

    private static Element storeEdgeSizeMode(LatticeDrawing drawing) {
        return makeSettingElementForStrategyValueItem(
                ConExpXMLElements.EDGE_DISPLAY_MODE,
                drawing.getEdgeSizeCalcStrategyItem());
    }

    private static Element storeNodeRadiusMode(LatticeDrawing drawing) {
        return makeSettingElementForStrategyValueItem(
                ConExpXMLElements.NODE_RADIUS_MODE,
                drawing.getNodeRadiusStrategyItem());
    }

    private static Element storeMaxNodeRadius(LatticeDrawing drawing) {
        return makeSettingElement(ConExpXMLElements.MAX_NODE_RADIUS,
                String.valueOf(drawing.getDrawParams().getMaxNodeRadius()));
    }

    private static Element storeShowCollision(LatticeDrawing drawing) {
        return makeSettingElement(ConExpXMLElements.SHOW_COLLISIONS,
                String.valueOf(drawing.getDrawParams().isShowCollisions()));
    }


    private static Element storeObjectDisplayMode(LatticeDrawing drawing) {
        return makeSettingElement(ConExpXMLElements.OBJECT_LABEL_STRATEGY_DISPLAY_MODE,
                drawing.getObjectLabelingStrategyKey());
    }

    private static Element storeAttributeDisplayMode(LatticeDrawing drawing) {
        return makeSettingElement(ConExpXMLElements.ATTRIBUTE_LABEL_DISPLAY_MODE,
                drawing.getAttributeLabelingStrategyKey());
    }

    private static Element makeSettingElement(final String elementName, final String elementValue) {
        Element element = new Element(elementName);
        element.setAttribute(ConExpXMLElements.VALUE_ATTRIBUTE, elementValue);
        return element;
    }

    private static Element storeLabelsSize(LatticeDrawing drawing) {
        return makeSettingElement(ConExpXMLElements.LABEL_FONT_SIZE, String.valueOf(drawing.getPainterOptions().getLabelsFontSize()));
    }

    private static Element makeLabelFigureElement(String labelType, IFigureWithCoords labelFigure, String referenceType, int i) {
        Element attributeLabelFigure = makeFigureElement(labelType);
        attributeLabelFigure.addContent(storeFigureLocation(labelFigure));
        attributeLabelFigure.addContent(makeReference(referenceType, i));
        return attributeLabelFigure;
    }

    private static Element makeReference(String referenceName, int i) {
        Element referenceElement = new Element(referenceName);
        referenceElement.setAttribute(ConExpXMLElements.ID_ATTRIBUTE, Integer.toString(i));
        return referenceElement;
    }

    private static Element storeConceptFigures(final LatticeDrawing drawing) {
        final Element conceptDrawings = new Element(ConExpXMLElements.CONCEPT_FIGURES_ELEMENT);
        final Lattice lat = drawing.getLattice();
        lat.forEach(new Lattice.LatticeElementVisitor() {
            public void visitNode(LatticeElement conceptNode) {
                AbstractConceptCorrespondingFigure conceptFigure = drawing.getFigureForConcept(conceptNode);
                conceptDrawings.addContent(storeConceptFigure(lat.getContext(), conceptFigure));
            }
        });
        return conceptDrawings;
    }

    private static Element storeConceptFigure(AttributeInformationSupplier attrInfo, AbstractConceptCorrespondingFigure conceptFigure) {
        return storeFigureForConcept(ConExpXMLElements.CONCEPT_FIGURE_TYPE, conceptFigure, conceptFigure.getConcept(), attrInfo);
    }

    private static Element storeFigureForConcept(String figureType, IFigureWithCoords figure, ItemSet concept, AttributeInformationSupplier attrInfo) {
        Element conceptFigureElement = makeFigureElement(figureType);
        conceptFigureElement.addContent(storeFigureLocation(figure));
        conceptFigureElement.addContent(makeIntent(concept.getAttribs()));
        return conceptFigureElement;
    }

    private static Element makeFigureElement(String figureType) {
        final Element conceptFigureElement = new Element(ConExpXMLElements.FIGURE);
        conceptFigureElement.setAttribute(ConExpXMLElements.FIGURE_TYPE_ATTRIBUTE, figureType);
        return conceptFigureElement;
    }

    private static Element storeFigureLocation(IFigureWithCoords f) {
        Element figureCenter = new Element(ConExpXMLElements.FIGURE_COORDS);
        figureCenter.addContent(XMLGeneralTypesUtil.storePoint2D(f.getCenter()));
        return figureCenter;
    }


    private static Element storeContext(ExtendedContextEditingInterface cxt) {
        Element contextElement = new Element(ConExpXMLElements.CONTEXT_ELEMENT);
        contextElement.setAttribute(ConExpXMLElements.ID_ATTRIBUTE, "0");
        contextElement.setAttribute(ConExpXMLElements.CONTEXT_ELEMENT_TYPE_ATTRIBUTE, ConExpXMLElements.CONTEXT_TYPE_BINARY);

        contextElement.addContent(storeAttributes(cxt));
        contextElement.addContent(storeObjects(cxt));

        return contextElement;
    }

    private static Element storeAttributes(ExtendedContextEditingInterface cxt) {
        Element attributes = new Element(ConExpXMLElements.ATTRIBUTE_COLLECTION);
        for (int i = 0; i < cxt.getAttributeCount(); i++) {
            attributes.addContent(makeAttributeElement(i, cxt));
        }
        return attributes;
    }

    private static Element makeAttributeElement(int i, ExtendedContextEditingInterface cxt) {
        Element attribute = new Element(ConExpXMLElements.ATTRIBUTE_ELEMENT);
        attribute.setAttribute(ConExpXMLElements.ID_ATTRIBUTE, Integer.toString(i));
        attribute.addContent(makeNameElement(cxt.getAttribute(i).getName()));
        return attribute;
    }

    private static Element makeNameElement(final String entityName) {
        Element nameElement = new Element(ConExpXMLElements.ELEMENT_NAME_ELEMENT);
        nameElement.addContent(entityName);
        return nameElement;
    }

    private static Element storeObjects(ExtendedContextEditingInterface cxt) {
        Element objects = new Element(ConExpXMLElements.OBJECT_COLLECTION);
        for (int i = 0; i < cxt.getObjectCount(); i++) {
            objects.addContent(makeObjectElement(cxt, i));
        }
        return objects;
    }

    private static Element makeObjectElement(ExtendedContextEditingInterface cxt, int i) {
        Element object = new Element(ConExpXMLElements.OBJECT_ELEMENT);
        object.addContent(makeNameElement(cxt.getObject(i).getName()));
        object.addContent(makeObjectIntent(cxt, i));
        return object;
    }

    private static Element makeObjectIntent(ExtendedContextEditingInterface cxt, int i) {
        return makeIntent(cxt.getRelation().getSet(i));
    }

    private static Element makeIntent(Set intent) {
        Element relation = new Element(ConExpXMLElements.INTENT);
        writeSet(intent, relation);
        return relation;
    }

    private static void writeSet(Set intent, Element relation) {
        final int size = intent.size();
        for (int j = 0; j < size; j++) {
            if (intent.in(j)) {
                Element el = new Element(ConExpXMLElements.OBJECT_HAS_ATTRIBUTE);
                el.setAttribute(ConExpXMLElements.ATTRIBUTE_ID, Integer.toString(j));
                relation.addContent(el);
            }
        }
    }

}

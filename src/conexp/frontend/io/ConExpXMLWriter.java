/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.io;

import canvas.figures.IFigureWithCoords;
import conexp.core.*;
import conexp.frontend.ContextDocument;
import conexp.frontend.DocumentWriter;
import conexp.frontend.SetProvidingEntitiesMask;
import conexp.frontend.components.LatticeComponent;
import conexp.frontend.latticeeditor.LatticeDrawing;
import conexp.frontend.latticeeditor.figures.AbstractConceptCorrespondingFigure;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;
import util.Assert;
import util.XMLGeneralTypesUtil;

import java.io.IOException;
import java.io.Writer;


public class ConExpXMLWriter implements DocumentWriter {

    public void storeDocument(conexp.frontend.Document document, Writer writer) throws IOException {
        if (!(document instanceof ContextDocument)) {
            throw new IOException("Can't store this type of document:" + document);
        }
        ContextDocument contextDocument = (ContextDocument) document;
        Element root = new Element(ConExpXMLElements.DOC_XML_ROOT);
        root.addContent(storeVersion());
        root.addContent(storeContexts(contextDocument));
        root.addContent(storeLattices(contextDocument));

        Document xmlDoc = new Document(root);
        XMLOutputter outputter = new XMLOutputter();
        outputter.output(xmlDoc, writer);
    }

    static final int MAJOR_VERSION = 1;
    static final int MINOR_VERSION = 0;

    private Element storeVersion() {
        Element version = new Element(ConExpXMLElements.VERSION_ELEMENT);
        version.setAttribute(ConExpXMLElements.VERSION_MAJOR_NUMBER_ATTRIBUTE, Integer.toString(MAJOR_VERSION));
        version.setAttribute(ConExpXMLElements.VERSION_MINOR_NUMBER_ATTRIBUTE, Integer.toString(MINOR_VERSION));
        return version;
    }

    private Element storeContexts(ContextDocument document) {
        Element contextCollection = new Element(ConExpXMLElements.CONTEXTS_COLLECTION);

        ExtendedContextEditingInterface cxt = document.getContext();

        contextCollection.addContent(storeContext(cxt));
        return contextCollection;
    }


    private Element storeLattices(ContextDocument document) {
        Element latticeCollection = new Element(ConExpXMLElements.LATTICE_COLLECTION);
        storeLatticeComponent(latticeCollection, document.getLatticeComponent());
        return latticeCollection;
    }

    private void storeLatticeComponent(Element latticeCollection, final LatticeComponent latticeComponent) {
        if (!latticeComponent.isEmpty()) {
            latticeCollection.addContent(makeLatticeElement(latticeComponent));
        }
    }

    private Element makeLatticeElement(LatticeComponent latticeComponent) {
        Element latticeElement = new Element(ConExpXMLElements.LATTICE_ELEMENT);
        storeFeatureMask(latticeComponent, latticeElement);
        storeDrawings(latticeComponent, latticeElement);
        return latticeElement;
    }

    private void storeFeatureMask(LatticeComponent latticeComponent, Element latticeElement) {
        latticeElement.addContent(makeAttributeMaskElement(latticeComponent.getAttributeMask()));
    }

    private Element makeAttributeMaskElement(SetProvidingEntitiesMask attributeMask) {
        Element attributeMaskElement = new Element(ConExpXMLElements.ATTRIBUTE_MASK_ELEMENT);
        writeSet(attributeMask.toSet(), attributeMaskElement);
        return attributeMaskElement;
    }

    private void storeDrawings(LatticeComponent latticeComponent, Element latticeElement) {
        latticeElement.addContent(makeDrawingElement(latticeComponent.getDrawing()));
    }

    private Element makeDrawingElement(LatticeDrawing drawing) {
        Element latticeDrawingElement = new Element(ConExpXMLElements.LATTICE_DIAGRAM);
        latticeDrawingElement.addContent(storeConceptFigures(drawing));
        latticeDrawingElement.addContent(storeDrawingSettings(drawing));
        if (drawing.hasLabelsForAttributes()) {
            latticeDrawingElement.addContent(storeAttributeLabels(drawing));
        }
        if (drawing.hasLabelsForObjects()) {
            latticeDrawingElement.addContent(storeObjectLabels(drawing));
        }
        if (drawing.hasLabelsForConcepts()) {
            latticeDrawingElement.addContent(storeConceptLabels(drawing));
        }
        return latticeDrawingElement;
    }

    private Element storeConceptLabels(final LatticeDrawing drawing) {
        final Element conceptLabelFigures = new Element(ConExpXMLElements.CONCEPT_LABELS_ELEMENT);
        final Lattice lat = drawing.getLattice();
        lat.forEach(new Lattice.LatticeElementVisitor() {
            public void visitNode(LatticeElement conceptNode) {
                IFigureWithCoords conceptLabelFigure = drawing.getLabelForConcept(conceptNode);
                if (null != conceptLabelFigure) {
                    conceptLabelFigures.addContent(
                            storeFigureForConcept(ConExpXMLElements.CONCEPT_LABEL_FIGURE_TYPE,
                                    conceptLabelFigure,
                                    conceptNode,
                                    lat.getContext()));
                }
            }
        });

        return conceptLabelFigures;
    }

    private Element storeObjectLabels(LatticeDrawing drawing) {
        Element objectFigures = new Element(ConExpXMLElements.OBJECT_LABELS_ELEMENT);
        ExtendedContextEditingInterface cxt = drawing.getConceptSet().getContext();
        for (int i = 0; i < cxt.getObjectCount(); i++) {
            ContextEntity currObject = cxt.getObject(i);
            IFigureWithCoords objectLabel = drawing.getLabelForObject(currObject);
            Assert.isTrue(null != objectLabel, "If drawing has labels, every object should has label");
            objectFigures.addContent(
                    makeLabelFigureElement(
                            ConExpXMLElements.OBJECT_LABEL_FIGURE_TYPE,
                            objectLabel,
                            ConExpXMLElements.OBJECT_REFERENCE,
                            i
                    )
            );
        }
        return objectFigures;
    }

    private Element storeAttributeLabels(LatticeDrawing drawing) {
        Element attributesFigures = new Element(ConExpXMLElements.ATTRIBUTE_LABELS_ELEMENT);
        ExtendedContextEditingInterface cxt = drawing.getConceptSet().getContext();
        for (int i = 0; i < cxt.getAttributeCount(); i++) {
            ContextEntity currAttribute = cxt.getAttribute(i);
            IFigureWithCoords attributeLabel = drawing.getLabelForAttribute(currAttribute);
            if (null == attributeLabel) {
                continue;
            }
            attributesFigures.addContent(
                    makeLabelFigureElement(
                            ConExpXMLElements.ATTRIBUTE_LABEL_FIGURE_TYPE,
                            attributeLabel,
                            ConExpXMLElements.ATTRIBUTE_REFERENCE,
                            i));
        }
        return attributesFigures;
    }

    private Element storeDrawingSettings(LatticeDrawing drawing) {
        Element settingElement = new Element(ConExpXMLElements.LATTICE_DIAGRAM_SETTINGS);
        settingElement.addContent(storeAttributeDisplayMode(drawing));
        settingElement.addContent(storeObjectDisplayMode(drawing));
        return settingElement;
    }

    private Element makeLabelFigureElement(String labelType, IFigureWithCoords labelFigure, String referenceType, int i) {
        Element attributeLabelFigure = makeFigureElement(labelType);
        attributeLabelFigure.addContent(storeFigureLocation(labelFigure));
        attributeLabelFigure.addContent(makeReference(referenceType, i));
        return attributeLabelFigure;
    }

    private Element makeReference(String referenceName, int i) {
        Element attributeReference = new Element(referenceName);
        attributeReference.setAttribute(ConExpXMLElements.ID_ATTRIBUTE, Integer.toString(i));
        return attributeReference;
    }

    private Element storeObjectDisplayMode(LatticeDrawing drawing) {
        Element objectsLabelsDisplayMode = new Element(ConExpXMLElements.OBJECT_LABEL_STRATEGY_DISPLAY_MODE);
        objectsLabelsDisplayMode.setAttribute(ConExpXMLElements.VALUE_ATTRIBUTE, drawing.getObjectLabelingStrategyKey());

        return objectsLabelsDisplayMode;
    }

    private Element storeAttributeDisplayMode(LatticeDrawing drawing) {
        Element attributeLabelsDisplayMode = new Element(ConExpXMLElements.ATTRIBUTE_LABEL_DISPLAY_MODE);
        attributeLabelsDisplayMode.setAttribute(ConExpXMLElements.VALUE_ATTRIBUTE, drawing.getAttributeLabelingStrategyKey());
        return attributeLabelsDisplayMode;
    }

    private Element storeConceptFigures(final LatticeDrawing drawing) {
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

    private Element storeConceptFigure(AttributeInformationSupplier attrInfo, AbstractConceptCorrespondingFigure conceptFigure) {
        return storeFigureForConcept(ConExpXMLElements.CONCEPT_FIGURE_TYPE, conceptFigure, conceptFigure.getConcept(), attrInfo);
    }

    private Element storeFigureForConcept(String figureType, IFigureWithCoords figure, ItemSet concept, AttributeInformationSupplier attrInfo) {
        Element conceptFigureElement = makeFigureElement(figureType);
        conceptFigureElement.addContent(storeFigureLocation(figure));
        conceptFigureElement.addContent(makeIntent(concept.getAttribs()));
        return conceptFigureElement;
    }

    private Element makeFigureElement(String figureType) {
        final Element conceptFigureElement = new Element(ConExpXMLElements.FIGURE);
        conceptFigureElement.setAttribute(ConExpXMLElements.FIGURE_TYPE_ATTRIBUTE, figureType);
        return conceptFigureElement;
    }

    private Element storeFigureLocation(IFigureWithCoords f) {
        Element figureCenter = new Element(ConExpXMLElements.FIGURE_COORDS);
        figureCenter.addContent(XMLGeneralTypesUtil.storePoint2D(f.getCenter()));
        return figureCenter;
    }


    private Element storeContext(ExtendedContextEditingInterface cxt) {
        Element contextElement = new Element(ConExpXMLElements.CONTEXT_ELEMENT);
        contextElement.setAttribute(ConExpXMLElements.ID_ATTRIBUTE, "0");
        contextElement.setAttribute(ConExpXMLElements.CONTEXT_ELEMENT_TYPE_ATTRIBUTE, ConExpXMLElements.CONTEXT_TYPE_BINARY);

        contextElement.addContent(storeAttributes(cxt));
        contextElement.addContent(storeObjects(cxt));

        return contextElement;
    }

    private Element storeAttributes(ExtendedContextEditingInterface cxt) {
        Element attributes = new Element(ConExpXMLElements.ATTRIBUTE_COLLECTION);
        for (int i = 0; i < cxt.getAttributeCount(); i++) {
            attributes.addContent(makeAttributeElement(i, cxt));
        }
        return attributes;
    }

    private Element makeAttributeElement(int i, ExtendedContextEditingInterface cxt) {
        Element attribute = new Element(ConExpXMLElements.ATTRIBUTE_ELEMENT);
        attribute.setAttribute(ConExpXMLElements.ID_ATTRIBUTE, Integer.toString(i));
        attribute.addContent(makeNameElement(cxt.getAttribute(i).getName()));
        return attribute;
    }

    private Element makeNameElement(final String entityName) {
        Element nameElement = new Element(ConExpXMLElements.ELEMENT_NAME_ELEMENT);
        nameElement.addContent(entityName);
        return nameElement;
    }

    private Element storeObjects(ExtendedContextEditingInterface cxt) {
        Element objects = new Element(ConExpXMLElements.OBJECT_COLLECTION);
        for (int i = 0; i < cxt.getObjectCount(); i++) {
            objects.addContent(makeObjectElement(cxt, i));
        }
        return objects;
    }

    private Element makeObjectElement(ExtendedContextEditingInterface cxt, int i) {
        Element object = new Element(ConExpXMLElements.OBJECT_ELEMENT);
        object.addContent(makeNameElement(cxt.getObject(i).getName()));
        object.addContent(makeObjectIntent(cxt, i));
        return object;
    }

    private Element makeObjectIntent(ExtendedContextEditingInterface cxt, int i) {
        return makeIntent(cxt.getRelation().getSet(i));
    }

    private Element makeIntent(Set intent) {
        Element relation = new Element(ConExpXMLElements.INTENT);
        writeSet(intent, relation);
        return relation;
    }

    private void writeSet(Set intent, Element relation) {
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

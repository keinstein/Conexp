/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.io;

public interface ConExpXMLElements {
    String DOC_XML_ROOT = "ConceptualSystem";
    String CONTEXTS_COLLECTION = "Contexts";

    String CONTEXT_ELEMENT = "Context";
    String CONTEXT_ELEMENT_TYPE_ATTRIBUTE = "Type";
    String CONTEXT_TYPE_BINARY = "Binary";

    String LATTICE_COLLECTION = "Lattices";
    String LATTICE_ELEMENT = "Lattice";
    String ATTRIBUTE_MASK_ELEMENT = "AttributeMask";
    String OBJECT_MASK_ELEMENT = "ObjectMask";

    String LATTICE_DIAGRAM = "LineDiagram";
    String LATTICE_DIAGRAM_SETTINGS = "LineDiagramSettings";
    String ATTRIBUTE_LABEL_DISPLAY_MODE = "AttributeLabelsDisplayMode";
    String OBJECT_LABEL_STRATEGY_DISPLAY_MODE = "ObjectLabelsDisplayMode";
    String LABEL_FONT_SIZE = "LabelFontSize";
    String SHOW_COLLISIONS = "ShowCollisions";
    String MAX_NODE_RADIUS = "MaxNodeRadius";
    String NODE_RADIUS_MODE = "NodeRadiusMode";
    String EDGE_DISPLAY_MODE = "EdgeDisplayMode";
    String HIGHLIGHT_MODE = "HighlightMode";
    String GRID_SIZE_X = "GridSizeX";
    String GRID_SIZE_Y = "GridSizeY";
    String SELECTION = "Selection";
    String LATTICE_LAYOUT = "Layout";

    String VALUE_ATTRIBUTE = "Value";
    String CONCEPT_FIGURES_ELEMENT = "ConceptFigures";
    String ATTRIBUTE_LABELS_ELEMENT = "AttributeLabels";
    String OBJECT_LABELS_ELEMENT = "ObjectLabels";
    String CONCEPT_LABELS_ELEMENT = "ConceptLabels";
    //In order to preserbe backward compatibility
    String DOWN_CONCEPT_LABELS_ELEMENT = CONCEPT_LABELS_ELEMENT;
    String UP_CONCEPT_LABELS_ELEMENT = "UpConceptLabels";


    String FIGURE = "LineDiagramFigure";
    String FIGURE_TYPE_ATTRIBUTE = "Type";
    String ATTRIBUTE_LABEL_FIGURE_TYPE = "AttributeLabel";
    String OBJECT_LABEL_FIGURE_TYPE = "ObjectLabel";
    String CONCEPT_FIGURE_TYPE = "Concept";
    String CONCEPT_LABEL_FIGURE_TYPE = "ConceptLabel";


    String FIGURE_COORDS = "FigureCooords";

    String ATTRIBUTE_COLLECTION = "Attributes";
    String ATTRIBUTE_ELEMENT = "Attribute";
    String ATTRIBUTE_REFERENCE = "AttributeReference";

    String OBJECT_COLLECTION = "Objects";
    String OBJECT_ELEMENT = "Object";
    String OBJECT_REFERENCE = "ObjectReference";

    String INTENT = "Intent";

    String OBJECT_HAS_ATTRIBUTE = "HasAttribute";
    String ATTRIBUTE_ID = "AttributeIdentifier";

    String ELEMENT_NAME_ELEMENT = "Name";
    String ID_ATTRIBUTE = "Identifier";

    String VERSION_ELEMENT = "Version";
    String VERSION_MAJOR_NUMBER_ATTRIBUTE = "MajorNumber";
    String VERSION_MINOR_NUMBER_ATTRIBUTE = "MinorNumber";

    String RECALCULATION_POLICY = "RecalculationPolicy";
}

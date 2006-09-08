/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.core.layout.layeredlayout;

import conexp.core.LatticeElement;
import conexp.core.layout.ConceptCoordinateMapper;
import util.collection.CollectionFactory;
import util.gui.GraphicObjectsFactory;

import java.awt.geom.Point2D;
import java.util.SortedSet;


public class AllConceptOnOneLayerHaveDifferentXCoordinatesEvaluationFunction implements IEvaluationFunction {
    private LatticeElement[][] elementsByLayers;
    ConceptCoordinateMapper conceptCoordinateMapper;

    public AllConceptOnOneLayerHaveDifferentXCoordinatesEvaluationFunction(ConceptCoordinateMapper mapper, LatticeElement[][] elementsByLayers) {
        this.elementsByLayers = elementsByLayers;
        setConceptCoordinateMapper(mapper);
    }

    public void setConceptCoordinateMapper(ConceptCoordinateMapper conceptCoordinateMapper) {
        this.conceptCoordinateMapper = conceptCoordinateMapper;
    }

    public double getEvaluationForLattice() {
        int violatedConstraints = 0;
        Point2D coords = GraphicObjectsFactory.makePoint2D();
        for (int layer = 0; layer < elementsByLayers.length; layer++) {
            LatticeElement[] layerInfo = elementsByLayers[layer];
            SortedSet coordinatesOfElementsInLayer = CollectionFactory.createSortedSet();
            for (int elementOfLayer = 0; elementOfLayer < layerInfo.length; elementOfLayer++) {
                conceptCoordinateMapper.setCoordsForConcept(layerInfo[elementOfLayer], coords);
                Double xcoord = new Double(coords.getX());
                coordinatesOfElementsInLayer.add(xcoord);
            }
            violatedConstraints += layerInfo.length - coordinatesOfElementsInLayer.size();
        }
        return -violatedConstraints;
    }
}

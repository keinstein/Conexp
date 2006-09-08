/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.core.layout.layeredlayout.tests;

import conexp.core.ItemSet;
import conexp.core.Lattice;
import conexp.core.LatticeElement;
import conexp.core.layout.ConceptCoordinateMapper;
import conexp.core.tests.SetBuilder;
import junit.framework.Assert;
import util.collection.CollectionFactory;
import util.gui.GraphicObjectsFactory;

import java.awt.geom.Point2D;
import java.util.Map;


public class MapBasedConceptCoordinateMapper implements ConceptCoordinateMapper {
    private final Map coordsForConcepts;

    public MapBasedConceptCoordinateMapper(Map coordsForConcepts) {
        this.coordsForConcepts = coordsForConcepts;
    }

    public void setCoordsForConcept(ItemSet c, Point2D coords) {
        Point2D location = (Point2D) coordsForConcepts.get(c);
        coords.setLocation(location);
    }

    public static ConceptCoordinateMapper buildMapperForLattice(Lattice lattice,
                                                                int[][] intents, double[][] coordinates) {
        Map map = CollectionFactory.createDefaultMap();
        Assert.assertEquals(intents.length, coordinates.length);
        for (int i = 0; i < intents.length; i++) {
            LatticeElement latticeElement = lattice.findElementWithIntent(SetBuilder.makeSet(intents[i]));
            double[] currentCoordinates = coordinates[i];
            Assert.assertEquals("there are should be only two coordinates", 2, currentCoordinates.length);
            Point2D point = GraphicObjectsFactory.makePoint2D(currentCoordinates[0], currentCoordinates[1]);
            Object prevValue = map.put(latticeElement, point);
            Assert.assertNull("intents shouldn't be repeated " + intents[i], prevValue);
        }
        return new MapBasedConceptCoordinateMapper(map);
    }
}

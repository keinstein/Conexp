/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.layout;

import conexp.core.ConceptIterator;
import conexp.core.LatticeElement;
import conexp.core.enumerators.ConceptFilterIterator;
import conexp.core.enumerators.ConceptIdealIterator;

import java.util.Iterator;

public class FreeseQuantativeLayout extends FreezeBaseLayout {

    protected synchronized void update(double att, double repulsion) {
        //pay attension to size-1;
        int size = lattice.conceptsCount();
        double[] forces = new double[2];
        for (int i = 0; i < size; i++) {
            LatticeElement x = lattice.elementAt(i);
            Point3D currCoords = getConceptInfo(x).coords;
            ConceptIterator filter = currIter % 2 == 0 ? (ConceptIterator) new ConceptFilterIterator(x) : (ConceptIterator) new ConceptIdealIterator(x);
            filter.nextConcept(); // Skip the first element which is x.
            while (filter.hasNext()) {
                LatticeElement otherConcept = filter.nextConcept();
                GenericForceDirectedLayouter.ForceDirectConceptInfo conceptInfo = getConceptInfo(otherConcept);
                Point3D otherCoords = conceptInfo.coords;
                double actualAttr = 1.0 / (x.getOwnObjCnt() + otherConcept.getOwnObjCnt()) * att;
                attraction(currCoords, otherCoords, actualAttr, forces);

                currCoords.adjustForce(forces[0], forces[1]);
                otherCoords.adjustForce(-forces[0], -forces[1]);
            }

            Iterator list = (currIter % 2 == 0 ? getHighIncomparablesForConcept(x) : getLowIncomparablesForConcept(x)).iterator();
            while (list.hasNext()) {
                Point3D otherCoords = getConceptInfo((LatticeElement) list.next()).coords;
                repulsion(currCoords,
                        otherCoords,
                        repulsion, forces);
                currCoords.adjustForce(forces[0], forces[1]);
                otherCoords.adjustForce(-forces[0], -forces[1]);
            }
        }
        updateConceptsCoords();
    }
}

/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/

package conexp.core.layout;

import conexp.core.ConceptIterator;
import conexp.core.ItemSet;
import conexp.core.LatticeElement;
import conexp.core.enumerators.ConceptFilterIterator;
import conexp.core.enumerators.ConceptIdealIterator;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

public class FreezeLayout extends SimpleForceLayout {
    static class FreezeLayoutConceptInfo extends ForceDirectConceptInfo {
        Collection highIncomparables;
        Collection lowIncomparables;
    }

    protected synchronized void update(float att, float repulsion) {
        //pay attension to size-1;
        int size = lattice.conceptsCount();
        float[] forces = new float[2];
        for (int i = 0; i < size; i++) {
            LatticeElement x = lattice.elementAt(i);
            Point3D currCoords = getConceptInfo(x).coords;
            ConceptIterator filter = (currIter % 2 == 0 ? (ConceptIterator) new ConceptFilterIterator(x) : (ConceptIterator) new ConceptIdealIterator(x));
            filter.nextConcept(); // Skip the first element which is x.
            while (filter.hasNext()) {
                Point3D otherCoords = getConceptInfo(filter.nextConcept()).coords;
                attraction(currCoords, otherCoords, att, forces);

                currCoords.adjustForce(forces[0], forces[1]);
                otherCoords.adjustForce(-forces[0], -forces[1]);
            }

            Iterator list = (currIter % 2 == 0 ? getHighIncomparablesForConcept(x) : getLowIncomparablesForConcept(x)).iterator();
            while (list.hasNext()) {
                Point3D otherCoords = getConceptInfo((LatticeElement) list.next()).coords;
                repulsion(
                        currCoords,
                        otherCoords,
                        repulsion, forces);
                currCoords.adjustForce(forces[0], forces[1]);
                otherCoords.adjustForce(-forces[0], -forces[1]);
            }
        }
        updateConceptsCoords();
    }


    // This finds the attraction between two points and updates their currentForce.
    protected void attraction(Point3D pt1, Point3D pt2, float att_fac, float[] res) {
        res[0] = att_fac * (pt2.x - pt1.x);
        res[1] = att_fac * (pt2.y - pt1.y);
    }

    /**
     * Insert the method's description here.
     * Creation date: (07.03.01 1:21:12)
     */
    private void calcIncomparables() {
        int n = lattice.conceptsCount();
        Arrays.sort(topSorted, new ConceptRankComparator());
        for (int i = 0; i < n; i++) {
            LatticeElement curr = topSorted[i];
            LinkedList list = new LinkedList();
            int j = i;
            FreezeLayoutConceptInfo currInf = getLocalConceptInfo(curr);
            while (j >= 0 && getConceptInfo(topSorted[j]).rank == currInf.rank) {
                j--;
            }

            for (; ++j < n;) {
                int cmp = curr.compare(topSorted[j]);
                if (!(cmp == LatticeElement.LESS || cmp == LatticeElement.EQUAL)) {
                    list.add(topSorted[j]);
                }
            }
            currInf.highIncomparables = list;
        }

        for (int i = n; --i >= 0;) {
            LatticeElement curr = topSorted[i];
            LinkedList list = new LinkedList();
            int j = i;
            FreezeLayoutConceptInfo currInf = getLocalConceptInfo(curr);
            while (j < n && getConceptInfo(topSorted[j]).rank == currInf.rank) {
                j++;
            }

            for (; --j > 0;) {
                int cmp = curr.compare(topSorted[j]);
                if (!(cmp == LatticeElement.GREATER || cmp == LatticeElement.EQUAL)) {
                    list.add(topSorted[j]);
                }
            }
            currInf.lowIncomparables = list;
        }
    }

    /**
     * Insert the method's description here.
     * Creation date: (09.03.01 10:57:52)
     * @return java.lang.Object
     * @param el conexp.core.LatticeElement
     */
    protected FreezeLayoutConceptInfo getLocalConceptInfo(int index) {
        return (FreezeLayoutConceptInfo) elementMap[index];
    }

    /**
     * Insert the method's description here.
     * Creation date: (11.03.01 1:57:30)
     */
    protected void localInit() {
        super.localInit();
        calcIncomparables();
    }

    /**
     * Comments for the makeConceptInfo method.
     * @return conexp.core.layout.GenericLayouter$LayoutConceptInfo
     */
    protected LayoutConceptInfo makeConceptInfo() {
        return new FreezeLayoutConceptInfo();
    }

    // This finds the repulsion between two points and updates their currentForce.
    protected void repulsion(Point3D pt1, Point3D pt2, float repulsionFactor, float[] res) {
        float dx = pt1.x - pt2.x;
        float dy = pt1.y - pt2.y;
        int dz = pt1.z - pt2.z;
        float inv_d_cubed;
        if (dz == 0) {
            if (Math.abs(dx) < 0.2 && Math.abs(dy) < 0.2) {
                inv_d_cubed = (float) 37.0;
            } else if (Math.abs(dx) < 1.0 && (Math.abs(dy) < 1.0)) {
                inv_d_cubed = (float) 1.0 / ((float) Math.pow(Math.abs(dx), 2) +
                        (float) Math.pow(Math.abs(dy), 2));
            } else {
                inv_d_cubed = (float) 1.0 / ((float) Math.pow(Math.abs(dx), 3) +
                        (float) Math.pow(Math.abs(dy), 3) +
                        (float) Math.pow(Math.abs(dz), 3));
            }
        } else {
            inv_d_cubed = (float) 1.0 / ((float) Math.pow(Math.abs(dx), 3) +
                    (float) Math.pow(Math.abs(dy), 3) +
                    (float) Math.pow(Math.abs(dz), 3));
        }

        dx *= inv_d_cubed * repulsionFactor;
        dy *= inv_d_cubed * repulsionFactor;
        res[0] = dx;
        res[1] = dy;
    }


    /**
     * Insert the method's description here.
     * Creation date: (02.05.01 23:05:30)
     */
    protected int calcIterCount() {
        return lattice.conceptsCount() * 3 + 90;
    }


    /**
     * Insert the method's description here.
     * Creation date: (09.03.01 3:00:32)
     * @return java.util.Collection[]
     * @test_public
     */
    public java.util.Collection getHighIncomparablesForConcept(ItemSet el) {
        return getLocalConceptInfo(el).highIncomparables;
    }


    /**
     * Insert the method's description here.
     * Creation date: (09.03.01 10:57:52)
     * @return java.lang.Object
     * @param el conexp.core.LatticeElement
     * @test_public
     */
    public FreezeLayoutConceptInfo getLocalConceptInfo(ItemSet el) {
        return (FreezeLayoutConceptInfo) elementMap[el.getIndex()];
    }


    /**
     * Insert the method's description here.
     * Creation date: (09.03.01 3:00:32)
     * @return java.util.Collection[]
     * @test_public
     */
    public java.util.Collection getLowIncomparablesForConcept(ItemSet el) {
        return getLocalConceptInfo(el).lowIncomparables;
    }
}

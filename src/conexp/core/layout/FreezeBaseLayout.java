/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.core.layout;

import conexp.core.ItemSet;
import conexp.core.LatticeElement;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;


public abstract class FreezeBaseLayout extends SimpleForceLayout {
    /**
     * Insert the method's description here.
     * Creation date: (07.03.01 1:21:12)
     */
    private void calcIncomparables() {
        int n = lattice.conceptsCount();
        Arrays.sort(topSorted, new ConceptRankComparator());
        for (int i = 0; i < n; i++) {
            LatticeElement curr = topSorted[i];
            List list = new LinkedList();
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
            List list = new LinkedList();
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
     * Creation date: (11.03.01 1:57:30)
     */
    protected void localInit() {
        super.localInit();
        calcIncomparables();
    }

    // This finds the repulsion between two points and updates their currentForce.
    protected void repulsion(Point3D pt1, Point3D pt2, double repulsionFactor, double[] res) {
        double dx = pt1.getX() - pt2.getX();
        double dy = pt1.getY() - pt2.getY();
        int dz = pt1.z - pt2.z;
        float inv_d_cubed;
        if (dz == 0) {
            if (Math.abs(dx) < 0.2 && Math.abs(dy) < 0.2) {
                inv_d_cubed = 37.0f;
            } else if (Math.abs(dx) < 1.0 && Math.abs(dy) < 1.0) {
                inv_d_cubed = 1.0f / ((float) Math.pow(Math.abs(dx), 2) +
                        (float) Math.pow(Math.abs(dy), 2));
            } else {
                inv_d_cubed = 1.0f / ((float) Math.pow(Math.abs(dx), 3) +
                        (float) Math.pow(Math.abs(dy), 3) +
                        (float) Math.pow(Math.abs(dz), 3));
            }
        } else {
            inv_d_cubed = 1.0f / ((float) Math.pow(Math.abs(dx), 3) +
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
     *
     * @return java.util.Collection[]
     * @test_public
     */
    public Collection getHighIncomparablesForConcept(ItemSet el) {
        return getLocalConceptInfo(el).highIncomparables;
    }

    /**
     * Insert the method's description here.
     * Creation date: (09.03.01 10:57:52)
     *
     * @param el conexp.core.LatticeElement
     * @return java.lang.Object
     * @test_public
     */
    public FreezeLayoutConceptInfo getLocalConceptInfo(ItemSet el) {
        return (FreezeLayoutConceptInfo) elementMap[el.getIndex()];
    }

    /**
     * Insert the method's description here.
     * Creation date: (09.03.01 3:00:32)
     *
     * @return java.util.Collection[]
     * @test_public
     */
    public Collection getLowIncomparablesForConcept(ItemSet el) {
        return getLocalConceptInfo(el).lowIncomparables;
    }

    // This finds the attraction between two points and updates their currentForce.
    protected void attraction(Point3D pt1, Point3D pt2, double att_fac, double[] res) {
        res[0] = att_fac * (pt2.getX() - pt1.getX());
        res[1] = att_fac * (pt2.getY() - pt1.getY());
    }

    /**
     * Comments for the makeConceptInfo method.
     *
     * @return conexp.core.layout.GenericLayouter$LayoutConceptInfo
     */
    protected LayoutConceptInfo makeConceptInfo() {
        return new FreezeLayoutConceptInfo();
    }

    static class FreezeLayoutConceptInfo extends ForceDirectConceptInfo {
        Collection highIncomparables;
        Collection lowIncomparables;
    }
}

/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/

package conexp.core.layout;

import conexp.core.ItemSet;
import conexp.core.LatticeElement;
import conexp.util.valuemodels.BoundedDoubleValue;
import util.comparators.ComparatorUtil;

import java.util.Comparator;


public abstract class GenericForceDirectedLayouter extends GenericLayouter {
    protected PrimeGenerator primeGen = new PrimeGenerator();

    private BoundedDoubleValue rotationAngle;
    protected LatticeElement[] topSorted;

    class ConceptRankComparator implements Comparator {
        public int compare(Object obj1, Object obj2) {
            int i1 = getConceptInfo((LatticeElement) obj1).rank;
            int i2 = getConceptInfo((LatticeElement) obj2).rank;
            return ComparatorUtil.compareInt(i1, i2);
        }
    }

    public static class ForceDirectConceptInfo extends LayoutConceptInfo {
        public Point3D coords = new Point3D();
        public int rank;

        public void project2D(double angle) {
            float half = 0.5f;
            coords.project2d(angle);
            coords.getProjection().x += half;
        }
    }

    private java.beans.PropertyChangeSupport propertyChange;
    private java.beans.PropertyChangeListener propertyChangeListener;

    static final String ROTATION_ANGLE_PROPERTY = "rotationAngle";

    protected void setupDiagram() {
        topSorted = lattice.topologicallySortedElements();
        calcRanks();
        calcInitialVerticesPosition();
        // template-method
        localInit();
        topSorted = null;
    }

















    // This does a translation in the x-y plane so that 0 is at the origin.
    // It returns a scale factor which can be used to get the coords in [0,1].

    protected synchronized float translateCoordsRelativeToZeroAndReturnDiameter() {
        Point3D p = getConceptInfo(0).coords;

        float x0 = p.x;
        float y0 = p.y;
        float maxSq = (float) 0.0;

        for (int i = lattice.conceptsCount(); --i >= 0;) {
            p = getConceptInfo(i).coords;
            p.x -= x0;
            p.y -= y0;
            float distSq = p.x * p.x + p.y * p.y;
            if (distSq > maxSq) maxSq = distSq;
        }
        float maxZ = getConceptInfo(lattice.getOne()).coords.z;

        return (maxZ * maxZ > 4 * maxSq ? maxZ : 2 * (float) Math.sqrt(maxSq));
    }


    protected synchronized void normalizeCoords(float scaleFactor) {
        if (scaleFactor == 0) {
            return;
        }
//        System.out.println("Scale factor ="+scaleFactor);
        for (int i = lattice.conceptsCount(); --i >= 0;) {
            getConceptInfo(i).coords.setNormalizedCoords(scaleFactor);
        }
    }


    /**
     * Insert the method's description here.
     * Creation date: (09.03.01 12:04:52)
     * @return int[]
     */
    private int[] calcDepthsForConcepts() {
        int size = lattice.conceptsCount();
        int[] depths = new int[size];
        for (int i = size; --i >= 0;) {
            int depth = 0;
            LatticeElement curr = topSorted[i];
            for (int j = curr.getSuccCount(); --j >= 0;) {
                int depth2 = 1 + depths[curr.getSucc(j).getIndex()];
                if (depth2 > depth)
                    depth = depth2;
            }
            depths[curr.getIndex()] = depth;
        }
        return depths;
    }

    /**
     * Insert the method's description here.
     * Creation date: (20.02.01 0:55:19)
     */
    private void calcInitialVerticesPosition() {
        primeGen.reset();
        float PI = (float) Math.PI;
        int size = topSorted.length;
        for (int i = 0; i < size;) {
            ForceDirectConceptInfo inf = getConceptInfo(topSorted[i]);
            int rank = inf.rank;
            // j will be the number of vertices with the same rank as the ith one.
            int j;
            for (j = 1; j < size - i; j++) {
                if (rank != getConceptInfo(topSorted[i + j]).rank) {
                    break;
                }
            }
            float angle = 2 * PI / j;

            for (int k = 0; k < j; k++) {
                Point3D pt = getConceptInfo(topSorted[i + k]).coords;
                pt.z = rank;
                pt.x = j * (float) Math.cos(k * angle + PI / primeGen.nextPrime());
                pt.y = j * (float) Math.sin(k * angle + PI / primeGen.nextPrime());
            }
            i += j;
        }
    }

    /**
     * Insert the method's description here.
     * Creation date: (07.03.01 23:57:47)
     */
    protected void calcProjectedCoords() {
        float scale = translateCoordsRelativeToZeroAndReturnDiameter();
        normalizeCoords(scale);
        project2d(getRotationAngle().getValue());
    }

    /**
     * Insert the method's description here.
     * Creation date: (09.03.01 11:26:10)
     */
    private void calcRanks() {
        int[] depths = calcDepthsForConcepts();
        for (int i = lattice.conceptsCount(); --i >= 0;) {
            LatticeElement concept = lattice.elementAt(i);
            ForceDirectConceptInfo conInf = getConceptInfo(concept);
            conInf.rank = lattice.getHeight() + concept.getHeight() - depths[concept.getIndex()];
        }
    }

    /**
     * Insert the method's description here.
     * Creation date: (11.03.01 1:47:03)
     */
    protected abstract void forceDirectedPlacement();

    protected ForceDirectConceptInfo getConceptInfo(int index) {
        return (ForceDirectConceptInfo) elementMap[index];
    }

    protected abstract void localInit();


    protected synchronized java.beans.PropertyChangeSupport getPropertyChange() {
        if (null == propertyChange) {
            propertyChange = new java.beans.PropertyChangeSupport(this);
            propertyChange.addPropertyChangeListener(getPropertyChangeListener());
        }
        return propertyChange;
    }

    protected synchronized java.beans.PropertyChangeListener getPropertyChangeListener() {
        if (null == propertyChangeListener) {
            propertyChangeListener = new java.beans.PropertyChangeListener() {
                public void propertyChange(java.beans.PropertyChangeEvent evt) {
                    if (ROTATION_ANGLE_PROPERTY.equals(evt.getPropertyName())) {
                        projectAndAssignCoords();
                    }
                }
            };
        }
        return propertyChangeListener;
    }

    public synchronized BoundedDoubleValue getRotationAngle() {
        if (null == rotationAngle) {
            rotationAngle = new BoundedDoubleValue(ROTATION_ANGLE_PROPERTY, 0., 0., 2 * Math.PI, 360);
            rotationAngle.setPropertyChange(getPropertyChange());
        }
        return rotationAngle;
    }

    protected synchronized void project2d(double angle) {
        for (int i = lattice.conceptsCount(); --i >= 0;) {
            getConceptInfo(i).project2D(angle);
        }
    }

    protected void projectAndAssignCoords() {
        calcProjectedCoords();
        assignCoordsToLattice();
    }

    public void calcInitialPlacement() {
        setupDiagram();
        projectAndAssignCoords();
    }

    public ForceDirectConceptInfo getConceptInfo(ItemSet el) {
        return (ForceDirectConceptInfo) elementMap[el.getIndex()];
    }

    public boolean isIncremental() {
        return true;
    }
}

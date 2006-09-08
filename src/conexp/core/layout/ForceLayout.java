/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.layout;

import conexp.core.LatticeElement;

public class ForceLayout extends SimpleForceLayout {


    protected synchronized void update(double att, double repulsion) {
        //pay attension to size-1;
        int size = lattice.conceptsCount();
        double[] forces = new double[2];
        for (int i = 0; i < size; i++) {
            LatticeElement x = lattice.elementAt(i);
            Point3D currCoords = getConceptInfo(x).coords;
            for (int j = 0; j < size; j++) {
                Point3D otherCoords = getConceptInfo(lattice.elementAt(j)).coords;
                if (Point3D.distance(currCoords, otherCoords) > 3.) {
                    continue;
                }

                repulsion(currCoords,
                        otherCoords,
                        repulsion, forces);

                currCoords.adjustForce(forces[0], forces[1]);
                otherCoords.adjustForce(-forces[0], -forces[1]);
            }

            for (int j = x.getPredCount(); --j >= 0;) {
                LatticeElement currPred = x.getPred(j);
                Point3D otherCoords = getConceptInfo(currPred).coords;
                attraction(currCoords, otherCoords, att, forces);
                currCoords.adjustForce(forces[0], forces[1]);
                otherCoords.adjustForce(-forces[0], -forces[1]);
            }
        }
        updateConceptsCoords();
    }


    // This finds the attraction between two points and updates their currentForce.
    protected void attraction(Point3D pt1, Point3D pt2, double att_fac, double[] res) {
        res[0] = 3.0f * att_fac * (pt2.getX() - pt1.getX());
        res[1] = 3.0f * att_fac * (pt2.getY() - pt1.getY());
    }

    /**
     * Comments for the makeConceptInfo method.
     *
     * @return conexp.core.layout.GenericLayouter$LayoutConceptInfo
     */
    protected LayoutConceptInfo makeConceptInfo() {
        return new ForceDirectConceptInfo();
    }

    // This finds the repulsion between two points and updates their currentForce.
    protected void repulsion(Point3D pt1, Point3D pt2, double repulsionFactor, double[] res) {
        double dx = pt1.getX() - pt2.getX();
        double dy = pt1.getY() - pt2.getY();
        int dz = pt1.z - pt2.z;
        float inv_d_cubed;
        if (dz == 0 && -0.2 < dx && dx < 0.2 && -0.2 < dy && dy < 0.2) {
            inv_d_cubed = (float) 37.0;
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
        return lattice.conceptsCount() * 3;
    }
}

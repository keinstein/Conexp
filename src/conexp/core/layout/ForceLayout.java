/* Diagram.java	96/30/6


*/

package conexp.core.layout;

import conexp.core.LatticeElement;

public class ForceLayout extends SimpleForceLayout {


    protected synchronized void update(float att, float repulsion) {
        //pay attension to size-1;
        int size = lattice.conceptsCount();
        float[] forces = new float[2];
        for (int i = 0; i < size; i++) {
            LatticeElement x = lattice.elementAt(i);
            Point3D currCoords = getConceptInfo(x).coords;
            for (int j = 0; j < size; j++) {
                Point3D otherCoords = getConceptInfo(lattice.elementAt(j)).coords;
                if (Point3D.distance(currCoords, otherCoords) > 3.) {
                    continue;
                }

                repulsion(
                        currCoords,
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
    protected void attraction(Point3D pt1, Point3D pt2, float att_fac, float[] res) {
        res[0] = 3.0f * att_fac * (pt2.x - pt1.x);
        res[1] = 3.0f * att_fac * (pt2.y - pt1.y);
    }

    /**
     * Comments for the makeConceptInfo method.
     * @return conexp.core.layout.GenericLayouter$LayoutConceptInfo
     */
    protected LayoutConceptInfo makeConceptInfo() {
        return new ForceDirectConceptInfo();
    }

    // This finds the repulsion between two points and updates their currentForce.
    protected void repulsion(Point3D pt1, Point3D pt2, float repulsionFactor, float[] res) {
        float dx = pt1.x - pt2.x;
        float dy = pt1.y - pt2.y;
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
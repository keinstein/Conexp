/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.layout;

import conexp.util.gui.paramseditor.BoundedDoubleValueParamInfo;
import conexp.util.gui.paramseditor.ParamInfo;
import util.Assert;

import java.awt.geom.Point2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public abstract class SimpleForceLayout extends GenericForceDirectedLayouter {
    protected int currIter;
    protected ForceDistribution forceDistribution;

    abstract protected void update(double att, double repulsion);


    /**
     * Insert the method's description here.
     * Creation date: (07.03.01 23:54:57)
     */
    public void assignCoordsToLattice() {
        int levels = lattice.getHeight();
        double minYProjection = getConceptInfo(lattice.getZero()).coords.getProjection().getY();
        double maxYProjection = getConceptInfo(lattice.getOne()).coords.getProjection().getY();

        double yScaleFactor = maxYProjection - minYProjection;
        Assert.isTrue(yScaleFactor >= 0);

        final double sizeY = yScaleFactor > 0.02 ? levels * drawParams.getGridSizeY() / yScaleFactor : 1;

        double minX = 0;

        for (int i = lattice.conceptsCount(); --i >= 0;) {
            Point2D currProjection = getConceptInfo(lattice.elementAt(i)).coords.getProjection();
            if (minX > currProjection.getX()) {
                minX = currProjection.getX();
            }
        }

        final double sizeX = drawParams.getGridSizeX() * sizeY / drawParams.getGridSizeY();

        for (int i = lattice.conceptsCount(); --i >= 0;) {
            ForceDirectConceptInfo currInfo = getConceptInfo(lattice.elementAt(i));
            Point2D currProjection = currInfo.coords.getProjection();
            currInfo.x = sizeX * (currProjection.getX() - minX);
            currInfo.y = sizeY * (yScaleFactor - (currProjection.getY() - minYProjection));
        }
        fireLayoutChanged();
    }

    // This finds the attraction between two points and updates their currentForce.
    abstract protected void attraction(Point3D pt1, Point3D pt2, double att_fac, double[] res);

    /**
     * Insert the method's description here.
     * Creation date: (20.02.01 0:53:14)
     */
    private void calcForceFactors() {
        getForceDistribution().init(lattice.conceptsCount());
        currIter = 0;
    }

    /**
     * Insert the method's description here.
     * Creation date: (11.03.01 1:57:30)
     */
    protected void forceDirectedPlacement() {
        float[] forceFactors = getForceDistribution().forceConstantsForIterations(currIter++);
        update(forceFactors[0], forceFactors[1]);
    }

    /**
     * Comments for the improveOnce method.
     */
    public void improveOnce() {
        forceDirectedPlacement();
        projectAndAssignCoords();
    }

    /**
     * Insert the method's description here.
     * Creation date: (11.03.01 1:57:30)
     */
    protected void localInit() {
        calcForceFactors();
    }

    /**
     * Insert the method's description here.
     * Creation date: (07.03.01 1:15:54)
     */
    public void performLayout() {
        setupDiagram();
        calcProjectedCoords();
        int iterCount = calcIterCount();

        for (int i = 0; i < iterCount; i++) {
            forceDirectedPlacement();
        }

        projectAndAssignCoords();
    }

    abstract protected void repulsion(Point3D pt1, Point3D pt2, double repulsionFactor, double[] res);

    protected ParamInfo[] makeParams() {
        return new ParamInfo[]{
                new BoundedDoubleValueParamInfo("Angle", getRotationAngle()),
                new BoundedDoubleValueParamInfo("Attraction", getForceDistribution().getAttractionFactorModel()),
                new BoundedDoubleValueParamInfo("Repulsion", getForceDistribution().getRepulsionFactorModel()),
        };
    }


    /**
     * Insert the method's description here.
     * Creation date: (02.05.01 23:04:11)
     */
    protected abstract int calcIterCount();


    /**
     * Insert the method's description here.
     * Creation date: (29.05.01 6:20:08)
     *
     * @return conexp.core.layout.ForceDistribution
     */
    public ForceDistribution getForceDistribution() {
        if (null == forceDistribution) {
            forceDistribution = new ForceDistribution();
            forceDistribution.getPropertyChange().addPropertyChangeListener(new PropertyChangeListener() {
                public void propertyChange(PropertyChangeEvent evt) {
                    fireLayoutPropertyChanged(LAYOUT_PARAMS_CHANGE);
                }
            });
        }
        return forceDistribution;
    }


    /**
     * Insert the method's description here.
     * Creation date: (04.06.01 14:11:34)
     *
     * @return boolean
     */
    public boolean isDone() {
        return forceDistribution.isDone(currIter);
    }


    /**
     * Insert the method's description here.
     * Creation date: (02.05.01 23:24:52)
     */
    protected void updateConceptsCoords() {
        for (int i = lattice.conceptsCount(); --i >= 0;) {
            getConceptInfo(i).coords.update();
        }
    }
}

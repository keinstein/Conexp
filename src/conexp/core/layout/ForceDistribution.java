/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.layout;

import conexp.util.valuemodels.BoundedDoubleValue;

import java.beans.PropertyChangeSupport;


public class ForceDistribution {
    static final String REPULSION_PROPERTY = "repulsion";
    static final String ATTRACTION_PROPERTY = "attraction";

    private BoundedDoubleValue attractionFactorModel;
    private BoundedDoubleValue repulsionFactorModel;

    private PropertyChangeSupport propertyChange;

    private final static int PHASE_COUNT = 3;
    private int[] phaseBounds = new int[PHASE_COUNT - 1];
    private float[][] phaseConstants = new float[PHASE_COUNT][2];

    private int iterPerPhase;

    /**
     * Insert the method's description here.
     * Creation date: (12.03.01 22:43:06)
     *
     * @param currIter int
     * @return double[]
     */
    public float[] forceConstantsForIterations(int currIter) {
        for (int i = 0; i < PHASE_COUNT - 1; i++) {
            if (currIter < phaseBounds[i]) {
                return phaseConstants[i];
            }
        }
        return phaseConstants[PHASE_COUNT - 1];
    }

    /**
     * Insert the method's description here.
     * Creation date: (12.03.01 22:42:22)
     *
     * @param verticeCount int
     */
    public void init(int verticeCount) {
        float sqrtSize = (float) Math.sqrt(verticeCount);
        float localAttractionFactor = getAttractionFactor() / sqrtSize;
        float localRepulsionFactor = getRepulsionFactor() / sqrtSize;

        iterPerPhase = verticeCount + 30;

        phaseBounds[0] = iterPerPhase;
        phaseBounds[1] = phaseBounds[0] + iterPerPhase;


        phaseConstants[0][0] = 0.5f * localAttractionFactor;
        phaseConstants[0][1] = 3.0f * localRepulsionFactor;

        phaseConstants[1][0] = 3.0f * localAttractionFactor;
        phaseConstants[1][1] = 0.75f * localRepulsionFactor;

        phaseConstants[2][0] = 0.75f * localAttractionFactor;
        phaseConstants[2][1] = 1.5f * localRepulsionFactor;
    }


    public ForceDistribution() {
    }


    public float getAttractionFactor() {
        return (float) getAttractionFactorModel().getValue();
    }


    public BoundedDoubleValue getAttractionFactorModel() {
        if (null == attractionFactorModel) {
            attractionFactorModel = new BoundedDoubleValue(ATTRACTION_PROPERTY, 0.1, 0.1, 0.3, 20);
            attractionFactorModel.setPropertyChange(getPropertyChange());
        }
        return attractionFactorModel;
    }


    protected PropertyChangeSupport getPropertyChange() {
        if (null == propertyChange) {
            propertyChange = new PropertyChangeSupport(this);
        }
        return propertyChange;
    }


    public float getRepulsionFactor() {
        return (float) getRepulsionFactorModel().getValue();
    }


    public BoundedDoubleValue getRepulsionFactorModel() {
        if (null == repulsionFactorModel) {
            repulsionFactorModel = new BoundedDoubleValue(REPULSION_PROPERTY, 1.0, 0.1, 3.0, 29);
            repulsionFactorModel.setPropertyChange(getPropertyChange());
        }
        return repulsionFactorModel;
    }


    /**
     * Insert the method's description here.
     * Creation date: (04.06.01 14:07:38)
     *
     * @param iterCount int
     * @return boolean
     */
    public boolean isDone(int iterCount) {
        return iterCount > iterPerPhase * PHASE_COUNT;
    }
}

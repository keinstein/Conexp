/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor.noderadiusstrategy;

import conexp.frontend.latticeeditor.AbstractDrawingStrategyModel;
import conexp.frontend.latticeeditor.DrawParameters;

public class NodeRadiusStrategyModel extends AbstractDrawingStrategyModel {
    private final static int OWN_OBJECTS_VOLUME = 0;
    private static final int OWN_ATTRIBUTE_VOLUME = OWN_OBJECTS_VOLUME + 1;
    private final static int MAX_NODE_RADIUS_STRATEGY = OWN_ATTRIBUTE_VOLUME + 1;
    private final static int EXTENT_VOLUME = MAX_NODE_RADIUS_STRATEGY + 1;
    private final static int STABILITY_TO_DESTR_STRATEGY = EXTENT_VOLUME + 1;
    private final static int LAST_STRATEGY = STABILITY_TO_DESTR_STRATEGY;

//    private final static int POINT_STABILITY = STABILITY_TO_DESTR_STRATEGY+1;
//    private final static int INTEGRAL_STABILITY = POINT_STABILITY + 1;
//    private final static int LAST_STRATEGY = INTEGRAL_STABILITY;
    private final static int STRATEGY_COUNT = LAST_STRATEGY + 1;


    public NodeRadiusStrategyModel(DrawParameters opt) {
        super(opt);
    }

    protected void createStrategies(DrawParameters opt) {
        allocateStrategies(STRATEGY_COUNT);
        setStrategy(OWN_OBJECTS_VOLUME, "~ to own objects", new OwnObjectsVolumeNodeRadiusCalcStrategy(opt));
        setStrategy(OWN_ATTRIBUTE_VOLUME, "~ to own attributes", new OwnAttributesVolumeNodeRadiusCalcStrategy(opt));
        setStrategy(MAX_NODE_RADIUS_STRATEGY, "Fixed radius", new MaxNodeRadiusCalcStrategy(opt));
        setStrategy(EXTENT_VOLUME, "~ of object extent", new ObjectVolumeNodeRadiusCalcStrategy(opt));
        setStrategy(STABILITY_TO_DESTR_STRATEGY, "~ stability", new ConceptStabilityToDesctructionNodeRadiusCalcStrategy(opt));
//        setStrategy(POINT_STABILITY, "~ point stability", new ConceptPointStabilityNodeRadiusCalcStrategy(opt));
//        setStrategy(INTEGRAL_STABILITY, "~ integral stability", new ConceptIntegralStabilityNodeRadiusCalcStrategy(opt));
    }
}

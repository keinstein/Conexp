package conexp.frontend.latticeeditor.noderadiusstrategy;

import conexp.frontend.latticeeditor.AbstractDrawingStrategyModel;
import conexp.frontend.latticeeditor.DrawParameters;

public class NodeRadiusStrategyModel extends AbstractDrawingStrategyModel {
    private final static int OWN_OBJECTS_VOLUME  = 0;
    private final static int MAX_NODE_RADIUS_STRATEGY = OWN_OBJECTS_VOLUME + 1;
    private final static int EXTENT_VOLUME = MAX_NODE_RADIUS_STRATEGY  + 1;
    private final static int STABILITY_STRATEGY = EXTENT_VOLUME + 1;
    private final static int LAST_STRATEGY = STABILITY_STRATEGY;
//    private final static int LAST_STRATEGY = EXTENT_VOLUME;
    private final static int STRATEGY_COUNT = LAST_STRATEGY + 1;


    public NodeRadiusStrategyModel(DrawParameters opt) {
        super(opt);
    }

    protected void createStrategies(DrawParameters opt) {
        allocateStrategies(STRATEGY_COUNT);
        setStrategy(OWN_OBJECTS_VOLUME, "~ to own objects", new OwnObjectsVolumeNodeRadiusCalcStrategy(opt));
        setStrategy(MAX_NODE_RADIUS_STRATEGY, "Fixed radius", new MaxNodeRadiusCalcStrategy(opt));
        setStrategy(EXTENT_VOLUME, "~ of object extent", new ObjectVolumeNodeRadiusCalcStrategy(opt));
        setStrategy(STABILITY_STRATEGY, "~ stability", new ConceptStabilityNodeRadiusCalcStrategy(opt));
    }
}
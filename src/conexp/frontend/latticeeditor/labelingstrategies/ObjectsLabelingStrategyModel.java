package conexp.frontend.latticeeditor.labelingstrategies;

import conexp.frontend.latticeeditor.AbstractDrawingStrategyModel;
import conexp.frontend.latticeeditor.DrawParameters;

public class ObjectsLabelingStrategyModel extends AbstractDrawingStrategyModel {
    private final static int NO_OBJECTS_LABELS_STRATEGY = 0;
    private final static int OBJECTS_LABEL_STRATEGY = NO_OBJECTS_LABELS_STRATEGY + 1;
    private final static int OWN_OBJECTS_COUNT_LABEL_STRATEGY = OBJECTS_LABEL_STRATEGY + 1;
    private final static int OBJECTS_COUNT_LABEL_STRATEGY = OWN_OBJECTS_COUNT_LABEL_STRATEGY + 1;
    private final static int STABILITY_LABEL_STRATEGY = OBJECTS_COUNT_LABEL_STRATEGY + 1;
    private final static int LAST_STRATEGY = STABILITY_LABEL_STRATEGY;
//    private final static int LAST_STRATEGY = OBJECTS_COUNT_LABEL_STRATEGY;
    private final static int STRATEGY_COUNT = LAST_STRATEGY + 1;

    /**
     * Insert the method's description here.
     * Creation date: (24.12.00 3:44:08)
     * @param opt conexp.frontend.latticeeditor.LatticePainterOptions
     */
    public ObjectsLabelingStrategyModel(DrawParameters opt) {
        super(opt);
    }

    /**
     * createStrategies method comment.
     */
    protected void createStrategies(DrawParameters opt) {
        allocateStrategies(STRATEGY_COUNT);
        setStrategy(NO_OBJECTS_LABELS_STRATEGY, LabelingStrategiesKeys.NO_OBJECTS_LABELS_STRATEGY, "Don't show", NullLabellingStrategy.makeNull());
        setStrategy(OBJECTS_LABEL_STRATEGY, LabelingStrategiesKeys.ALL_OBJECTS_LABELING_STRATEGY, "Show labels", new AllObjectsLabelingStrategy(opt));
        setStrategy(OWN_OBJECTS_COUNT_LABEL_STRATEGY, LabelingStrategiesKeys.OWN_OBJECTS_COUNT_LABEL_STRATEGY, "Show own objects count", new OwnObjectsCountLabelingStrategy(opt));
        setStrategy(OBJECTS_COUNT_LABEL_STRATEGY, LabelingStrategiesKeys.OBJECTS_COUNT_LABEL_STRATEGY, "Show object count", new ObjectsCountLabelingStrategy(opt));
        setStrategy(STABILITY_LABEL_STRATEGY, LabelingStrategiesKeys.STABILITY_LABEL_STRATEGY, "Stability", new StabilityLabelingStrategy(opt));
    }
}
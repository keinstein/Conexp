/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.latticeeditor.labelingstrategies;

import conexp.frontend.latticeeditor.AbstractDrawingStrategyModel;
import conexp.frontend.latticeeditor.DrawParameters;

public class AttributesLabelingStrategyModel extends AbstractDrawingStrategyModel {
    private final static int NO_ATTRIBS_LABELS_STRATEGY = 0;
    private final static int ATTRIBS_LABEL_STRATEGY = NO_ATTRIBS_LABELS_STRATEGY + 1;
//    private final static int FULL_INTENT_LABELING_STRATEGY = ATTRIBS_LABEL_STRATEGY + 1;
//    private final static int FULL_CONCEPT_LABELING_STRATEGY = FULL_INTENT_LABELING_STRATEGY + 1;
    private final static int LAST_STRATEGY = ATTRIBS_LABEL_STRATEGY;//FULL_CONCEPT_LABELING_STRATEGY;
    private final static int STRATEGY_COUNT = LAST_STRATEGY + 1;

    /**
     * Insert the method's description here.
     * Creation date: (24.12.00 3:44:08)
     * @param opt conexp.frontend.latticeeditor.LatticePainterOptions
     */
    public AttributesLabelingStrategyModel(DrawParameters opt) {
        super(opt);
    }

    /**
     * createStrategies method comment.
     */
    protected void createStrategies(DrawParameters opt) {
        allocateStrategies(STRATEGY_COUNT);
        setStrategy(NO_ATTRIBS_LABELS_STRATEGY, LabelingStrategiesKeys.NO_ATTRIBS_LABELING_STRATEGY, "Don't show", NullLabellingStrategy.makeNull());
        setStrategy(ATTRIBS_LABEL_STRATEGY, LabelingStrategiesKeys.ALL_ATTRIBS_LABELING_STRATEGY_KEY, "Show labels", new AllAttribsLabelingStrategy(opt));
/*        setStrategy(FULL_INTENT_LABELING_STRATEGY, "Show full intent", new FullIntentLabelingStrategy(opt));
        setStrategy(FULL_CONCEPT_LABELING_STRATEGY, "Show full conexp", new FullConceptLabelingStrategy(opt)); */

    }
}

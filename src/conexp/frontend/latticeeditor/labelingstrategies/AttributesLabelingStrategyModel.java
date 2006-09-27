/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor.labelingstrategies;

import conexp.util.gui.strategymodel.AbstractNonGrowingStrategyModel;

public class AttributesLabelingStrategyModel extends AbstractNonGrowingStrategyModel {

    public AttributesLabelingStrategyModel() {
        super();
    }

    public String[][] getCreateInfo() {
        return new String[][]{
                {"Don't show", LabelingStrategiesKeys.NO_ATTRIBS_LABELING_STRATEGY,
                        "conexp.frontend.latticeeditor.labelingstrategies.NullLabellingStrategy"},
                {"Show labels", LabelingStrategiesKeys.ALL_ATTRIBS_LABELING_STRATEGY_KEY,
                        "conexp.frontend.latticeeditor.labelingstrategies.AllAttribsLabelingStrategy"},
                {"Show multi-labels", LabelingStrategiesKeys.ATTRIBS_MULTI_LABELING_STRATEGY_KEY,
                        "conexp.frontend.latticeeditor.labelingstrategies.AllAttribsMultiLineLabelingStrategy"},
                {"Show own attributes count", LabelingStrategiesKeys.OWN_ATTRIBS_COUNT_LABELING_STRATEGY,
                          "conexp.frontend.latticeeditor.labelingstrategies.OwnAttribsCountLabelingStrategy"},

        };
    }
}

/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.latticeeditor.labelingstrategies;

import conexp.frontend.latticeeditor.DrawParamsIndependentStrategyModel;

public class ObjectsLabelingStrategyModel extends DrawParamsIndependentStrategyModel{

    public ObjectsLabelingStrategyModel() {
    }


    public String[][] getCreateInfo() {
        return new String[][]{
            {"Don't show", LabelingStrategiesKeys.NO_OBJECTS_LABELS_STRATEGY, "conexp.frontend.latticeeditor.labelingstrategies.NullLabellingStrategy"},
            {"Show labels", LabelingStrategiesKeys.ALL_OBJECTS_LABELING_STRATEGY, "conexp.frontend.latticeeditor.labelingstrategies.AllObjectsLabelingStrategy"},
            {"Show own objects count", LabelingStrategiesKeys.OWN_OBJECTS_COUNT_LABEL_STRATEGY, "conexp.frontend.latticeeditor.labelingstrategies.OwnObjectsCountLabelingStrategy"},
            {"Show object count", LabelingStrategiesKeys.OBJECTS_COUNT_LABEL_STRATEGY, "conexp.frontend.latticeeditor.labelingstrategies.ObjectsCountLabelingStrategy"},
            {"Stability", LabelingStrategiesKeys.STABILITY_TO_DESCTRUCTION_LABEL_STRATEGY, "conexp.frontend.latticeeditor.labelingstrategies.StabilityToDesctructionLabelingStrategy"},
            {"Attribs multi line", "Attribs multi line", "conexp.frontend.latticeeditor.labelingstrategies.AllAttribsMultiLineLabelingStrategy"},
            {"Object multi line", "Object multi line", "conexp.frontend.latticeeditor.labelingstrategies.AllObjectsMultiLineLabelingStrategy"},
            {"Full concept", "Full concept", "conexp.frontend.latticeeditor.labelingstrategies.FullConceptLabelingStrategy"},


           // { "Point Stability", LabelingStrategiesKeys.POINT_STABILITY_LABEL_STRATEGY, "conexp.frontend.latticeeditor.labelingstrategies.PointStabilityLabelingStrategy"},
           // { "Integral stability", LabelingStrategiesKeys.INTEGRAL_STABILITY_LABEL_STRATEGY, "conexp.frontend.latticeeditor.labelingstrategies.IntegralStabilityLabelingStrategy"}
        };
    }

}

/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor.labelingstrategies;

public interface LabelingStrategiesKeys {
    String NO_ATTRIBS_LABELING_STRATEGY = "NoAttribsLabelsStrategy";
    String ALL_ATTRIBS_LABELING_STRATEGY_KEY = "AllAttribsLabelsStrategy";
    String ATTRIBS_MULTI_LABELING_STRATEGY_KEY = "AllAttribsMultiLabelsStrategy";
    String OWN_ATTRIBS_COUNT_LABELING_STRATEGY
            = "OwnAttribsCountLabelsStrategy";

    String NO_OBJECTS_LABELING_STRATEGY = "NoObjectsLabelsStrategy";
    String ALL_OBJECTS_LABELING_STRATEGY = "AllObjectsLabelsStrategy";
    String OWN_OBJECTS_COUNT_LABELING_STRATEGY = "OwnObjectsCountLabelsStrategy";
    String OBJECTS_MULTI_LABELING_STRATEGY_KEY = "AllObjectsMultiLabelsStrategy";

    String OBJECTS_COUNT_LABELING_STRATEGY = "ObjectsCountLabelsStrategy";
    String STABILITY_TO_DESCTRUCTION_LABELING_STRATEGY = "StabilityLabelsStrategy";

    String POINT_STABILITY_LABELING_STRATEGY = "PointStabilityLabelsStrategy";
    String INTEGRAL_STABILITY_LABELING_STRATEGY = "IntegralStabilityLabelsStrategy";
}

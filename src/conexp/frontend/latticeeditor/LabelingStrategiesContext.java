/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor;

import conexp.core.ExtendedContextEditingInterface;

public interface LabelingStrategiesContext {
    ILabelingStrategy getAttrLabelingStrategy();

    String getAttributeLabelingStrategyKey();

    boolean setAttributeLabelingStrategyByKey(String key);

    ILabelingStrategy getObjectsLabelingStrategy();

    String getObjectLabelingStrategyKey();

    boolean setObjectLabelingStrategyKey(String key);


    void initStrategies(ExtendedContextEditingInterface cxt, ConceptSetDrawing fd);

    void setupLabelingStrategies(ExtendedContextEditingInterface cxt);

    void shutdownStrategies(ConceptSetDrawing fd);
}

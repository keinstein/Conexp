package cefacade;


/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 */

public interface IContextReducabilityAnalyser {
    IEntityReducibilityInfo getObjectReducabilityInfo(int objectId);

    IEntityReducibilityInfo getAttributeReducabilityInfo(int attributeId);
}

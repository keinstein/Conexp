/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package cefacade;




public interface IContextReducabilityAnalyser {
    IEntityReducibilityInfo getObjectReducabilityInfo(int objectId);

    IEntityReducibilityInfo getAttributeReducabilityInfo(int attributeId);
}

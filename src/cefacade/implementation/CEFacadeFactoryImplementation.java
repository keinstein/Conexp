package cefacade.implementation;

import conexp.core.FCAEngineRegistry;

/**
 * Author: Serhiy Yevtushenko
 * Date: 17.01.2003
 * Time: 18:29:03
 */
public class CEFacadeFactoryImplementation {
    public static SimpleContextImplementation makeContext(int objectCount, int attributeCount) {
        return new SimpleContextImplementation(FCAEngineRegistry.makeContext(objectCount, attributeCount));
    }
}

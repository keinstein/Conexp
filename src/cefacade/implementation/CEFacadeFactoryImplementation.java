/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package cefacade.implementation;

import conexp.core.FCAEngineRegistry;


public class CEFacadeFactoryImplementation {
    private CEFacadeFactoryImplementation() {
    }

    public static SimpleContextImplementation makeContext(int objectCount, int attributeCount) {
        return new SimpleContextImplementation(FCAEngineRegistry.makeContext(objectCount, attributeCount));
    }
}

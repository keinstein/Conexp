/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package cefacade;

import cefacade.implementation.CEFacadeFactoryImplementation;


public class CEFacadeFactory {
    /**
     * Creates context with specified number of object and attributes
     * @param objectCount
     * @param attributeCount
     * @return empty context with #objectCount objects and #attributeCount attributes
     *        with default names
     */

    public static ISimpleContext makeContext(int objectCount, int attributeCount) {
        return CEFacadeFactoryImplementation.makeContext(objectCount, attributeCount);
    }

    /**
     * Creates empty context
     * @return empty context
     */

    public static ISimpleContext makeContext() {
        return makeContext(0, 0);
    }
}

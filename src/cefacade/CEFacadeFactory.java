package cefacade;

import cefacade.implementation.CEFacadeFactoryImplementation;

/**
 * Author: Serhiy Yevtushenko
 * Date: 17.01.2003
 * Time: 17:24:02
 */
public class CEFacadeFactory {
    /**
     * Creates context with specified number of object and attributes
     * @param objectCount
     * @param attributeCount
     * @return empty context with #objectCount objects and #attributeCount attributes
     *        with default names
     */

    public static ISimpleContext makeContext(int objectCount, int attributeCount){
        return CEFacadeFactoryImplementation.makeContext(objectCount, attributeCount);
    }

    /**
     * Creates empty context
     * @return empty context
     */

    public static ISimpleContext makeContext(){
        return makeContext(0, 0);
    }
}

package cefacade.implementation;

import cefacade.ISimpleContext;

/**
 * Author: Serhiy Yevtushenko
 * Date: 17.01.2003
 * Time: 18:30:57
 */
public class ContextReducerImplementation {
    public static ISimpleContext getReducedContext(ISimpleContext context) {
        SimpleContextImplementation contextWrapper = (SimpleContextImplementation)context.makeCopy();
        contextWrapper.getContext().reduceObjects();
        contextWrapper.getContext().reduceAttributes();
        return contextWrapper;
    }
}

/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/

package cefacade.implementation;

import cefacade.ISimpleContext;


public class ContextReducerImplementation {
    public static ISimpleContext getReducedContext(ISimpleContext context) {
        SimpleContextImplementation contextWrapper = (SimpleContextImplementation) context.makeCopy();
        contextWrapper.getContext().reduceObjects();
        contextWrapper.getContext().reduceAttributes();
        return contextWrapper;
    }
}

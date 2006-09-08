/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package cefacade;

import cefacade.implementation.ContextReducerImplementation;


public class ContextReducer implements IContextTranformer {


    public ISimpleContext transform(ISimpleContext context) {
        return getReducedContext(context);
    }

    /**
     * @param context - context, for which calculate reduced
     * @return reduced context
     */

    public static ISimpleContext getReducedContext(ISimpleContext context) {
        return ContextReducerImplementation.getReducedContext(context);
    }

}

package cefacade;

import cefacade.implementation.ContextReducerImplementation;

/**
 * Author: Serhiy Yevtushenko
 * Date: 17.01.2003
 * Time: 17:31:47
 */
public class ContextReducer implements IContextTranformer{


    public ISimpleContext transform(ISimpleContext context) {
        return getReducedContext(context);
    }

    /**
     * @param context - context, for which calculate reduced
     * @return reduced context
     */

    public static ISimpleContext getReducedContext(ISimpleContext context){
        return ContextReducerImplementation.getReducedContext(context);
    }

}

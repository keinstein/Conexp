/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package cefacade;


public interface IContextTranformer {
    ISimpleContext transform(ISimpleContext context);
}

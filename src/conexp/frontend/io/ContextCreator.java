/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.frontend.io;
import conexp.core.Context;
public interface ContextCreator {
    
    Context createContext(Object entity);
}


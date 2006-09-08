/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.frontend.io;



public class ConImpContextReaderFactory implements ContextReaderFactory {
    public ContextReader makeContextReader() {
        return new ConImpContextLoader();
    }
}

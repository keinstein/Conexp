package conexp.frontend.io;

/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 * Date: 16/10/2003
 * Time: 1:28:50
 */

public class TabSeparatedContextReaderFactory implements ContextReaderFactory {
    public ContextReader makeContextReader() {
        return new TabSeparatedContextReader();
    }
}

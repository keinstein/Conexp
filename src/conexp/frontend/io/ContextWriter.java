package conexp.frontend.io;

import conexp.core.ContextEditingInterface;

import java.io.IOException;
import java.io.Writer;

/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 */

public interface ContextWriter {
    void writeContext(ContextEditingInterface cxt, Writer w) throws IOException;
}

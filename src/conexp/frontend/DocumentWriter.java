/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend;

import java.io.IOException;
import java.io.Writer;

public interface DocumentWriter {
    void storeDocument(Document document, Writer writer) throws IOException;
}

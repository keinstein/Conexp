/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.io;

import conexp.core.ExtendedContextEditingInterface;
import conexp.frontend.ContextDocument;
import conexp.frontend.Document;
import conexp.frontend.DocumentWriter;

import java.io.IOException;
import java.io.Writer;

public class ConImpContextWriter implements DocumentWriter {
    private static final String newline = System.getProperty("line.separator");

    public void storeDocument(Document document, Writer w) throws IOException {
        if (!(document instanceof ContextDocument)) {
            throw new IOException("Can't process documents of type: " + document);
        }
        ContextDocument contextDocument = (ContextDocument) document;
        ExtendedContextEditingInterface cxt = contextDocument.getContext();
        try {
            w.write("B" + newline);//$NON-NLS-1$
            w.write(newline); //here was name
            int numObj = cxt.getObjectCount();
            w.write(numObj + newline);
            int numAttr = cxt.getAttributeCount();
            w.write(numAttr + newline + newline);
            for (int i = 0; i < numObj; i++) {
                w.write(cxt.getObject(i).getName() + newline);
            }
            for (int i = 0; i < numAttr; i++) {
                w.write(cxt.getAttribute(i).getName() + newline);
            }
            StringBuffer res = new StringBuffer(numAttr + 2);
            for (int i = 0; i < numObj; i++) {
                res.delete(0, res.length());
                for (int j = 0; j < numAttr; j++) {
                    res.append(cxt.getRelationAt(i, j) ? "X" : ".");//$NON-NLS-2$//$NON-NLS-1$
                }
                w.write(res.append(newline).toString());
            }
        } finally {
            w.close();
        }

    }
}

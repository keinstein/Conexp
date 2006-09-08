/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.io;

import conexp.core.ContextEditingInterface;
import conexp.frontend.ContextDocument;
import conexp.frontend.Document;
import conexp.frontend.DocumentWriter;

import java.io.IOException;
import java.io.Writer;

public class ConImpContextWriter implements DocumentWriter, ContextWriter {
    private static final String newline = System.getProperty("line.separator");

    public void storeDocument(Document document, Writer writer) throws IOException {
        if (!(document instanceof ContextDocument)) {
            throw new IOException("Can't process documents of type: " + document);
        }
        ContextDocument contextDocument = (ContextDocument) document;
        writeContext(contextDocument.getContext(), writer);
    }

    public void writeContext(ContextEditingInterface cxt, Writer writer) throws IOException {
        writer.write('B' + newline);//$NON-NLS-1$
        writer.write(newline); //here was name
        int numObj = cxt.getObjectCount();
        writer.write(numObj + newline);
        int numAttr = cxt.getAttributeCount();
        writer.write(numAttr + newline + newline);
        for (int i = 0; i < numObj; i++) {
            writer.write(cxt.getObject(i).getName() + newline);
        }
        for (int i = 0; i < numAttr; i++) {
            writer.write(cxt.getAttribute(i).getName() + newline);
        }
        StringBuffer res = new StringBuffer(numAttr + 2);
        for (int i = 0; i < numObj; i++) {
            res.delete(0, res.length());
            for (int j = 0; j < numAttr; j++) {
                res.append(cxt.getRelationAt(i, j) ? "X" : ".");//$NON-NLS-2$//$NON-NLS-1$
            }
            writer.write(res.append(newline).toString());
        }
    }
}

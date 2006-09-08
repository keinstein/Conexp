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

public class TabSeparatedContextWriter implements DocumentWriter, ContextWriter {
    private static final String newline = System.getProperty("line.separator");

    public void storeDocument(Document document, Writer writer) throws IOException {
        if (!(document instanceof ContextDocument)) {
            throw new IOException("Can't process documents of type: " + document);
        }
        ContextDocument contextDocument = (ContextDocument) document;
        writeContext(contextDocument.getContext(), writer);
    }

    public void writeContext(ContextEditingInterface cxt, Writer writer) throws IOException {
        int numObj = cxt.getObjectCount();
        int numAttr = cxt.getAttributeCount();
        String separator = "\t";
        StringBuffer headerLine = new StringBuffer();
        for (int i = 0; i < numAttr; i++) {
            headerLine.append(cxt.getAttribute(i).getName());
            headerLine.append(separator);
        }
        writer.write(headerLine.toString() + newline);
        writer.write(newline);
        StringBuffer res = new StringBuffer(2 * numAttr + 2);
        for (int i = 0; i < numObj; i++) {
            res.delete(0, res.length());
            for (int j = 0; j < numAttr; j++) {
                res.append(cxt.getRelationAt(i, j) ? "1" : "0");//$NON-NLS-2$//$NON-NLS-1$
                res.append(separator);
            }
            writer.write(res.append(newline).toString());
        }
    }
}

/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.io;

import conexp.core.Context;
import conexp.core.ContextFactoryRegistry;
import conexp.core.FCAEngineRegistry;
import conexp.core.LocalizedMessageSupplier;
import conexp.core.ModifiableSet;
import util.DataFormatException;
import util.FormatUtil;
import util.collection.CollectionFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.List;
import java.util.StringTokenizer;


public class TabSeparatedContextReader implements ContextReader {
    protected LocalizedMessageSupplier getLocalizedMessageSupplier() {
        if (null == localizedMessageSupplier) {
            localizedMessageSupplier = new LocalizedMessageSupplier() {
                public String getMessage(String key) {
                    if ("ConImpLoader.ErrorInFileFormMsg".equals(key)) {
                        return "Error in data format on line {0}";
                    }
                    return "";
                }
            };
        }
        return localizedMessageSupplier;
    }

    public void setLocalizedMessageSupplier(LocalizedMessageSupplier localizedMessageSuplier) {
        this.localizedMessageSupplier = localizedMessageSuplier;
    }

    LocalizedMessageSupplier localizedMessageSupplier;


    private LineNumberReader reader;

    public Context parseContext(Reader r) throws IOException, DataFormatException {
        reader = new LineNumberReader(new BufferedReader(r));
        String SEPARATOR = "\t";

        String str = safeGetLine();

        StringTokenizer stringTokenizer = new StringTokenizer(str, SEPARATOR);
        List names = CollectionFactory.createDefaultList();
        while (stringTokenizer.hasMoreTokens()) {
            names.add(stringTokenizer.nextToken());
        }
        int numAttr = names.size();

        safeGetLine();//skipping

        //name
        Context cxt = FCAEngineRegistry.makeContext(0, numAttr);
        for (int i = 0; i < names.size(); i++) {
            cxt.getAttribute(i).setName((String) names.get(i));
        }
        ModifiableSet intent = ContextFactoryRegistry.createSet(numAttr);
        for (str = getNextLine(); str != null; str = getNextLine()) {
            intent.clearSet();
            StringTokenizer tokeniser = new StringTokenizer(str, SEPARATOR);
            int attrNo = 0;
            while (tokeniser.hasMoreTokens()) {
                String value = tokeniser.nextToken().trim();
                if (attrNo >= numAttr) {
                    throw makeFileFormatException();
                }
                if ("1".equals(value)) {
                    intent.put(attrNo);
                } else if ("0".equals(value)) {
                    //its ok
                } else {
                    throw makeFileFormatException();
                }
                attrNo++;
            }
            cxt.addObjectWithNameAndIntent(cxt.makeUniqueObjectName(), intent);
        }
        return cxt;

    }

    private String safeGetLine() throws IOException, DataFormatException {
        String str = getNextLine();
        if (null == str) {
            throw makeFileFormatException();
        }
        return str;
    }

    private String getNextLine() throws IOException {
        return reader.readLine();
    }

    private DataFormatException makeFileFormatException() {
        final String message = getLocalizedMessageSupplier().getMessage("ConImpLoader.ErrorInFileFormMsg");
        return new DataFormatException(FormatUtil.format(message, reader.getLineNumber()));
    }


}

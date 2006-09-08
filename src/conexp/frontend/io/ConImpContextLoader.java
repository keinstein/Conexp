/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.io;

import conexp.core.Context;
import conexp.core.FCAEngineRegistry;
import conexp.core.LocalizedMessageSupplier;
import util.DataFormatException;
import util.FormatUtil;
import util.StringUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;


public class ConImpContextLoader implements ContextReader {
    private LineNumberReader reader;

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

    private LocalizedMessageSupplier localizedMessageSupplier;

    public Context parseContext(Reader r) throws IOException, DataFormatException {
        reader = new LineNumberReader(new BufferedReader(r));

        String str = getNextLine();

        if (!"B".equals(str)) {
            throw makeFileFormatException();
        }
        reader.readLine(); //skipping line with a name
        //name
        try {
            str = getNextLine();
            int numObj = Integer.parseInt(str);

            str = getNextLine();
            int numAttr = Integer.parseInt(str);

            Context tmp = FCAEngineRegistry.makeContext(numObj, numAttr);

            str = getNextLine(); //skipping empty line between names and data
            if (!StringUtil.isEmpty(str)) {
                pushBack(str);
            }

            for (int i = 0; i < numObj; i++) {
                tmp.getObject(i).setName(getNextLine());
            }
            for (int i = 0; i < numAttr; i++) {
                tmp.getAttribute(i).setName(getNextLine());
            }
            for (int i = 0; i < numObj; i++) {
                str = getNextLine();
                for (int j = 0; j < numAttr; j++) {
                    char c = str.charAt(j);
                    switch (c) {
                        case 'x':
                        case 'X':
                            tmp.setRelationAt(i, j, true);
                            break;
                        case '.':
                        case ' ':
                            tmp.setRelationAt(i, j, false);
                            break;
                        default:
                            throw makeFileFormatException(); //$NON-NLS-1$
                    }
                }
            }
            return tmp;
        } catch (NumberFormatException e) {
            throw makeFileFormatException(); //$NON-NLS-1$
        }

    }

    private String storedLine;

    private void pushBack(String str) {
        storedLine = str;
    }

    private String getNextLine() throws IOException {
        if (storedLine != null) {
            String ret = storedLine;
            storedLine = null;
            return ret;
        }
        return reader.readLine();
    }

    private DataFormatException makeFileFormatException() {
        final String message = getLocalizedMessageSupplier().getMessage("ConImpLoader.ErrorInFileFormMsg");
        return new DataFormatException(FormatUtil.format(message, reader.getLineNumber()));
    }


}

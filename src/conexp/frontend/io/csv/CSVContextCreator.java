/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.frontend.io.csv;
import conexp.core.Context;
import conexp.core.ContextEntity;
import conexp.core.LocalizedMessageSupplier;
import conexp.frontend.io.ContextCreator;
import conexp.frontend.io.ContextReader;
import util.DataFormatException;
import util.FormatUtil;
import util.StringUtil;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.StringTokenizer;
public class CSVContextCreator implements ContextCreator, ContextReader {
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
    
    protected String crossToken = "1";
    
    protected String separator;
    protected boolean debug = false;
    public static final char COMMENT_CHARACTER = '%';
    public CSVContextCreator() {
        this(";");
    }
    public CSVContextCreator(String separator) {
        this.separator = separator;
    }
    private LineNumberReader reader;
    public Context parseContext(Reader inputReader) throws IOException, DataFormatException {
        Context context = new Context(0, 0);
        reader = new LineNumberReader(new BufferedReader(inputReader));
        boolean attributeInfoRead = false;
        while (!attributeInfoRead) {
            String input = safeGetLine();
            attributeInfoRead = readAttributes(input.trim(), context);
        }
        String input = getNextLine();
        int lastObjectIndex = -1;
        while (input != null) {
            if (addObjectInfos(input.trim(), lastObjectIndex, context)) {
                lastObjectIndex++;
            }
            input = getNextLine();
        }
        return context;
    }
    public Context createContext(Object obj) {
        try {
            return parseContext((Reader) obj);
        } catch (IOException e) {
            e.printStackTrace();  

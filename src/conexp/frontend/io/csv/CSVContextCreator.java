package conexp.frontend.io.csv;import java.io.BufferedReader;import java.io.IOException;import java.io.Reader;import java.util.Hashtable;import java.util.StringTokenizer;import conexp.core.Context;import conexp.core.ContextEntity;import conexp.frontend.io.ContextCreator;import util.StringUtil;/** * * CVSContextCreator creates a Context or loads a Document from a ContextCreator */public class CSVContextCreator implements ContextCreator {	/** the marker for crosses in the context*/    protected String ONE_MARK = "1";	/** the separator for crosses in the context*/    protected String separator = ";";    protected boolean debug = false;    protected Hashtable attributes = null;    public static final char COMMENT_CHARACTER = '%';//	TODO possibility to add Object to Context from Reader by setting this to null    public CSVContextCreator() {        attributes = new Hashtable();    }    public CSVContextCreator(String separator) {        this();        this.separator = separator;    }    public Context createContext(Reader reader) {        Context context = new Context(0, 0);        BufferedReader buff = new BufferedReader(reader);        String input;        int lastobjindex = -1;        try {            input = buff.readLine();            boolean thereIsNoNewAttributes = false;            while (!thereIsNoNewAttributes) {                input.trim();                setAttributes(input, context);                thereIsNoNewAttributes = true;                input = buff.readLine();            }            while (input != null) {                input.trim();                if (addObjectInfos(input, lastobjindex, context)) lastobjindex++;                input = buff.readLine();            }        } catch (IOException e) {            System.out.println(                    "Exception occured while reading file" + e);        }        attributes = null;        return context;    }    public Context createContext(Object obj) {        return createContext((Reader) obj);    }    protected void setAttributes(String input, Context context) {        if (shouldSkipLine(input)){            return;        }        StringTokenizer tokenizer = new StringTokenizer(input, separator);        // right now do nothng with the first one        while (tokenizer.hasMoreTokens()) {            String tok = tokenizer.nextToken();            context.addAttribute(ContextEntity.createContextAttribute(tok));        }    }    private boolean shouldSkipLine(String input) {        if(input.charAt(0) == COMMENT_CHARACTER){            return true;        }        return "".equals(StringUtil.safeTrim(input));    }    protected boolean addObjectInfos(String input, int lastobjindex, Context context) {        if (shouldSkipLine(input)){            return false;        }        StringTokenizer tokenizer = new StringTokenizer(input, separator);        String tok = tokenizer.nextToken();        context.addObject(ContextEntity.createContextObject(tok));        lastobjindex++;        int attrindex = 0;        while (tokenizer.hasMoreTokens()) {            tok = tokenizer.nextToken();            tok.trim();            if (tok.equals(ONE_MARK)){                context.setRelationAt(lastobjindex, attrindex, true);            }            attrindex++;        }        return true;    }    /**     * @return the string Value which sets the cross     */    public String getONE_MARK() {        return ONE_MARK;    }    /**     * @return the value of the separator     */    public String getSeparator() {        return separator;    }    public void setONE_MARK(String newValue) {        ONE_MARK = newValue;    }    /**     * set the value of the field separator     * @param newValue     */    public void setSeparator(String newValue) {        separator = newValue;    }}
/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.frontend.io.objattrlist;


import conexp.core.Context;
import conexp.core.ContextEntity;
import conexp.frontend.ContextDocument;
import conexp.frontend.DataFormatErrorHandler;
import conexp.frontend.DocumentLoader;
import conexp.frontend.io.ConExpXMLWriter;
import conexp.frontend.io.ContextCreator;
import conexp.frontend.io.ContextReader;
import conexp.frontend.io.DefaultDataFormatErrorHandler;
import conexp.frontend.io.UrlUtil;
import util.DataFormatException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;



public class ObjectListContextCreator implements ContextCreator, DocumentLoader, ContextReader {
    boolean debug = false;
    Map attributes = null;
    Map objects = null;

    public String COMMENTS = "%";


    public ObjectListContextCreator() {
        attributes = new HashMap();
        objects = new HashMap();
    }

    public Context createContextFromReader(Reader reader, DataFormatErrorHandler errorhandler) {
        try {
            Exception e;
            try {
                return parseContext(reader);
            } catch (IOException e1) {
                e = e1;
            } catch (DataFormatException e1) {
                e = e1;
            }
            //actually, it's quite a misuse. Julian, do you really need this?
            errorhandler.handleCriticalError(new DataFormatException(e));
        } catch (DataFormatException dfe) {
            System.out.println(dfe);
        }

        return new Context(0, 0);

    }

    public Context parseContext(Reader reader) throws IOException, DataFormatException {
        Context context = new Context(0, 0);
        attributes.clear();
        objects.clear();

        BufferedReader bufferedReader = new BufferedReader(reader);

        String OBJECT_SEPARATOR = ":";


        String input;

        while ((input = bufferedReader.readLine()) != null) {

            input = input.trim();
            if ("".equals(input) || input.startsWith(COMMENTS)) {
                continue;
            }
            StringTokenizer tokenizer = new StringTokenizer(input, OBJECT_SEPARATOR, false);


            if (!tokenizer.hasMoreTokens()) {
                continue; // there is no object on this line .... so nothing interesting ---> continue
            }
            int objectIndex = addObject(tokenizer.nextToken(), context);

            if (tokenizer.hasMoreTokens()) {
                String nextToken = tokenizer.nextToken();

                nextToken = nextToken.trim();
                String ATTRIBUTE_DELIMETER = ";";
                tokenizer = new StringTokenizer(nextToken, ATTRIBUTE_DELIMETER);

                while (tokenizer.hasMoreTokens()) {
                    int attrnumber = addAttribute(tokenizer.nextToken(ATTRIBUTE_DELIMETER), context);
                    context.setRelationAt(objectIndex, attrnumber, true);
                }
            }
        }
        return context;
    }


    int attrcount = -1;
    int objcount = -1;


    public int addObject(String obj, Context context) {
        if (objects.containsKey(obj)) {
            return ((Integer) objects.get(obj)).intValue();
        } else {
            context.addObject(ContextEntity.createContextObject(obj));
            objcount++;
            objects.put(obj, new Integer(objcount));
            return objcount;
        }

    }


    public int addAttribute(String obj, Context context) {

        if (attributes.containsKey(obj)) {
            return ((Integer) attributes.get(obj)).intValue();
        } else {
            ContextEntity attribute = ContextEntity.createContextAttribute(obj);
            context.addAttribute(attribute);
            attrcount++;
            attributes.put(obj, new Integer(attrcount));
            return attrcount;
        }
    }


    public void createContext(String inputnameOrURL, String outputnameOrURL) {
        Reader reader = UrlUtil.getReaderForFileOrURL(debug, inputnameOrURL);
        Context context = createContext(reader);
        ContextDocument contdoc = new ContextDocument(context);
        try {

            (new ConExpXMLWriter()).storeDocument(contdoc, new FileWriter(outputnameOrURL));

        } catch (IOException e) {

            System.out.print("An Exception Occured while trying to Store Document");

        }

    }

    public Context createContext(Object obj) {

        try {
            if (obj instanceof Reader) {
                return createContextFromReader((Reader) obj, DefaultDataFormatErrorHandler.getInstance());
            }
            return createContextFromReader(new FileReader((String) obj), DefaultDataFormatErrorHandler.getInstance());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return null;

    }


    public static void main(String[] args) {
        ObjectListContextCreator cc = new ObjectListContextCreator();
        cc.createContext(args[0], args[1]);
    }


    public ContextDocument loadDocument(Reader reader) {
        return new ContextDocument(createContext(reader));
    }


    public ContextDocument loadDocument(Reader reader, DataFormatErrorHandler errorhandler) {
        return new ContextDocument(createContext(reader));

    }

}

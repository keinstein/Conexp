package conexp.frontend.io.objattrlist;


import java.io.BufferedReader;

import java.io.FileNotFoundException;

import java.io.FileReader;

import java.io.FileWriter;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import java.io.Reader;

import java.net.MalformedURLException;

import java.net.URL;

import java.util.Hashtable;

import java.util.StringTokenizer;

import util.DataFormatException;


import conexp.core.Context;

import conexp.core.ContextEntity;

import conexp.frontend.ContextDocument;

import conexp.frontend.DataFormatErrorHandler;

import conexp.frontend.DocumentLoader;

import conexp.frontend.io.*;


public class ObjectListContextCreator implements ContextCreator, DocumentLoader, ContextReader {
    boolean debug = false;
    Hashtable attributes = null;
    Hashtable objects = null;

    public String COMMENTS = "%";


    public ObjectListContextCreator() {
        attributes = new Hashtable();
        objects = new Hashtable();
    }

    //todo: Julian, have a look, whether this really was intended. Sergey
    public ObjectListContextCreator(String COMMENTS) {
        this();
    }


    public Context createContextfromReader(Reader reader, DataFormatErrorHandler errorhandler) {
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

        StringTokenizer tokenizer = null;

        String input;

        while ((input = bufferedReader.readLine()) != null) {

            input = input.trim();
            if ((input.equals("")) || (input.startsWith(COMMENTS))) {
                continue;
            }
            System.out.println("essai" + input + "öööö");
            input = input.substring(0, input.length());//TODO check whether there is no mistake if there is ;

            tokenizer = new StringTokenizer(input, OBJECT_SEPARATOR, false);


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

    public int addCompleteAttribute(Context context, int i) {

        int index = -1;

        ContextEntity attribute;

        if (attributes.contains(new Integer(i))) {

            attribute = (ContextEntity) attributes.get(new Integer(i));

        } else {

            attribute = context.getAttribute(i);

            attribute = ContextEntity.createContextAttribute(attribute.getName());

            context.addAttribute(attribute);

            index = context.getAttributeCount() - 1;

            attributes.put(new Integer(i), attribute);

        }

        return index;

    }


    public Context createContext(Object obj) {

        try {

            if (obj instanceof Reader) {
                return createContextfromReader((Reader) obj, DefaultDataFormatErrorHandler.getInstance());
            }
            return createContext(new FileReader((String) obj));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return null;

    }


    public static void main(String[] args) {
        ObjectListContextCreator cc = null;

//        if (args.length == 3){
//            cc = new ObjectListContextCreator(args[2]);
//        } else {
        cc = new ObjectListContextCreator();
//        }
        cc.createContext(args[0], args[1]);
    }


    public ContextDocument loadDocument(Reader reader) {
        return new ContextDocument(createContext(reader));
    }


    public ContextDocument loadDocument(Reader reader, DataFormatErrorHandler errorhandler) {
        return new ContextDocument(createContext(reader));

    }

}

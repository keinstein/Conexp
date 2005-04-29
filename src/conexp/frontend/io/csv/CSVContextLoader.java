/*
 * Created on May 22, 2003
 *
 * This software package is under copyright licence conditions
 * Please read the LICENCE File at the root of this Software package
 */
package conexp.frontend.io.csv;

import conexp.core.Context;
import conexp.frontend.ContextDocument;
import conexp.frontend.DataFormatErrorHandler;
import conexp.frontend.DocumentLoader;
import conexp.frontend.io.ConExpXMLWriter;
import util.DataFormatException;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Julien Tane
 */
public class CSVContextLoader implements DocumentLoader {

    private boolean debug = true;

    private String separator = ";";

    /**
     *
     */
    public CSVContextLoader() {
        super();
    }

    /**
     *
     */
    public CSVContextLoader(String sep) {
        super();
    }

    /* (non-Javadoc)
     * @see conexp.frontend.DocumentLoader#loadDocument(java.io.Reader, conexp.frontend.DataFormatErrorHandler)
     */
    public ContextDocument loadDocument(Reader reader, DataFormatErrorHandler errorHandler)
            throws IOException, DataFormatException {
        CSVContextCreator cc = new CSVContextCreator(getSeparator());
        Context context = cc.parseContext(reader);
        ContextDocument contextDocument = new ContextDocument(context);
        return contextDocument;
    }

    /**
     * returns the separator of this CSV reader
     * default value is ";"
     *
     * @return
     */
    public String getSeparator() {
        return separator;
    }

    /**
     * sets the separator of this CSV reader
     *
     * @param string
     */
    public void setSeparator(String string) {
        separator = string;
    }


    public static void main(String[] args) {
        CSVContextLoader csvcl;
        if (args.length == 3) {
            csvcl = new CSVContextLoader(args[2]);
        } else {
            csvcl = new CSVContextLoader();
        }
        try {

            FileReader fr = new FileReader(args[0]);

            ContextDocument contextDocument = csvcl.loadDocument(fr, new DataFormatErrorHandler() {
                /* (non-Javadoc)
                     * @see conexp.frontend.DataFormatErrorHandler#handleCriticalError(util.DataFormatException)
                     */
                public void handleCriticalError(DataFormatException ex)
                        throws DataFormatException {
                    // TODO Auto-generated method stub
                }

                /* (non-Javadoc)
                 * @see conexp.frontend.DataFormatErrorHandler#handleUncriticalError(util.DataFormatException)
                 */
                public void handleUncriticalError(DataFormatException ex)
                        throws DataFormatException {
                    // TODO Auto-generated method stub

                }
            });

            (new ConExpXMLWriter()).storeDocument(contextDocument,
                    new FileWriter(args[2]));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (DataFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }


    public Reader getReaderForFileOrURL(String inputnameOrURL) {
        Reader reader = null;
        try {
            URL url = new URL(inputnameOrURL);
            InputStream in = url.openStream();
            reader = new InputStreamReader(in);
            return reader;
        } catch (MalformedURLException e) {
            if (debug) System.out.println(inputnameOrURL + " is not an URL");
        } catch (IOException e) {
            System.out.println("an exception Occured while trying to read " + inputnameOrURL);
        }

        try {
            reader = new FileReader(inputnameOrURL);
        } catch (FileNotFoundException e) {
            if (debug) System.out.println("File " + e + " couldn't be opened");
        }

        return reader;
    }
}

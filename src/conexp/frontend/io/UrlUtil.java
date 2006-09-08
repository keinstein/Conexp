/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.frontend.io;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;



public class UrlUtil {
    private UrlUtil() {
    }

    //todo:sye - review and fix code
    public static Reader getReaderForFileOrURL(boolean debug1, String inputNameOrURL) {
        Reader reader = null;
        try {
            URL url = new URL(inputNameOrURL);
            InputStream in = url.openStream();
            reader = new InputStreamReader(in);
            return reader;

        } catch (MalformedURLException e) {
            if (debug1) {
                System.out.println(inputNameOrURL + " is not an URL");
            }
        } catch (IOException e) {
            System.out.println("an exception Occured while trying to read " + inputNameOrURL);
        }

        try {
            reader = new FileReader(inputNameOrURL);
        } catch (FileNotFoundException e) {
            if (debug1) {
                System.out.println("File " + e + " couldn't be opened");
            }
        }
        return reader;

    }
}

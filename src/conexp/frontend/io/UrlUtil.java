package conexp.frontend.io;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 * Date: 17/7/2003
 * Time: 11:19:36
 */

public class UrlUtil {
    public static Reader getReaderForFileOrURL(boolean debug1, String inputNameOrURL) {
        Reader reader = null;
        URL url = null;
        try {
            url = new URL(inputNameOrURL);
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

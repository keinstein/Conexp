/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/

package conexp.frontend;

import conexp.util.exceptions.ConfigFatalError;

import javax.swing.*;
import java.net.URL;
import java.util.MissingResourceException;
import java.util.ResourceBundle;


public class ResourceLoader {
    private static ResourceLoader loader;

    /**
     * ResourceLoader constructor comment.
     */
    public ResourceLoader() {
        super();
    }

    //----------------------------------------------
    static public ImageIcon getIcon(String path) {
        if (null == path) {
            return null;
        } // end of if ()
        URL url = ResourceLoader.class.getResource(path);
        MyImageIcon icon = null;
        if (null != url) {
            icon = new MyImageIcon(url);
        }
        return icon;
    }


    /**
     * Insert the method's description here.
     * Creation date: (14.06.01 23:42:24)
     * @return java.util.ResourceBundle
     * @param name java.lang.String
     */
    public static ResourceBundle getResourceBundle(String name) {
        try {
            return ResourceBundle.getBundle(name);
        } catch (MissingResourceException ex) {
            throw new ConfigFatalError("Resource" + name + " not found");
        }
    }


    /**
     * Insert the method's description here.
     * Creation date: (15.06.01 23:26:18)
     * @return conexp.frontend.ResourceLoader
     */
    private static synchronized ResourceLoader getResourceLoader() {
        if (null == loader) {
            loader = new ResourceLoader();
        }
        return loader;
    }


    /**
     * Insert the method's description here.
     * Creation date: (15.06.01 23:23:53)
     * @return java.net.URL
     * @param path java.lang.String
     */
    public static java.net.URL getResourceUrl(String path) {
        try {
            return getResourceLoader().getClass().getResource(path);
        } catch (Exception ex) {
            throw new ConfigFatalError("can't read resource " + path);
        }
    }
}

/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend;

import conexp.util.exceptions.ConfigFatalError;
import util.ResourceHelper;

import javax.swing.ImageIcon;
import java.util.MissingResourceException;
import java.util.ResourceBundle;


public class ResourceLoader {
    private static ResourceLoader loader;

    /**
     * ResourceLoader constructor comment.
     */
    private ResourceLoader() {
        super();
    }

    //----------------------------------------------
    static public ImageIcon getIcon(String path) {
        return ResourceHelper.getIconFromResource(path, ResourceLoader.class);
    }


    /**
     * Insert the method's description here.
     * Creation date: (14.06.01 23:42:24)
     *
     * @param name java.lang.String
     * @return java.util.ResourceBundle
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
     *
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
     *
     * @param path java.lang.String
     * @return java.net.URL
     */
    public static java.net.URL getResourceUrl(String path) {
        try {
            return getResourceLoader().getClass().getResource(path);
        } catch (Exception ex) {
            throw new ConfigFatalError("can't read resource " + path);
        }
    }
}

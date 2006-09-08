/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.util;

import java.util.ArrayList;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.StringTokenizer;


public class ResourceManager implements IResourceManager {


    private ResourceBundle res;
    public static final String actionSuffix = "Action";
    private static final String imageSuffix = "Image";
    private static final String selectedImageSuffix = "SelectedImage";
    private static final String tooltipSuffix = "Tooltip";
    private static final String labelSuffix = "Label";
    private static final String shortcutSuffix = "Shortcut";

    //----------------------------------------------
    private String getResourceString(String nm) {
        String str = null;
        if (null != res) {
            try {
                str = res.getString(nm);
            } catch (MissingResourceException mre) {
            }
        }
        return str;
    }
    //----------------------------------------------

    /**
     * Take the given string and chop it up into a series
     * of strings on whitespace boundries.  This is useful
     * for trying to get an array of strings out of the
     * resource file.
     */
    private static String[] tokenize(String input) {
        ArrayList v = new ArrayList();
        StringTokenizer t = new StringTokenizer(input);
        String cmd[];

        while (t.hasMoreTokens()) {
            v.add(t.nextToken());
        }
        cmd = new String[v.size()];
        for (int i = 0; i < cmd.length; i++) {
            cmd[i] = (String) v.get(i);
        }

        return cmd;
    }


    public ResourceManager(ResourceBundle res) {
        super();
        this.res = res;
    }

    public String getCommandImage(String command) {
        return getResourceString(command + imageSuffix);
    }

    public String getSelectedImage(String command) {
        return getResourceString(command + selectedImageSuffix);
    }

    public String getCommandLabel(String key) {
        return getResourceString(key + labelSuffix);
    }

    public String getCommandShortcut(String command) {
        return getResourceString(command + shortcutSuffix);
    }

    public String getCommandTooltip(String command) {
        return getResourceString(command + tooltipSuffix);
    }

    public String[] getResourceDescription(String resName) {
        return tokenize(getResourceString(resName));
    }
}

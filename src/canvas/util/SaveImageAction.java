/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/

package canvas.util;

import canvas.IScreenImageProvider;
import util.StringUtil;
import util.gui.fileselector.ExtensionFileFilter;
import util.gui.fileselector.FileSelectorService;
import util.gui.fileselector.GenericFileFilter;
import util.imagecodecs.ImageCodec;
import util.imagecodecs.ImageCodecsFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;


public class SaveImageAction extends AbstractAction {
    private IScreenImageProvider screenImageProvider;
    private String title;
    protected Frame parentFrame = null;

    public Frame getParentFrame() {
        return parentFrame;
    }

    public void setParentFrame(Frame parentFrame) {
        this.parentFrame = parentFrame;
    }


    public SaveImageAction(String actionName, IScreenImageProvider screenImageProvider, String title) {
        super(actionName);
        this.screenImageProvider = screenImageProvider;
        this.title = title;
    }

    public SaveImageAction(IScreenImageProvider screenImageProvider, String title) {
        this("saveImage", screenImageProvider, title);
    }

    GenericFileFilter[] imageFilters;

    ImageCodecsFactory getImageCodecsFactory() {
        return util.ServiceRegistry.imageCodecsFactory();
    }

    GenericFileFilter[] getImageFilters() {
        if (null == imageFilters) {
            String[][] descriptions = getImageCodecsFactory().getDescriptions();
            imageFilters = new GenericFileFilter[descriptions.length];
            for (int i = descriptions.length; --i >= 0;) {
                imageFilters[i] = new ExtensionFileFilter(descriptions[i][1], descriptions[i][0]);
            }
        }
        return imageFilters;
    }

    public void actionPerformed(ActionEvent e) {
        FileSelectorService fileSelector = util.ServiceRegistry.fileSelectorService();
        if (fileSelector
                .performSaveService(
                        getParentFrame(), //parentFrame
                        title,
                        null, //start dir
                        null,
                        getImageFilters()
                )) {
            try {
                String path = fileSelector.getSelectedPath();
                BufferedOutputStream out = new BufferedOutputStream(
                        new FileOutputStream(
                                path));
                try {
                    ImageCodec codec = getImageCodecsFactory().makeCodec(StringUtil.getExtension(path));
                    codec.saveToStream(screenImageProvider.getScreenImage(), out);
                } finally {
                    out.flush();
                    out.close();
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }
}

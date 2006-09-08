/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package canvas.util;

import util.IExporter;
import util.ServiceRegistry;
import util.collection.CollectionFactory;
import util.errorhandling.IErrorHandler;
import util.gui.fileselector.ExtensionFileFilter;
import util.gui.fileselector.FileSelectorService;
import util.gui.fileselector.GenericFileFilter;

import javax.swing.AbstractAction;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.io.IOException;


public abstract class SaveImageActionBase extends AbstractAction {
    private String title;
    private Frame parentFrame = null;
    private GenericFileFilter[] formatFilters;
    protected IExporter[] exporters;
    private IErrorHandler errorHandler;

    protected SaveImageActionBase(String name, String title, IErrorHandler errorHandler) {
        super(name);
        this.title = title;
        this.errorHandler = errorHandler;
    }


    private Frame getParentFrame() {
        return parentFrame;
    }

    public void setParentFrame(Frame parentFrame) {
        this.parentFrame = parentFrame;
    }

    private GenericFileFilter[] getFormatFilters() {
        if (null == formatFilters) {
            java.util.List filters = CollectionFactory.createDefaultList();
            for (int exporterIndex = 0; exporterIndex < exporters.length; exporterIndex++) {
                String[][] descriptions = exporters[exporterIndex].getDescriptions();
                for (int j = descriptions.length; --j >= 0;) {
                    filters.add(new ExtensionFileFilter(descriptions[j][1], descriptions[j][0]));
                }
            }
            formatFilters = (GenericFileFilter[]) filters.toArray(new GenericFileFilter[filters.size()]);
        }
        return formatFilters;
    }

    public void actionPerformed(ActionEvent e) {
        FileSelectorService fileSelector = ServiceRegistry.fileSelectorService();
        try {
            if (fileSelector
                    .performSaveService(
                            getParentFrame(), //parentFrame
                            title,
                            null, //start dir
                            null,
                            getFormatFilters()
                    )) {
                String selectedPath = fileSelector.getSelectedPath();
                for (int i = 0; i < exporters.length; i++) {
                    IExporter exporter = exporters[i];
                    if (exporter.accepts(selectedPath)) {
                        exporter.performExportService(selectedPath);
                        break;
                    }
                }
            }
        } catch (IOException e1) {
            errorHandler.notify(getParentFrame(), e1);
        }

    }
}

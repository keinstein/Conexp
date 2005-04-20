package canvas.util;

import util.gui.fileselector.GenericFileFilter;
import util.gui.fileselector.ExtensionFileFilter;
import util.gui.fileselector.FileSelectorService;
import util.collection.CollectionFactory;
import util.IExporter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.*;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: sergey
 * Date: 17/4/2005
 * Time: 14:33:10
 * To change this template use File | Settings | File Templates.
 */
public abstract class SaveImageActionBase extends AbstractAction {
    protected String title;
    protected Frame parentFrame = null;
    protected GenericFileFilter[] formatFilters;
    protected IExporter[] exporters;

    public SaveImageActionBase(String name, String title) {
        super(name);
        this.title = title;
    }

    public Frame getParentFrame() {
        return parentFrame;
    }

    public void setParentFrame(Frame parentFrame) {
        this.parentFrame = parentFrame;
    }

    protected GenericFileFilter[] getFormatFilters() {
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
        FileSelectorService fileSelector = util.ServiceRegistry.fileSelectorService();
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
            e1.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }

    }
}

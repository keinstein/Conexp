package conexp.frontend.latticeeditor;

import util.collection.CollectionFactory;
import util.gui.fileselector.ExtensionFileFilter;
import util.gui.fileselector.FileSelectorService;
import util.gui.fileselector.GenericFileFilter;

import javax.swing.*;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;

import canvas.util.RasterImageLatticeExporter;

/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 * Date: 30/12/2003
 * Time: 14:30:49
 */

public class ExportLatticeInfoAction extends AbstractAction {
    protected String title;
    protected Frame parentFrame = null;
    protected GenericFileFilter[] formatFilters;
    protected ILatticeExporter[] exporters;

    public ExportLatticeInfoAction(String name, String title, LatticePainterPanel latticePainterPanel) {
        super(name);
        this.title = title;
        exporters = new ILatticeExporter[]{
          new RasterImageLatticeExporter(latticePainterPanel),
          new ConscriptLatticeExporter(latticePainterPanel),
          new PlainTextLatticeExporter(latticePainterPanel)
        };
    }

    public Frame getParentFrame() {
        return parentFrame;
    }

    public void setParentFrame(Frame parentFrame) {
        this.parentFrame = parentFrame;
    }

    protected GenericFileFilter[] getFormatFilters() {
        if (null == formatFilters) {
            List filters = CollectionFactory.createDefaultList();
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
                    ILatticeExporter exporter = exporters[i];
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

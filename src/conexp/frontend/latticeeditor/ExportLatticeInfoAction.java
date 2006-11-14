/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.frontend.latticeeditor;

import canvas.util.RasterImageExporter;
import canvas.util.SaveImageActionBase;
import util.IExporter;
import util.errorhandling.IErrorHandler;



public class ExportLatticeInfoAction extends SaveImageActionBase {

    public ExportLatticeInfoAction(String name, String title, LatticePainterPanel latticePainterPanel, IErrorHandler errorHandler) {
        super(name, title, errorHandler);
        exporters = getExporters(latticePainterPanel);
    }

    private static IExporter[] getExporters(LatticePainterPanel latticePainterPanel) {
        return new IExporter[]{
                new RasterImageExporter(latticePainterPanel),
                new ConscriptLatticeExporter(latticePainterPanel),
                new PlainTextLatticeExporter(latticePainterPanel),
                new DotExporter(latticePainterPanel)
        };
    }


}

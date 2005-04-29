package conexp.frontend.latticeeditor;

import canvas.util.RasterImageExporter;
import canvas.util.SaveImageActionBase;
import util.IExporter;

/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 * Date: 30/12/2003
 * Time: 14:30:49
 */

public class ExportLatticeInfoAction extends SaveImageActionBase {

    public ExportLatticeInfoAction(String name, String title, LatticePainterPanel latticePainterPanel) {
        super(name, title);
        exporters = getExporters(latticePainterPanel);
    }

    private static IExporter[] getExporters(LatticePainterPanel latticePainterPanel) {
        return new IExporter[]{
                  new RasterImageExporter(latticePainterPanel),
                  new ConscriptLatticeExporter(latticePainterPanel),
                  new PlainTextLatticeExporter(latticePainterPanel)
                };
    }


}

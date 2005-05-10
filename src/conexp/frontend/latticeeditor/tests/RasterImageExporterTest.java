package conexp.frontend.latticeeditor.tests;

/**
 * User: sergey
 * Date: 6/5/2005
 * Time: 10:49:27
 */

import canvas.util.RasterImageExporter;
import conexp.core.tests.SetBuilder;
import conexp.core.layoutengines.SimpleLayoutEngine;
import conexp.frontend.latticeeditor.LatticePainterPanel;
import conexp.frontend.components.LatticeComponent;
import junit.framework.TestCase;

import java.io.File;

public class RasterImageExporterTest extends TestCase {
    public void testSavingToGif() throws Exception{

        LatticeComponent component =     new LatticeComponent(SetBuilder.makeContext(new int[][]{{0}}));
        component.setLayoutEngine(new SimpleLayoutEngine());

        LatticePainterPanel panel = new LatticePainterPanel(component);

        panel.setAntiAlias(true);
        panel.initialUpdate();
        component.calculateAndLayoutLattice();

        RasterImageExporter exporter = new RasterImageExporter(panel);
        String fileName="conexp/frontend/resources/tests/LatticeImage.gif";
        String path = new File(fileName).getAbsolutePath();

        exporter.performExportService(path);

    }

}
package conexp.frontend.latticeeditor.tests;

/**
 * User: sergey
 * Date: 6/5/2005
 * Time: 10:49:27
 */

import canvas.util.RasterImageExporter;
import conexp.frontend.components.LatticeComponent;
import conexp.frontend.components.tests.ComponentsObjectMother;
import conexp.frontend.latticeeditor.LatticePainterPanel;
import junit.framework.TestCase;

import java.io.File;

public class RasterImageExporterTest extends TestCase {
    public void testSavingToGif() throws Exception{

        LatticeComponent component =     ComponentsObjectMother.makeLatticeComponentWithSimpleLayoutEngine(new int[][]{{0}});
        LatticePainterPanel panel = LatticePainterPanel.createLatticePainterPanel(component);

        panel.setAntiAlias(true);
        panel.initialUpdate();
        component.calculateAndLayoutLattice();

        RasterImageExporter exporter = new RasterImageExporter(panel);
        String fileName="conexp/frontend/resources/tests/LatticeImage.gif";
        String path = new File(fileName).getAbsolutePath();

        exporter.performExportService(path);

    }

}
/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.frontend.io.tests;

import conexp.frontend.components.LatticeComponent;
import conexp.frontend.components.tests.ComponentsObjectMother;
import conexp.frontend.io.LatticeCSCExporter;
import conexp.frontend.latticeeditor.LatticeDrawing;
import conexp.frontend.latticeeditor.labelingstrategies.LabelingStrategiesKeys;
import junit.framework.TestCase;

import java.io.BufferedWriter;
import java.io.StringWriter;



public class LatticeCSCExporterTest extends TestCase {
    private LatticeComponent latticeComponent;

    public void testExportLatticeToCSC() {
        LatticeDrawing drawing = latticeComponent.getDrawing();
        LatticeCSCExporter exporter = new LatticeCSCExporter();
        exporter.setDrawing(drawing);
        StringWriter writer = new StringWriter();
        exporter.exportDiagram(new BufferedWriter(writer));
    }

    public void testCaseWhenThereAreLabels() {
        LatticeDrawing drawing = latticeComponent.getDrawing();
        drawing.setObjectLabelingStrategyKey(LabelingStrategiesKeys.ALL_OBJECTS_LABELING_STRATEGY);
        drawing.setAttributeLabelingStrategyKey(LabelingStrategiesKeys.ALL_ATTRIBS_LABELING_STRATEGY_KEY);
        LatticeCSCExporter exporter = new LatticeCSCExporter();
        exporter.setDrawing(drawing);
        StringWriter writer = new StringWriter();
        exporter.exportDiagram(new BufferedWriter(writer));
    }

    protected void setUp() {
        latticeComponent = ComponentsObjectMother.makeLatticeComponent(new int[][]{{0, 0, 1},
                {1, 0, 0},
                {0, 1, 1}});


        latticeComponent.calculateAndLayoutLattice();
    }

    public static void testFormat() {
        String res = LatticeCSCExporter.format("{0, number, 0.##}", new Object[]{new Double(3.14)});
        assertEquals("should contain dot", -1, res.indexOf(','));
/*
        Locale[] availableLocales = Locale.getAvailableLocales();
        for (int i = 0; i < availableLocales.length; i++) {
            Locale locale = availableLocales[i];
            String s =(new MessageFormat("{0, number, 0.##}", locale)).format(new Object[]{new Double(3.14)});
            System.out.println("locale "+locale);
            System.out.println(s);
        }
*/
    }

    public static void testEncodeName() {
        assertEquals("Black \\\\& Decker", LatticeCSCExporter.encodeName("Black & Decker"));
    }
}

/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.frontend.latticeeditor;

import conexp.frontend.io.LatticeCSCExporter;
import util.FileNameMangler;
import util.IExporter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class ConscriptLatticeExporter implements IExporter {
    LatticePainterPanel panel;
    public static final String CONSCRIPT_EXTENSION = "csc";

    public ConscriptLatticeExporter(LatticePainterPanel panel) {
        this.panel = panel;
    }

    public LatticeDrawing getDrawing() {
        return panel.getLatticeDrawing();
    }

    public String[][] getDescriptions() {
        return new String[][]{{CONSCRIPT_EXTENSION, "ConScript"}};
    }

    public boolean accepts(String path) {
        return FileNameMangler.getFileExtension(path).equals(CONSCRIPT_EXTENSION);
    }

    public void performExportService(String path) throws IOException {
        BufferedWriter out = new BufferedWriter(new FileWriter(path));
        try {
            LatticeCSCExporter exporter = new LatticeCSCExporter();
            exporter.setDrawing(getDrawing());
            exporter.exportDiagram(out);
        } finally {
            out.flush();
            out.close();
        }
    }
}

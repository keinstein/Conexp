package conexp.frontend.latticeeditor;

import util.FileNameMangler;

import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;

import conexp.frontend.io.LatticeCSCExporter;

/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 * Date: 30/12/2003
 * Time: 15:17:46
 */
public class ConscriptLatticeExporter implements ILatticeExporter {
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

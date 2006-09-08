/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.frontend.latticeeditor;

import conexp.core.Concept;
import conexp.core.ConceptsCollection;
import conexp.core.ContextEntity;
import conexp.core.Edge;
import conexp.core.Lattice;
import conexp.core.LatticeElement;
import conexp.frontend.latticeeditor.figures.AbstractConceptCorrespondingFigure;
import util.FileNameMangler;
import util.IExporter;

import java.awt.geom.Point2D;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Iterator;



public class PlainTextLatticeExporter implements IExporter {
    LatticePainterPanel panel;
    public static final String PLAIN_TEXT_EXTENSION = "txt";

    public PlainTextLatticeExporter(LatticePainterPanel panel) {
        this.panel = panel;
    }

    public LatticeDrawing getDrawing() {
        return panel.getLatticeDrawing();
    }

    public String[][] getDescriptions() {
        return new String[][]{{PLAIN_TEXT_EXTENSION, "Plain text file"}};
    }

    private void writeLattice(FileWriter writer, final ConceptSetDrawing conceptSetDrawing) {
        PrintWriter printWriter = new PrintWriter(writer, true);

        printNodesCoords(conceptSetDrawing, printWriter);
        printEdges(conceptSetDrawing, printWriter);
        printOwnObjectLabels(conceptSetDrawing, printWriter);
        printOwnAttributesLabels(conceptSetDrawing, printWriter);
        printWriter.println("EOF");
    }

    private static void printNodesCoords(ConceptSetDrawing conceptSetDrawing, PrintWriter writer) {
        final ConceptsCollection conceptSet = conceptSetDrawing.getConceptSet();
        for (Iterator iterator = conceptSet.elements(); iterator.hasNext();) {
            Concept concept = (Concept) iterator.next();
            final AbstractConceptCorrespondingFigure figure = conceptSetDrawing.getFigureForConcept(concept);
            final Point2D c = figure.getCenter();
            writer.println("Node: " + (concept.getIndex() + 1) + ", " + c.getX() + ", " + c.getY());
        }
    }

    private static void printOwnObjectLabels(final ConceptSetDrawing conceptSetDrawing, PrintWriter writer) {
        final ConceptsCollection conceptSet = conceptSetDrawing.getConceptSet();
        for (Iterator iterator = conceptSet.elements(); iterator.hasNext();) {
            Concept concept = (Concept) iterator.next();
            final Iterator ownObjectsIterator = concept.ownObjectsIterator();
            while (ownObjectsIterator.hasNext()) {
                ContextEntity obj = (ContextEntity) ownObjectsIterator.next();
                writer.println("Object: " + (concept.getIndex() + 1) + ", " + obj.getName());
                obj.getName();
            }
        }
    }

    private static void printOwnAttributesLabels(final ConceptSetDrawing conceptSetDrawing, PrintWriter writer) {
        final ConceptsCollection conceptSet = conceptSetDrawing.getConceptSet();
        for (Iterator iterator = conceptSet.elements(); iterator.hasNext();) {
            Concept concept = (Concept) iterator.next();
            final Iterator ownAttributesIterator = concept.ownAttribsIterator();
            while (ownAttributesIterator.hasNext()) {
                ContextEntity attr = (ContextEntity) ownAttributesIterator.next();
                writer.println("Attribute: " + (concept.getIndex() + 1) + ", " + attr.getName());
                attr.getName();
            }
        }
    }

    private void printEdges(ConceptSetDrawing conceptSetDrawing, PrintWriter writer) {
        final Lattice lattice = conceptSetDrawing.getLattice();
        java.util.Set edgesSet = collectEdges(lattice);
        for (Iterator iterator = edgesSet.iterator(); iterator.hasNext();) {
            Edge edge = (Edge) iterator.next();
            writer.println("Edge: " + (edge.getStart().getIndex() + 1) +
                    ", " + (edge.getEnd().getIndex() + 1));
        }
    }

    private java.util.Set collectEdges(final Lattice lattice) {
        final LatticeElement top = lattice.getTop();
        java.util.Set edgesSet = new HashSet();
        collectEdges(top, edgesSet);
        return edgesSet;
    }

    private void collectEdges(LatticeElement element, java.util.Set edgesSet) {
        final Iterator edgesIterator = element.predessorsEdges();
        while (edgesIterator.hasNext()) {
            Edge edge = (Edge) edgesIterator.next();
            edgesSet.add(edge);
            collectEdges(edge.getStart(), edgesSet);
        }
    }

    public boolean accepts(String path) {
        return FileNameMangler.getFileExtension(path).equals(PLAIN_TEXT_EXTENSION);
    }

    public void performExportService(String path) throws IOException {
        ConceptSetDrawing conceptSetDrawing = panel.getConceptSetDrawing();
        FileWriter writer = null;
        try {
            writer = new FileWriter(path, false);
            writeLattice(writer, conceptSetDrawing);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }

    }
}

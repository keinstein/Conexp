/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.frontend.io;

import canvas.figures.FigureWithCoords;
import conexp.core.BinaryRelation;
import conexp.core.Context;
import conexp.core.ContextEntity;
import conexp.core.Lattice;
import conexp.core.LatticeElement;
import conexp.core.LatticeElementCollection;
import conexp.frontend.latticeeditor.LatticeDrawing;
import conexp.frontend.latticeeditor.figures.AbstractConceptCorrespondingFigure;
import conexp.frontend.latticeeditor.labelingstrategies.LabelingStrategiesKeys;
import util.StringUtil;

import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.Iterator;
import java.util.Locale;



public class LatticeCSCExporter {
    public static final String HEADER = "LINE_DIAGRAM";
    private PrintWriter pw;
    int depthLevel = 0;
    public static final String TITLE_STRING = "TITLE";
    public static final String UNITLENGTH_STRING = "UNITLENGTH 1 mm";
    public static final String POINTS_STRING = "POINTS";
    public static final String LINES_STRING = "LINES";


    public void println(Object message) {
        for (int i = 0; i < depthLevel; i++) {
            pw.print("\t");
        }
        pw.println(message);
    }

    public void exportDiagram(BufferedWriter writer) {
        pw = new PrintWriter(writer);
        println(HEADER);
        incDepthLevel();
        println(getDiagramId() + " =");
        incDepthLevel();
        println(TITLE_STRING + ' ' + getDiagramTitle());
        println(UNITLENGTH_STRING);
        printOutPoints();
        printOutLines();
        printOutObjects();
        printOutAttributes();
        printOutConcepts();
        println(";");
        pw.flush();
    }

    private void printOutConcepts() {
        println("CONCEPTS");
    }

    public static String format(String pattern, Object[] objects) {
        return (new MessageFormat(pattern, Locale.US)).format(objects);
    }

    private void printOutAttributes() {
        printSection("ATTRIBUTES", new Block() {
            public void doBlock() {
                Lattice lat = getLattice();
                Context cxt = lat.getContext();
                if (!drawing.hasLabelsForAttributes()) {
                    return;
                }
                for (int attrIndex = 0; attrIndex < cxt.getAttributeCount(); attrIndex++) {
                    ContextEntity attribute = cxt.getAttribute(attrIndex);
                    String name = attribute.getName();
                    //attribute format - number, identifier, description, format
                    LatticeElement attrConcept = lat.findLatticeElementForAttr(attrIndex);
                    int id = attrConcept.getIndex();
                    double offsetX;
                    double offsetY;
                    if (drawing.getAttributeLabelingStrategyKey().equals(LabelingStrategiesKeys.ALL_ATTRIBS_LABELING_STRATEGY_KEY))
                    {
                        FigureWithCoords labelForAttributeFigure = drawing.getLabelForAttribute(attribute);
                        Rectangle boundingBox = new Rectangle();
                        labelForAttributeFigure.boundingBox(boundingBox);
                        Point2D labelLocation = new Point2D.Double(boundingBox.getX(), boundingBox.getY());
                        // Point2D labelLocation=labelForAttributeFigure.getCenter();
                        AbstractConceptCorrespondingFigure figureForConcept = drawing.getFigureForConcept(attrConcept);

                        Point2D conceptFigureCenter = figureForConcept.getCenter();
                        offsetX = translateX(labelLocation.getX()) - translateX(conceptFigureCenter.getX());
                        offsetY = translateY(labelLocation.getY()) - translateY(conceptFigureCenter.getY());
                        //DecimalFormat
                        String s = format("{0} M{1} \"{2}\" \",,,,({3,number,0.##},{4,number,0.##}),l\"", new Object[]{
                                new Integer(id),
                                new Integer(attrIndex),
                                name,
                                new Double(offsetX),
                                new Double(offsetY)
                        });
                        println(s);
                    }
                }
            }
        });

    }

    private double pixelsPerMilimeter = 72 / 25.24;

    private double translateY(double y) {
        //y should be reversed
        return (drawing.getUserBoundsRect().getMaxY() - y) / pixelsPerMilimeter;
    }

    private double translateX(double x) {
        return x / pixelsPerMilimeter;
    }

    private void printOutObjects() {
        printSection("OBJECTS", new Block() {
            public void doBlock() {
                Lattice lat = getLattice();
                Context cxt = lat.getContext();
                BinaryRelation relation = cxt.getRelation();
                if (!drawing.hasLabelsForObjects()) {
                    return;
                }
                for (int objIndex = 0; objIndex < cxt.getObjectCount(); objIndex++) {
                    ContextEntity object = cxt.getObject(objIndex);
                    String name = object.getName();
                    //object format - number, identifier, description, format
                    LatticeElement objectConcept = lat.findLatticeElementFromOne(relation.getSet(objIndex));
                    int id = objectConcept.getIndex();
                    double offsetX = 0;
                    double offsetY = 0;
                    if (drawing.getObjectLabelingStrategyKey().equals(LabelingStrategiesKeys.ALL_OBJECTS_LABELING_STRATEGY))
                    {
                        FigureWithCoords labelForObjectFigure = drawing.getLabelForObject(object);
                        Rectangle boundingBox = new Rectangle();
                        labelForObjectFigure.boundingBox(boundingBox);
                        Point2D labelLocation = new Point2D.Double(boundingBox.getX(), boundingBox.getY());
                        AbstractConceptCorrespondingFigure figureForConcept = drawing.getFigureForConcept(objectConcept);
                        Point2D conceptFigureCenter = figureForConcept.getCenter();
                        offsetX = translateX(labelLocation.getX()) - translateX(conceptFigureCenter.getX());
                        offsetY = translateY(labelLocation.getY()) - translateY(conceptFigureCenter.getY());
                    }
                    String s = format("{0} G{1} \"{2}\" \",,,,({3,number,0.##},{4,number,0.##}),l\"", new Object[]{
                            new Integer(id),
                            new Integer(objIndex),
                            encodeName(name),
                            new Double(offsetX),
                            new Double(offsetY)
                    });
                    println(s);
                }
            }
        });
    }

    public static String encodeName(String name) {
        name = StringUtil.replaceStringWithNewString(name, "&", "\\\\&");
        return name;
    }

    interface Block {
        void doBlock();
    }

    private void printOutLines() {
        printSection(LINES_STRING, new Block() {
            public void doBlock() {
                final Lattice lattice = getLattice();
                for (int i = 0; i < lattice.conceptsCount(); i++) {
                    LatticeElement current = lattice.elementAt(i);
                    final LatticeElementCollection children = current.getChildren();
                    for (Iterator iterator = children.iterator(); iterator.hasNext();) {
                        LatticeElement other = (LatticeElement) iterator.next();
                        String string = format("({0}, {1})", new Object[]{
                                new Integer(current.getIndex()),
                                new Integer(other.getIndex())
                        });
                        println(string);
                    }
                }
            }
        });
    }

    private void printSection(final String linesString, Block block) {
        println(linesString);
        incDepthLevel();
        block.doBlock();
        decDepthLevel();
    }

    private void printOutPoints() {
        printSection(POINTS_STRING, new Block() {
            public void doBlock() {
                Lattice lattice = getLattice();
                for (int i = 0; i < lattice.conceptsCount(); i++) {
                    final Point2D point = drawing.getFigureForConcept(lattice.elementAt(i)).getCenter();
                    String message = format("{0} {1, number, 0.#} {2, number, 0.#}", new Object[]{new Integer(i),
                            new Double(translateX(point.getX())),
                            new Double(translateY(point.getY()))});
                    println(message);
                }
            }
        });
    }

    private Lattice getLattice() {
        return drawing.getLattice();
    }

    private void decDepthLevel() {
        depthLevel--;
    }

    private static String getDiagramTitle() {
        return "\"\"";
    }

    private void incDepthLevel() {
        depthLevel++;
    }

    private static String getDiagramId() {
        return "LineDiagramID";
    }

    LatticeDrawing drawing;

    public void setDrawing(LatticeDrawing drawing) {
        this.drawing = drawing;
    }


}

package conexp.frontend.latticeeditor;

import conexp.core.ContextEntity;
import conexp.core.Lattice;
import conexp.core.LatticeElement;
import util.FileNameMangler;
import util.IExporter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

public class DotExporter implements IExporter {
    public static final String DOT_TEXT_EXTENSION = "dot";

    LatticePainterPanel myPanel;

    public DotExporter(LatticePainterPanel panel) {
        myPanel = panel;
    }

    public String[][] getDescriptions() {
        return new String[][]{
                {DOT_TEXT_EXTENSION, "Graphviz Dot file"}
        };
    }

    public boolean accepts(String path) {
        return FileNameMangler.getFileExtension(path).equals(DOT_TEXT_EXTENSION);
    }

    public void performExportService(String path) throws IOException {
        FileOutputStream output = null;
        try {
            output = new FileOutputStream(path);
            PrintStream file = new PrintStream(output);
            file.print(asStringDot(myPanel.getLattice()));
        } finally {
            if (output != null) {
                output.close();
            }
        }
    }

    public static final String asStringDot(Lattice lattice) {
        StringBuffer string = new StringBuffer();

        string.append("digraph FCA {\n");
        string.append("\tratio=fill;\n");
        string.append("\tsize=\"7.5,10\";\n");
        string.append("\n");

        Hashtable conceptToNodeNumber = new Hashtable();

        string.append(getNodeList(lattice.getTop(), conceptToNodeNumber));

        string.append("\n");

        string.append(getArcList(lattice.getTop(), conceptToNodeNumber,
                new LinkedHashSet()));

        string.append("}\n");

        return string.toString();
    }

    // ------------------------------------------------------------------------

    public static final String getNodeList(final LatticeElement latticeElement,
                                           final Hashtable conceptToNodeNumber) {
        StringBuffer string = new StringBuffer();

        int nodeNumber = conceptToNodeNumber.size() + 1;
        conceptToNodeNumber.put(latticeElement, new Integer(nodeNumber));

        string.append("\tnode_");
        string.append(nodeNumber);
        string.append(" [label=\"");
        string.append(asStringNodeDot(latticeElement));
        string.append("\"];\n");

        for (int i = 0; i < latticeElement.getChildren().getSize(); i++) {
            LatticeElement child = latticeElement.getChildren().get(i);

            if (conceptToNodeNumber.containsKey(child)) {
                continue;
            }

            string.append(getNodeList(child, conceptToNodeNumber));
        }

        if (nodeNumber == 1) {
            string.append("\n\t{\n");

            for (int i = 0; i < latticeElement.getChildren().getSize(); i++) {
                LatticeElement child = latticeElement.getChildren().get(i);

                string.append("\tnode_");
                string.append(conceptToNodeNumber.get(child));
                string.append(";\n");
            }

            string.append("\n\trank=\"same\";\n\t}\n");
        }

        return string.toString();
    }

    // ------------------------------------------------------------------------

    public static final String getArcList(
            final LatticeElement latticeElement,
            final Hashtable conceptToNodeNumber,
            final Set visitedConcepts) {
        if (visitedConcepts.contains(latticeElement)) {
            return "";
        }

        visitedConcepts.add(latticeElement);

        StringBuffer string = new StringBuffer();

        for (int i = 0; i < latticeElement.getChildren().getSize(); i++) {
            LatticeElement child = latticeElement.getChildren().get(i);

            string.append("\tnode_");
            string.append(conceptToNodeNumber.get(latticeElement));
            string.append(" -> node_");
            string.append(conceptToNodeNumber.get(child));
            string.append(";\n");

            string.append(getArcList(child, conceptToNodeNumber, visitedConcepts));
        }

        return string.toString();
    }

    // ------------------------------------------------------------------------


    public static final String asStringNodeDot(LatticeElement latticeElement) {
        StringBuffer string = new StringBuffer();
        writeContextEntities(latticeElement.ownAttribsIterator(), string);
        string.append("\\n");
        writeContextEntities(latticeElement.ownObjectsIterator(), string);
        return string.toString();
    }

    private static void writeContextEntities(Iterator entityIterator, StringBuffer string) {
        boolean first = true;
        for (Iterator iterator = entityIterator; iterator.hasNext();) {
            ContextEntity entity = (ContextEntity) iterator.next();
            if (first) {
                first = false;
            } else {
                string.append(",");
            }
            string.append(escapeString(entity.getName()));

        }
    }

    public static String escapeString(String name) {
        return name.replaceAll("\"", "\\\\\"");
    }

}

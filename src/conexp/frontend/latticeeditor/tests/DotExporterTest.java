package conexp.frontend.latticeeditor.tests;

import conexp.core.FCAEngineRegistry;
import conexp.core.Lattice;
import conexp.core.Context;
import conexp.core.tests.SetBuilder;
import conexp.frontend.latticeeditor.DotExporter;
import junit.framework.TestCase;

public class DotExporterTest extends TestCase {
    static final String FULL_LATTICE_DOT_GOLDMASTER = "digraph FCA {\n" +
           "\tratio=fill;\n" +
           "\tsize=\"7.5,10\";\n" +
           "\n" +
           "\tnode_1 [label=\"Attr 1\\n\"];\n" +
           "\tnode_2 [label=\"Attr 3\\nObj 1\"];\n" +
           "\tnode_3 [label=\"\\n\"];\n" +
           "\tnode_4 [label=\"Attr 2\\nObj 2\"];\n" +
           "\n" +
           "\t{\n" +
           "\tnode_2;\n" +
           "\tnode_4;\n" +
           "\n" +
           "\trank=\"same\";\n" +
           "\t}\n" +
           "\n" +
           "\tnode_1 -> node_2;\n" +
           "\tnode_2 -> node_3;\n" +
           "\tnode_1 -> node_4;\n" +
           "\tnode_4 -> node_3;\n" +
           "}\n";
    static final String PARTIAL_LATTICE_DOT_GOLDMASTER = "digraph FCA {\n" +
           "\tratio=fill;\n" +
           "\tsize=\"7.5,10\";\n" +
           "\n" +
           "\tnode_1 [label=\"Attr 1\\nObj 1\"];\n" +
           "\tnode_2 [label=\"Attr 2\\nObj 2\"];\n"+
           "\n" +
           "\t{\n" +
           "\tnode_2;\n" +
           "\n" +
           "\trank=\"same\";\n" +
           "\t}\n" +
           "\n" +
           "\tnode_1 -> node_2;\n" +
           "}\n";


    public void testConversionWithStaticBuilder(){
        Lattice latt = FCAEngineRegistry.buildLattice(makeTestContext());
        String dotString = DotExporter.asStringDot(latt);
        assertEquals(FULL_LATTICE_DOT_GOLDMASTER, dotString);
    }

    public void testDotGenerationWithPartialLattice(){
        Lattice latt = FCAEngineRegistry.buildPartialLattice(makeTestContext(),
                SetBuilder.makeSet(new int[]{1, 1, 0}), SetBuilder.makeSet(new int[]{1, 1}));
        String dotString = DotExporter.asStringDot(latt);
        assertEquals(PARTIAL_LATTICE_DOT_GOLDMASTER, dotString);

    }

    private static Context makeTestContext() {
        return SetBuilder.makeContext(new int[][]{{1, 0, 1},
                {1, 1, 0}});
    }


    public void testEscapeString() throws Exception {
        assertEquals("abc", DotExporter.escapeString("abc"));
        assertEquals("a\\\"b", DotExporter.escapeString("a\"b"));
    }
}
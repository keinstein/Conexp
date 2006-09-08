/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.core;

import java.util.Iterator;



public class LatticeAlgorithms {
    private LatticeAlgorithms() {
    }

    public static LatticeElement findBottomUpMinimalElementThatIncludesSet(LatticeElement startNode, Set intent) {
        if (intent.equals(startNode.getAttribs())) {
            return startNode;
        }
        LatticeElement current = startNode;
        while (true) {
            outer:
            {
                LatticeElementCollection parents = current.getParents();
                for (Iterator iterator = parents.iterator(); iterator.hasNext();) {
                    LatticeElement parent = (LatticeElement) iterator.next();
                    int compare = intent.compare(parent.getAttribs());
                    switch (compare) {
                        case Set.EQUAL:
                            return parent;
                        case Set.SUBSET:
                            current = parent;
                            break outer;
                        case Set.SUPERSET:
                            //the case, when we reach a superset - that means, that there is no equal
                            return current;
                        case Set.NOT_COMPARABLE:
                            continue;
                    }
                }
                return current;
            }
        }
    }

    public static int latticeWidthLowerBound(Lattice lattice) {
        int height = lattice.getHeight();
        final int[] ranksMatrix = new int[height + 1];
        lattice.forEach(new Lattice.LatticeElementVisitor() {
            public void visitNode(LatticeElement node) {
                ranksMatrix[node.getHeight()]++;
            }
        });
        return maximum(ranksMatrix);
    }

    private static int maximum(final int[] anArray) {
        final int lastIndex = anArray.length - 1;
        int max = anArray[lastIndex];
        for (int i = anArray.length; --i >= 0;) {
            int value = anArray[i];
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    public static int latticeWidthUpperBound(Lattice lattice) {
        return lattice.conceptsCount() - lattice.getHeight();
    }
}

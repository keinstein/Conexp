package conexp.core;

import java.util.Iterator;

/*
 * This program is a part of the Darmstadt JSM Implementation.
 *
 * You can redistribute it (modify it, compile it, decompile it, whatever)
 * AMONG THE JSM COMMUNITY. If you plan to use this program outside the
 * community, please notify V.K.Finn (finn@viniti.ru) and the authors.
 *
 * Authors: Peter Grigoriev and Serhiy Yevtushenko
 * E-mail: {peter, sergey}@intellektik.informatik.tu-darmstadt.de
 *
 * Date: 5/7/2003
 * Time: 19:51:54
 */

public class LatticeAlgorithms {

    public static LatticeElement findBottomUpMinimalElementThatIncludesSet(LatticeElement startNode, Set intent) {
        if (intent.equals(startNode.getAttribs())) {
            return startNode;
        }
        LatticeElement current = startNode;
        while (true) {
            outer:{
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
}

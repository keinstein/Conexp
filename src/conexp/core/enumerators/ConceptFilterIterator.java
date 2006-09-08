/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.enumerators;

import conexp.core.LatticeElement;
import util.collection.CollectionFactory;

import java.util.Set;

public class ConceptFilterIterator extends DepthSearchIterator {
    private Set visited;

    //todo: add API for skipping first element
    //todo: allow to add antimonotonic search constraints

    public ConceptFilterIterator(LatticeElement start) {
        super();
        initDepthIterator(start, start.getSuccCount());
        visited = CollectionFactory.createDefaultSet();
    }

    protected LatticeElement findNextConcept() {
        //todo: think about possibility of bitset based visited state maintaince
        while (true) {
            PosInfo info = getTopElementOfStack();
            LatticeElement curr = info.curr;
            for (; --info.pos >= 0;) {
                LatticeElement child = curr.getSucc(info.pos);
                if (!visited.contains(child)) {
                    initStackVariables(depth + 1, child, child.getSuccCount(), info.mask);
                    visited.add(child);
                    depth++;
                    return getTopElementOfStack().curr;
                }
            }
            if (--depth < 0) {
                return null;
            }
        }
    }

}

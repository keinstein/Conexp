/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.core.enumerators;

import conexp.core.LatticeElement;

public class ConceptFilterIterator extends DepthSearchIterator {

    //todo: add API for skipping first element
    //todo: allow to add antimonotonic search constraints

    public ConceptFilterIterator(LatticeElement start) {
        super();
        initDepthIterator(start, start.getSuccCount());
    }

    protected LatticeElement findNextConcept() {
        while (true) {
            PosInfo info = getTopElementOfStack();
            LatticeElement curr = info.curr;
            for (; --info.pos >= 0;) {
                LatticeElement child = curr.getSucc(info.pos);
                temp.copy(curr.getAttribs());
                temp.andNot(child.getAttribs());
                if (!info.mask.intersects(temp)) {
                    initStackVariables(depth + 1, child, child.getSuccCount(), info.mask);
                    info.mask.or(temp);
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

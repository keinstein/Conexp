/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.enumerators;

import conexp.core.LatticeElement;

public class ConceptIdealIterator extends DepthSearchIterator {
    public ConceptIdealIterator(LatticeElement start) {
        super();
        // Assert.isTrue(start.getHeight() >= 0);
        initDepthIterator(start, start.getPredCount());
    }


    protected LatticeElement findNextConcept() {
        while (true) {
            DepthSearchIterator.PosInfo info = getTopElementOfStack();
            LatticeElement curr = info.curr;
            for (; --info.pos >= 0;) {
                LatticeElement child = curr.getPred(info.pos);
                temp.copy(child.getAttribs());
                temp.andNot(curr.getAttribs());
                if (!info.mask.intersects(temp)) {
                    initStackVariables(depth + 1, child, child.getPredCount(), info.mask);
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

/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor.figures.tests;

import com.mockobjects.ExpectationCounter;
import conexp.frontend.latticeeditor.figures.AbstractConceptCorrespondingFigure;
import conexp.frontend.latticeeditor.figures.DefaultFigureVisitor;


public class MockFigureVisitor extends DefaultFigureVisitor {
    private ExpectationCounter counter = new ExpectationCounter("Expected number of visits");

    /**
     * Insert the method's description here.
     * Creation date: (23.12.00 13:38:05)
     */
    public void setExpectedVisits(int cnt) {
        counter.setExpected(cnt);
    }

    /**
     * Insert the method's description here.
     * Creation date: (23.12.00 13:40:06)
     */
    public void verify() {
        counter.verify();
    }

    /**
     * visitConceptFigure method comment.
     */
    public void visitConceptCorrespondingFigure(AbstractConceptCorrespondingFigure f) {
        counter.inc();
    }
}

/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.latticeeditor;

import conexp.core.ConceptsCollection;
import conexp.core.ExtendedContextEditingInterface;


public class DefaultDimensionCalcStrategy extends AbstractDimensionCalcStrategy {

    protected DrawParameters options;

    /**
     * Insert the method's description here.
     * Creation date: (11.10.00 22:14:00)
     * @param opt conexp.frontend.LatticePainterOptions
     */
    public DefaultDimensionCalcStrategy(DrawParameters opt) {
        super();
        setDrawParams(opt);
    }

    /**
     * Insert the method's description here.
     * Creation date: (11.10.00 21:40:03)
     * @param newOptions conexp.frontend.LatticePainterOptions
     */
    public void setDrawParams(DrawParameters newOptions) {
        options = newOptions;
    }


    private ConceptsCollection conceptSet;

    protected ConceptsCollection getConceptSet() {
        return conceptSet;
    }

    protected boolean isInit() {
        return null != getConceptSet() && null != getConceptSet().getContext();
    }

    protected ExtendedContextEditingInterface getContext() {
        return getConceptSet().getContext();
    }

    public void setConceptSet(ConceptsCollection conceptSet) {
        this.conceptSet = conceptSet;
    }
}

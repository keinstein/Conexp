/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
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
     *
     * @param opt conexp.frontend.LatticePainterOptions
     */
    public DefaultDimensionCalcStrategy(DrawParameters opt) {
        super();
        setDrawParams(opt);
    }

    /**
     * Insert the method's description here.
     * Creation date: (11.10.00 21:40:03)
     *
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

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DefaultDimensionCalcStrategy)) {
            return false;
        }
        if (!getClass().equals(obj.getClass())) {
            return false;
        }

        final DefaultDimensionCalcStrategy defaultDimensionCalcStrategy = (DefaultDimensionCalcStrategy) obj;

        if (conceptSet != null ? !conceptSet.equals(defaultDimensionCalcStrategy.conceptSet) : defaultDimensionCalcStrategy.conceptSet != null)
        {
            return false;
        }
        if (options != null ? !options.equals(defaultDimensionCalcStrategy.options) : defaultDimensionCalcStrategy.options != null)
        {
            return false;
        }

        return true;
    }

    public int hashCode() {
        int result;
        result = options != null ? options.hashCode() : 0;
        result = 29 * result + (conceptSet != null ? conceptSet.hashCode() : 0);
        return result;
    }

}

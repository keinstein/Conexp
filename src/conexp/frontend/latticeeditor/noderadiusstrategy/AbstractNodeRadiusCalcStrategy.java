/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor.noderadiusstrategy;

import conexp.frontend.latticeeditor.ConceptQuery;
import conexp.frontend.latticeeditor.DefaultDimensionCalcStrategy;
import conexp.frontend.latticeeditor.DrawParameters;
import conexp.frontend.latticeeditor.FigureDimensionCalcStrategy;


public abstract class AbstractNodeRadiusCalcStrategy extends DefaultDimensionCalcStrategy implements FigureDimensionCalcStrategy {
    /**
     * Insert the method's description here.
     * Creation date: (11.10.00 22:14:00)
     *
     * @param opt conexp.frontend.LatticePainterOptions
     */
    public AbstractNodeRadiusCalcStrategy(DrawParameters opt) {
        super(opt);
    }

    /**
     * calcNodeRadius method comment.
     */
    public abstract int calcNodeRadius(ConceptQuery query);
}

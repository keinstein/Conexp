package conexp.frontend.latticeeditor.noderadiusstrategy;

import conexp.frontend.latticeeditor.ConceptQuery;
import conexp.frontend.latticeeditor.DrawParameters;

/**
 * Insert the type's description here.
 * Creation date: (11.10.00 21:44:17)
 */
public class MaxNodeRadiusCalcStrategy extends AbstractNodeRadiusCalcStrategy {
    /**
     * Insert the method's description here.
     * Creation date: (11.10.00 22:15:09)
     * @param opt conexp.frontend.LatticePainterOptions
     */
    public MaxNodeRadiusCalcStrategy(DrawParameters opt) {
        super(opt);
    }

    /**
     * calcNodeRadius method comment.
     */
    public int calcNodeRadius(ConceptQuery query) {
        return options.getMaxNodeRadius();
    }
}
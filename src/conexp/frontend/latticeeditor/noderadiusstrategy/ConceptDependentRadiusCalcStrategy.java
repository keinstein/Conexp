/*
 * User: Serhiy Yevtushenko
 * Date: 09.03.2002
 * Time: 18:16:49
  */
package conexp.frontend.latticeeditor.noderadiusstrategy;

import conexp.core.ConceptsCollection;
import conexp.frontend.latticeeditor.ConceptQuery;
import conexp.frontend.latticeeditor.DrawParameters;
import util.Assert;

public abstract class ConceptDependentRadiusCalcStrategy extends AbstractNodeRadiusCalcStrategy {
    private int deltaRadius;
    //   private double pixelPerObject;
    private double maximalValue;

    public ConceptDependentRadiusCalcStrategy(DrawParameters opt) {
        super(opt);
    }

    /**
     * calcNodeRadius method comment.
     */
    public int calcNodeRadius(ConceptQuery query) {
        return calculateRadius(calculatePercents(query));
        //return options.getMinNodeRadius() + Math.round((float)pixelPerObject * el.getOwnObjCnt());
    }

    protected abstract double calculatePercents(ConceptQuery query);

    private int calculateRadius(double percent) {
        return options.getMinNodeRadius()+Math.round((float)Math.sqrt(percent)*deltaRadius);
    }

    public void initCalc() {
        ConceptsCollection lattice = getConceptSet();
        if (null != lattice) {
            deltaRadius = options.getMaxNodeRadius() - options.getMinNodeRadius();
            maximalValue = calculateMaximalValue(lattice);
            Assert.isTrue(getMaximalValue()>=0);
//            pixelPerObject = deltaRadius / totalObjects;
        }
    }

    protected abstract int calculateMaximalValue(ConceptsCollection conceptSet);

    protected double getMaximalValue() {
        return maximalValue;
    }
}

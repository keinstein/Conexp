/*
 * Created by IntelliJ IDEA.
 * User: sergey
 * Date: Jan 7, 2002
 * Time: 9:14:50 PM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package conexp.core.registries;

import conexp.core.*;
import conexp.core.calculationstrategies.DepthSearchArrowCalculator;
import conexp.core.calculationstrategies.DepthSearchCalculator;
import conexp.core.calculationstrategies.DepthSearchCalculatorWithFeatureMask;
import conexp.core.enumcallbacks.ConceptSetCallback;
import conexp.core.searchconstraints.MinSupportConstrainer;
import util.Assert;

public class FCAEngineImplementation implements FCAEngine {
    public ArrowCalculator makeArrowCalculator() {
        return new DepthSearchArrowCalculator();
    }

    public Lattice buildLattice(Context cxt) {
        DepthSearchCalculator calc = new DepthSearchCalculator();

        Lattice result = makeLatticeForContext(cxt);
        calc.setLattice(result);
        calc.setRelation(cxt.getRelation());
        calc.buildLattice();
        cxt.locateElementsConcepts(result);
        result.calcHeight();
        return result;
    }

    public Lattice buildPartialLattice(Context cxt, Set featureMask) {
        Assert.isTrue(cxt.getAttributeCount()==featureMask.size());
        DepthSearchCalculatorWithFeatureMask calc = new DepthSearchCalculatorWithFeatureMask();
        calc.setFeatureMask(featureMask);

        Lattice result = makeLatticeForContext(cxt);
        calc.setLattice(result);
        calc.setRelation(cxt.getRelation());
        calc.buildLattice();
        cxt.locateElementsConcepts(result, featureMask);
        result.calcHeight();
        return result;
    }

    public Lattice buildIcebergLattice(Context cxt, int minSupport){
        DepthSearchCalculatorWithFeatureMask latticeCalc = new DepthSearchCalculatorWithFeatureMask();
        latticeCalc.setSearchConstrainter(new MinSupportConstrainer(minSupport));
        Lattice result = makeLatticeForContext(cxt);
        latticeCalc.setRelation(cxt.getRelation());
        latticeCalc.setLattice(result);
        latticeCalc.buildLattice();
        return result;
    }

    public ConceptsCollection buildIcebergConceptSet(ExtendedContextEditingInterface cxt, int minSupport) {
        ConceptsCollection conceptSet = new ConceptsCollection();
        ConceptEnumCallback callback = new ConceptSetCallback(conceptSet);
        buildIcebergConceptSet(cxt, minSupport, callback);
        return conceptSet;
    }

    public void buildIcebergConceptSet(ExtendedContextEditingInterface cxt, int minSupport, ConceptEnumCallback callback) {
        DepthSearchCalculatorWithFeatureMask latticeCalc = new DepthSearchCalculatorWithFeatureMask();
        latticeCalc.setSearchConstrainter(new MinSupportConstrainer(minSupport));
        latticeCalc.setRelation(cxt.getRelation());
        latticeCalc.setCallback(callback);
        latticeCalc.calculateConceptSet();
    }

    public Lattice makeLatticeForContext(Context cxt) {
        Lattice ret = new Lattice();
        ret.setContext(cxt);
        return ret;
    }

    public Context makeContext(int rows, int columns) {
        return prepareContext(new Context(rows, columns));
    }

    public Context makeContext(ModifiableBinaryRelation relation) {
        return prepareContext(new Context(relation));
    }

    private Context prepareContext(Context ret) {
        ret.setArrowCalculator(makeArrowCalculator());
        return ret;
    }

}
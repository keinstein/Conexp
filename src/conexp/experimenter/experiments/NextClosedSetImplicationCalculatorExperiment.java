/*
 * Created by IntelliJ IDEA.
 * User: Serhiy Yevtushenko
 * Date: Jul 7, 2002
 * Time: 12:08:47 PM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package conexp.experimenter.experiments;

import conexp.core.ImplicationCalcStrategy;
import conexp.core.calculationstrategies.NextClosedSetImplicationCalculator;

public class NextClosedSetImplicationCalculatorExperiment extends ImplicationSetExperiment {
    protected ImplicationCalcStrategy makeImplicationsCalcStrategy() {
        return new NextClosedSetImplicationCalculator();
    }
}

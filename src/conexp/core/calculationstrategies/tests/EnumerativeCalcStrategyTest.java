package conexp.core.calculationstrategies.tests;

import conexp.core.ConceptsCollection;
import conexp.core.enumcallbacks.ConceptSetCallback;


public abstract class EnumerativeCalcStrategyTest extends CalcStrategyTest {
    protected void setupStrategy(ConceptsCollection conceptSet) {
        calcStrategy.setCallback(new ConceptSetCallback(conceptSet));
    }
}
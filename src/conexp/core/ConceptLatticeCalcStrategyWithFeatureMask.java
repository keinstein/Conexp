/*
 * User: Serhiy Yevtushenko
 * Date: 30.04.2002
 * Time: 20:13:17
 */
package conexp.core;

public interface ConceptLatticeCalcStrategyWithFeatureMask extends ConceptCalcStrategy, LatticeCalcStrategy{
    public void setFeatureMask(Set featureMask);
}

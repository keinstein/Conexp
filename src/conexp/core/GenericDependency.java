package conexp.core;

import contingencytable.ExtendedContingencyTable;
import contingencytable.IContingencyTable;
import contingencytable.IExtendedContingencyTable;
import contingencytable.RuleMeasures;
import util.Assert;

/**
 * Insert the type's description here.
 * Creation date: (01.05.01 13:37:44)
 * @author Serhiy Yevtushenko
 */
public abstract class GenericDependency implements Dependency {

    protected ModifiableSet conclusion;

    protected ModifiableSet premise;

    ExtendedContingencyTable ruleCharacteristics=new ExtendedContingencyTable();

    public GenericDependency(Set premise, Set conclusion, int premiseSupport, int ruleSupport) {
        this.premise = premise.makeModifiableSetCopy();
        this.conclusion = conclusion.makeModifiableSetCopy();
        ruleCharacteristics.setCharacteristic(IContingencyTable.PREMISE,
                        IContingencyTable.CONCLUSION, ruleSupport);
        ruleCharacteristics.setCharacteristic(IContingencyTable.PREMISE,
                        IContingencyTable.CONCLUSION_COMPLEMENT, premiseSupport-ruleSupport);
    }

    public boolean equals(Object obj) {
        if (null == obj) {
            return false;
        }
        if (!(obj instanceof Dependency)) {
            return false;
        }
        Dependency dep = (Dependency) obj;
        if (!getPremise().equals(dep.getPremise())) {
            return false;
        }
        if (getPremiseSupport() != dep.getPremiseSupport()) {
            return false;
        }
        if (!getConclusion().equals(dep.getConclusion())) {
            return false;
        }
        if (getRuleSupport() != dep.getRuleSupport()) {
            return false;
        }
        return true;
    }

    public Set getConclusion() {
        return conclusion;
    }

    public int getConclusionLength() {
        return getConclusion().elementCount();
    }

    public Set getPremise() {
        return premise;
    }

    public int hashCode() {
        return premise.hashCode() ^ conclusion.hashCode();
    }

    public boolean isExact() {
        return getPremiseSupport() == getRuleSupport();
    }

    public String toString() {
        return this.getClass().getName() + " : " + premise + "==>" + conclusion;
    }

    int cachedPremiseLength = -1;

    public int getPremiseLength() {
        if(cachedPremiseLength==-1){
            cachedPremiseLength = premise.elementCount();
        }
        return cachedPremiseLength;
    }

    public IExtendedContingencyTable getCharacteristics() {
        return ruleCharacteristics;
    }

    public void setConclusionSupportAndTotalObjectCount(int conclusionSupport, int totalObjectCount) {

        ruleCharacteristics.setCharacteristic(IContingencyTable.PREMISE_COMPLEMENT,
                IContingencyTable.CONCLUSION, conclusionSupport - RuleMeasures.support(ruleCharacteristics));
        ruleCharacteristics.setCharacteristic(IContingencyTable.PREMISE_COMPLEMENT,
                IContingencyTable.CONCLUSION_COMPLEMENT, totalObjectCount-conclusionSupport-
                ruleCharacteristics.getCharacteristic(IContingencyTable.PREMISE, IContingencyTable.CONCLUSION_COMPLEMENT)
                );

        Assert.isTrue(ruleCharacteristics.getCharacteristicTotal(IContingencyTable.PREMISE)+
                ruleCharacteristics.getCharacteristicTotal(IContingencyTable.PREMISE_COMPLEMENT)==totalObjectCount);
        Assert.isTrue(ruleCharacteristics.getCharacteristicTotal(IContingencyTable.CONCLUSION)+
                ruleCharacteristics.getCharacteristicTotal(IContingencyTable.CONCLUSION_COMPLEMENT)==totalObjectCount);

    }

    public int getRuleSupport() {
        return RuleMeasures.support(ruleCharacteristics);
    }

    public int getPremiseSupport() {
        return ruleCharacteristics.getCharacteristicTotal(IContingencyTable.PREMISE);
    }

    public double getConfidence() {
        return RuleMeasures.confidence(ruleCharacteristics);
    }

    public boolean isValid() {
        if (getRuleSupport() > getPremiseSupport()) {
            return false;
        }
        if (getRuleSupport() <= 0) {
            return false;
        }
        if (getConclusion().elementCount() == 0) {
            return false;
        }
        return true;
    }

}
package conexp.core;

import contingencytable.IExtendedContingencyTable;

public interface Dependency {
    Set getConclusion();
    int getConclusionLength();
    Set getPremise();


    int getPremiseLength();

    boolean isExact();

    int getPremiseSupport();
    int getRuleSupport();
    double getConfidence();


    IExtendedContingencyTable getCharacteristics();

    void setConclusionSupportAndTotalObjectCount(int conclusionSupport, int totalObjectCount);
}
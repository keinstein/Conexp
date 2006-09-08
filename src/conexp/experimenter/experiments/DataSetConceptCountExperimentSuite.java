/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.experimenter.experiments;

import conexp.experimenter.framework.ExperimentSet;
import conexp.experimenter.framework.RelationSequenceSet;
import conexp.experimenter.relationsequences.ContextLoadingRelationGenerationStrategy;
import util.DataFormatException;

import java.io.IOException;


public class DataSetConceptCountExperimentSuite extends ExperimentSuite {
    public static void conceptSetExperiment() throws IOException, DataFormatException {
//        doRunTimeMeasurementExperiment(createScalingExperiment2(), createRelationSequence());
        doRunTimeMeasurementExperiment(createExperimentSet(), createRelationSequence());

    }

    static final String basePath = "c:\\Work\\Science\\Personal\\dissertation\\datasets\\AlreadyCharacterized\\";

    static String makePath(String fileName) {
        return basePath + fileName;
    }

    private static RelationSequenceSet createRelationSequence() throws DataFormatException, IOException {
        String[] files = {
/*
            "marketing3.cxt",
            "c20d10.cxt",

            "mushroom_exp.cxt",
*/
                //"audiology.cxt",
                "c73d10.cxt",
/*
            "vote.cxt",
            "tic-tac-toe.cxt",
            "primary-tumor.cxt",
            "post-operative.cxt",
*/
//            "vote-both.cxt",

/*
            "kr-vs-kp.cxt",
            "letter.cxt"
*/
//            "t20i6d100.cxt",
/*
            "breast-w.cxt",
            "breast-cancer.cxt",
            "lymph.cxt",
*/
//            "zoo.cxt",

//            "splice.cxt"
        };
        RelationSequenceSet relationSequenceSet = new RelationSequenceSet();
        for (int i = 0; i < files.length; i++) {
            String file = files[i];
            relationSequenceSet.addRelationSequence(new ContextLoadingRelationGenerationStrategy(makePath(file)));
        }
        return relationSequenceSet;
    }

    public static ExperimentSet createExperimentSet() {
        ExperimentSet set = new ExperimentSet();
//        set.addExperiment(new ConceptCountExperiment("conexp.core.calculationstrategies.DepthSearchCalculator"));
        set.addExperiment(new ConceptCountExperiment("research.conexp.core.calculationstrategies.DepthSearchIntentsCalcStrategyWithFastObjectIndex"));
        return set;
    }

    public static ExperimentSet createScalingExperiment() {
        ExperimentSet set = new ExperimentSet();
        set.addExperiment(new ConceptCountWithMinSupportExperiment(0.05));
        for (int i = 1; i < 10; i++) {
            set.addExperiment(new ConceptCountWithMinSupportExperiment(0.1 * i));
        }
        set.addExperiment(new ConceptCountExperiment("research.conexp.core.calculationstrategies.DepthSearchIntentsCalcStrategyWithFastObjectIndex"));
        return set;
    }

    public static ExperimentSet createScalingExperiment2() {
        ExperimentSet set = new ExperimentSet();
        for (int i = 1; i < 10; i++) {
            set.addExperiment(new ConceptCountWithMaxConceptSizeExperiment(i));
        }
        return set;
    }

    public static void main(String[] args) {
        try {
            conceptSetExperiment();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.experimenter.experiments;

import conexp.experimenter.framework.ExperimentSet;
import conexp.experimenter.framework.RelationSequenceSet;
import conexp.experimenter.relationsequences.ContextLoadingRelationGenerationStrategy;
import conexp.experimenter.relationsequences.DirectoryRelationSequenceBuilder;
import conexp.experimenter.relationsequences.TransposeRelationSequenceDecorator;
import conexp.frontend.io.TabSeparatedContextReaderFactory;
import util.DataFormatException;

import java.io.IOException;


public class BigConceptSetExperimentSuite extends ExperimentSuite {
    public static void conceptSetExperiment() {

        try {
            //doRunExperimentWithCountingOfSetOperations(createExperimentSet(), createRelationSequence());
            //doRunTimeMeasurementExperiment(createExperimentSet(), createRelationSequence());
            doRunTimeMeasurementExperiment(createExperimentSet(), ExperimentSuite.createRelationSequenceSet());
            //doRunTimeMeasurementExperiment(createExperimentSet(), createRelationSequenceObjedkov());
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }
    }

    static final String basePath = "c:\\Work\\Science\\Personal\\dissertation\\datasets\\AlreadyCharacterized\\";

    static String makePath(String fileName) {
        return basePath + fileName;
    }

    private static RelationSequenceSet createRelationSequence() throws IOException, DataFormatException {
        final RelationSequenceSet relationSequenceSet = makeContextsRelationSequence();
        int originalCount = relationSequenceSet.getRelationSequenceCount();
        for (int i = 0; i < originalCount; i++) {
            relationSequenceSet.addRelationSequence(new TransposeRelationSequenceDecorator(
                    relationSequenceSet.getRelationSequence(i)
            ));
        }
        return relationSequenceSet;
    }

    private static RelationSequenceSet makeContextsRelationSequence() throws IOException, DataFormatException {
        RelationSequenceSet relationSequenceSet = new RelationSequenceSet();
        String[] files = {
                "zoo.cxt", //379
                "post-operative.cxt", //2378
                "dbdata0.cxt", //2692
                "primary-tumor.cxt", //3743
                "breast-w.cxt", //9824
                "breast-cancer.cxt", //9918
                "lymph.cxt", //15504
                "SPECT_ALL.cxt", //21550
                "MortonRolphRacialStats2.cxt", //32017
                "flare.cxt",//28742
                "tic-tac-toe.cxt", //59505
/*
            "voegel6.cxt", //153385
            "kr-vs-kp.cxt", //101121
*/
//            "mushroom_exp.cxt", //238710,
                //"marketing3.cxt", //872118
/*            "dmc_2003-train.cxt",//
*/
        };
        for (int i = 0; i < files.length; i++) {
            String file = files[i];
            //  relationSequenceSet.addRelationSequence(new ContextLoadingRelation10IncreasingPartsGenerationStrategy( makePath(file)));
            relationSequenceSet.addRelationSequence(new ContextLoadingRelationGenerationStrategy(makePath(file)));
        }

        return relationSequenceSet;
    }


    public static RelationSequenceSet createRelationSequenceObjedkov() throws IOException, DataFormatException {
        String path = "c:\\Work\\Science\\Personal\\dissertation\\datasets\\Objedkov\\AllExperiments\\";
        return DirectoryRelationSequenceBuilder.buildRelationSequenceSet(path, "ltc", new TabSeparatedContextReaderFactory());
    }

    public static ExperimentSet createExperimentSet() {
        ExperimentSet set = new ExperimentSet();
        set.addExperiment(new CallbackBasedConceptSetExperiment("conexp.core.calculationstrategies.NextClosedSetCalculator"));
/*
        set.addExperiment(new IntentSetNeedingIntentSetExperiment("research.conexp.core.calculationstrategies.charm.Charm"));
        set.addExperiment(new IntentSetNeedingIntentSetExperiment("research.conexp.core.calculationstrategies.charm.Charm2"));
        set.addExperiment(new IntentSetNeedingIntentSetExperiment("research.conexp.core.calculationstrategies.charm.Charm3"));
*/
        set.addExperiment(new ConceptSetNeedingConceptSetExperiment("research.conexp.core.calculationstrategies.charm.FastSetBasedCharm"));
//        set.addExperiment(new ConceptSetNeedingConceptSetExperiment("research.conexp.core.calculationstrategies.incattr.IncrementalAttributeBasedNorrisCalculator"));

//        set.addExperiment(new ConceptSetNeedingConceptSetExperiment("research.conexp.core.calculationstrategies.charm.OptimisedSetBasedCharm"));
//        set.addExperiment(new ConceptSetNeedingConceptSetExperiment("research.conexp.core.calculationstrategies.charm.FastSetBasedCharm2"));

        set.addExperiment(new CallbackBasedConceptSetExperiment("conexp.core.calculationstrategies.DepthSearchCalculator"));
        set.addExperiment(new CallbackBasedConceptSetExperiment("research.conexp.core.calculationstrategies.SimpleDepthSearchCalculator"));
//        set.addExperiment(new CallbackBasedConceptSetExperiment("research.conexp.core.calculationstrategies.SimpleDepthSearchCalculator2"));
        set.addExperiment(new ConceptSetNeedingConceptSetExperiment("research.conexp.core.calculationstrategies.NorrisCalcStrategy"));
        set.addExperiment(new ConceptSetNeedingConceptSetExperiment("research.conexp.core.calculationstrategies.GodinedNorrisCalcStrategy"));
        set.addExperiment(new ConceptSetNeedingConceptSetExperiment("research.conexp.core.calculationstrategies.NourineRaynaudCalcStrategy"));
//        set.addExperiment(new LatticeBuildExperiment("research.conexp.core.calculationstrategies.AddAtomCalcStrategy"));
/*
        set.addExperiment(new LatticeBuildExperiment("research.conexp.core.calculationstrategies.BasePfalzCalcStrategy"));
        set.addExperiment(new LatticeBuildExperiment("research.conexp.core.calculationstrategies.AdvancedPfalzCalcStrategy"));
*/
        return set;
    }

    /**
     * Insert the method's description here.
     * Creation date: (06.07.01 13:59:10)
     *
     * @param args java.lang.String[]
     */
    public static void main(String[] args) {
        conceptSetExperiment();
    }


}

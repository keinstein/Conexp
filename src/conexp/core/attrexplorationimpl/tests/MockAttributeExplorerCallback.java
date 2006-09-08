/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.attrexplorationimpl.tests;

import conexp.core.AttributeExplorationError;
import conexp.core.AttributeExplorer;
import conexp.core.AttributeInformationSupplier;
import conexp.core.ContextEntity;
import conexp.core.LocalizedMessageSupplier;
import conexp.core.ModifiableSet;
import conexp.core.Set;
import conexp.core.tests.SetBuilder;
import junit.framework.Assert;

public class MockAttributeExplorerCallback extends Assert implements AttributeExplorer.AttributeExplorerUserCallback {

    public static class AcceptImplicationInfo {

        int response;
        Set expPremise;
        Set expConclusion;

        public AcceptImplicationInfo(int response, Set expPremise, Set expConclusion) {
            this.response = response;
            this.expPremise = expPremise;
            this.expConclusion = expConclusion;
        }
    }

    public static class ContrExampleInfo {
        int response;
        Set contrExample;

        public ContrExampleInfo(int response, Set contrexample) {
            this.response = response;
            this.contrExample = contrexample;
        }
    }

    public MockAttributeExplorerCallback() {
        this.acceptCounter = 0;
        this.contrExampleCounter = 0;
    }

    private int acceptCounter;
    private AcceptImplicationInfo[] expSequence;

    private int contrExampleCounter;
    private ContrExampleInfo[] contrExamples;

    public void setContrExamples(ContrExampleInfo[] contrExamples) {
        this.contrExamples = contrExamples;
    }

    public void setExpSequence(AcceptImplicationInfo[] expSequence) {
        this.expSequence = expSequence;
    }


    public int isTrue(Set premise, Set conclusion) {
        AcceptImplicationInfo current = expSequence[acceptCounter];
        if (!current.expPremise.equals(premise)) {
            fail("On step " + acceptCounter + " expected premise " + current.expPremise + " got premise " + premise);
        }
        if (!current.expConclusion.equals(conclusion)) {
            fail("On step " + acceptCounter + " expected conclusion " + current.expConclusion + " got conclusion " + conclusion);
        }
        acceptCounter++;
        return current.response;
    }

    public int queryContrExample(ContextEntity obj, ModifiableSet contrExample) {
        ContrExampleInfo curr = contrExamples[contrExampleCounter];
        contrExampleCounter++;
        if (curr.response == HAS_CONTREXAMPLE) {
            contrExample.copy(curr.contrExample);
        }
        return curr.response;
    }

    public void setAttributeInformationSupplier(AttributeInformationSupplier attrInfo) {

    }

    public void error(AttributeExplorationError error) {
        error.formatMessage(new LocalizedMessageSupplier() {
            public String getMessage(String key) {
                return " No message";
            }
        });
    }

    public static AcceptImplicationInfo[] makeAcceptImplicationInfoSequence(int[][][] acceptInfo) {
        AcceptImplicationInfo[] ret = new AcceptImplicationInfo[acceptInfo.length];
        for (int i = 0; i < acceptInfo.length; i++) {
            ret[i] = makeAcceptInformationInfo(acceptInfo[i][0][0], acceptInfo[i][1], acceptInfo[i][2]);
        }
        return ret;
    }

    public static ContrExampleInfo[] makeContrExampleSequence(int[][][] contrExamples) {
        ContrExampleInfo[] ret = new ContrExampleInfo[contrExamples.length];
        for (int i = 0; i < contrExamples.length; i++) {
            ret[i] = new ContrExampleInfo(contrExamples[i][0][0],
                    contrExamples[i][0][0] == HAS_CONTREXAMPLE ?
                            SetBuilder.makeSet(contrExamples[i][1]) : null);
        }
        return ret;
    }

    private static AcceptImplicationInfo makeAcceptInformationInfo(int response, int[] premise, int[] conclusion) {
        return new AcceptImplicationInfo(response, SetBuilder.makeSet(premise), SetBuilder.makeSet(conclusion));
    }

}

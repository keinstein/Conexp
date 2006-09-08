package conexp.experimenter.relationsequences;

import conexp.core.BinaryRelation;
import conexp.core.BinaryRelationUtils;
import conexp.frontend.io.ConImpContextReaderFactory;
import util.DataFormatException;

import java.io.IOException;

/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 * Date: 19/8/2003
 * Time: 23:14:27
 */

public class ContextLoadingRelation10IncreasingPartsGenerationStrategy extends ContextLoadingRelationGenerationStrategy {


    public ContextLoadingRelation10IncreasingPartsGenerationStrategy(String url) throws IOException, DataFormatException {
        super(url, 10, new ConImpContextReaderFactory());
    }


    public BinaryRelation makeRelation(int relNo) {
        BinaryRelation baseRelation = getContext().getRelation();
        if (relNo == count - 1) {
            return baseRelation;
        }
        float part = (relNo + 1f) / (float) count;
        int size = Math.round(part * baseRelation.getRowCount());

        return BinaryRelationUtils.createSlice(baseRelation, 0, size - 1);
    }

}

package conexp.experimenter.relationsequences;

import conexp.core.BinaryRelation;
import conexp.core.Context;
import conexp.frontend.io.ConImpContextLoader;
import conexp.frontend.io.ContextReader;
import util.Assert;
import util.DataFormatException;

import java.io.FileReader;
import java.io.IOException;

/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 * Date: 19/8/2003
 * Time: 23:14:27
 */

public class ContextLoadingRelationGenerationStrategy extends BaseRelationGenerationStrategy {
    private Context context;
    private String url;


    public ContextLoadingRelationGenerationStrategy(String url) throws IOException, DataFormatException{
        super(1);
        this.url = url;
        loadContext(url);
        createRelations();
    }

    private void loadContext(String url) throws IOException, DataFormatException {
        ContextReader loader = new ConImpContextLoader();
        FileReader fileReader=null;
        try {
            fileReader = new FileReader(url);
            context = loader.parseContext(fileReader);
        } finally {
            if(null!=fileReader){
                fileReader.close();
            }
        }
    }


    public BinaryRelation makeRelation(int relNo) {
        Assert.isTrue(relNo<=count);
        return context.getRelation();
    }

    public String describeStrategy() {
        return "From file:"+url;
    }
}

package conexp.experimenter.relationsequences;

import conexp.core.BinaryRelation;
import conexp.core.Context;
import conexp.frontend.io.ConImpContextLoader;
import conexp.frontend.io.ContextReader;
import conexp.frontend.io.ContextReaderFactory;
import conexp.frontend.io.ConImpContextReaderFactory;
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
    protected Context context;
    private String url;
    protected ContextReaderFactory contextReaderFactory;



    public ContextLoadingRelationGenerationStrategy(String url) throws IOException, DataFormatException{
        this(url, new ConImpContextReaderFactory());
    }

    public ContextLoadingRelationGenerationStrategy(String url, ContextReaderFactory contextReaderFactory) throws IOException, DataFormatException{
        this(url, 1, contextReaderFactory);
    }

    protected ContextLoadingRelationGenerationStrategy(String url, int count, ContextReaderFactory contextReaderFactory) throws IOException, DataFormatException{
        super(count);
        this.url = url;
        this.contextReaderFactory = contextReaderFactory;
        loadContext(url);
        createRelations();
    }

    private void loadContext(String url) throws IOException, DataFormatException {
        ContextReader loader = makeContextReader();
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

    private ContextReader makeContextReader() {
        return contextReaderFactory.makeContextReader();
    }

    protected Context getContext() {
        return context;
    }

    public BinaryRelation makeRelation(int relNo) {
        Assert.isTrue(relNo<=count);
        return context.getRelation();
    }

    public String describeStrategy() {
        return "From file:"+url;
    }
}

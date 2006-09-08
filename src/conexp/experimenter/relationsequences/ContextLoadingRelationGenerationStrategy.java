/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.experimenter.relationsequences;

import conexp.frontend.io.ConImpContextReaderFactory;
import conexp.frontend.io.ContextReader;
import conexp.frontend.io.ContextReaderFactory;
import util.DataFormatException;

import java.io.FileReader;
import java.io.IOException;



public class ContextLoadingRelationGenerationStrategy extends ContextBasedRelationSequence {
    private String url;
    protected ContextReaderFactory contextReaderFactory;


    public ContextLoadingRelationGenerationStrategy(String url) throws IOException, DataFormatException {
        this(url, new ConImpContextReaderFactory());
    }

    public ContextLoadingRelationGenerationStrategy(String url, ContextReaderFactory contextReaderFactory) throws IOException, DataFormatException {
        this(url, 1, contextReaderFactory);
    }

    protected ContextLoadingRelationGenerationStrategy(String url, int count, ContextReaderFactory contextReaderFactory) throws IOException, DataFormatException {
        super(count);
        this.url = url;
        this.contextReaderFactory = contextReaderFactory;
        loadContext(url);
        createRelations();
    }

    private void loadContext(String url) throws IOException, DataFormatException {
        ContextReader loader = makeContextReader();
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(url);
            setContext(loader.parseContext(fileReader));
        } finally {
            if (null != fileReader) {
                fileReader.close();
            }
        }
    }

    private ContextReader makeContextReader() {
        return contextReaderFactory.makeContextReader();
    }

    public String describeStrategy() {
        return "From file:" + url;
    }
}

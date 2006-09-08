/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend;

import conexp.frontend.io.ConExpXMLReader;
import conexp.frontend.io.ConExpXMLWriter;
import conexp.frontend.io.ConImpContextLoader;
import conexp.frontend.io.ConImpContextWriter;
import conexp.frontend.io.ContextReaderAdapter;
import conexp.frontend.io.TabSeparatedContextReader;
import conexp.frontend.io.TabSeparatedContextWriter;
import conexp.frontend.io.csv.CSVContextCreator;
import conexp.frontend.io.objattrlist.ObjectListContextCreator;
import conexp.frontend.util.StorageFormatManager;
import conexp.frontend.util.StorageFormatRecord;

public class ConExpStorageFormatManager extends StorageFormatManager {

    public ConExpStorageFormatManager() {
        super();
        setAllowAllSupported(true);
        registerStorageFormat(new StorageFormatRecord("Cxt files", "cxt", new ContextReaderAdapter(new ConImpContextLoader()), new ConImpContextWriter()));
        registerStorageFormat(new StorageFormatRecord("CSV files", "csv", new ContextReaderAdapter(new CSVContextCreator()), null));
        registerStorageFormat(new StorageFormatRecord("Tab separated files", "txt", new ContextReaderAdapter(new TabSeparatedContextReader()), new TabSeparatedContextWriter()));
        registerStorageFormat(new StorageFormatRecord("List of Object and its attributes", "oal", new ContextReaderAdapter(new ObjectListContextCreator()), null));
        registerDefaultStorageFormat(new StorageFormatRecord("ConExp xml format", "cex", new ConExpXMLReader(), new ConExpXMLWriter()));


    }
}

/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend;

import conexp.frontend.io.*;
import conexp.frontend.io.csv.CSVContextCreator;
import conexp.frontend.io.objattrlist.ObjectListContextCreator;
import conexp.frontend.util.StorageFormatManager;
import conexp.frontend.util.StorageFormatRecord;

public class ConExpStorageFormatManager extends StorageFormatManager {

    public ConExpStorageFormatManager() {
        super();
        setAllowAllSupported(true);
        registerStorageFormat(
                new StorageFormatRecord("Cxt files", "cxt", new ContextReaderDocumenLoaderAdapter(new ConImpContextLoader()), new ConImpContextWriter()));
        registerStorageFormat(new StorageFormatRecord("CSV files", "csv", new ContextReaderDocumenLoaderAdapter(new CSVContextCreator()), null));
        registerStorageFormat(new StorageFormatRecord("List of Object and its attributes", "oal", new ContextReaderDocumenLoaderAdapter(new ObjectListContextCreator()), null));
        registerDefaultStorageFormat(
                new StorageFormatRecord("ConExp xml format", "cex", new ConExpXMLReader(), new ConExpXMLWriter()));


    }
}

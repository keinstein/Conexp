/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend;

import conexp.frontend.io.ConExpXMLReader;
import conexp.frontend.io.ConExpXMLWriter;
import conexp.frontend.io.ConImpContextDocumenLoaderAdapter;
import conexp.frontend.io.ConImpContextWriter;
import conexp.frontend.util.StorageFormatManager;
import conexp.frontend.util.StorageFormatRecord;

public class ConExpStorageFormatManager extends StorageFormatManager {
    public ConExpStorageFormatManager() {
        super();
        registerStorageFormat(
                new StorageFormatRecord("Cxt files", "cxt", new ConImpContextDocumenLoaderAdapter(), new ConImpContextWriter()));
        registerDefaultStorageFormat(
                new StorageFormatRecord("ConExp xml format", "cex", new ConExpXMLReader(), new ConExpXMLWriter()));

    }
}

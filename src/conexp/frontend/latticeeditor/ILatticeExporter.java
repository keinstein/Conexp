package conexp.frontend.latticeeditor;

import java.io.IOException;

/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 * Date: 30/12/2003
 * Time: 14:17:07
 */

public interface ILatticeExporter {
    String[][] getDescriptions();
    boolean accepts(String path);
    void performExportService(String path) throws IOException;
}

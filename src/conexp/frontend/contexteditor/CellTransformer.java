/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.contexteditor;

interface CellTransformer {
    Object transformedValue(Object oldValue);
}

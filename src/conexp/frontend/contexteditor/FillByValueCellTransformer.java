/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.contexteditor;

public class FillByValueCellTransformer implements CellTransformer {

    public FillByValueCellTransformer(Object fillValue) {
        this.fillValue = fillValue;
    }

    Object fillValue;

    public Object transformedValue(Object oldValue) {
        return fillValue;
    }
}

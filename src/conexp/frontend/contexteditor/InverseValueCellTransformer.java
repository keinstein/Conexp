/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.contexteditor;

public class InverseValueCellTransformer implements CellTransformer {
    public Object transformedValue(Object oldValue) {
        if (oldValue instanceof Boolean) {
            if (Boolean.TRUE.equals(oldValue)) {
                return Boolean.FALSE;
            }
            return Boolean.TRUE;
        }
        return oldValue;
    }
}

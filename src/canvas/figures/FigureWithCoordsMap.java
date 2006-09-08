/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package canvas.figures;

import java.util.HashMap;
import java.util.Map;

public class FigureWithCoordsMap {

    protected Map map = new HashMap();

    public void put(Object object, IFigureWithCoords figure) {
        map.put(object, figure);
    }

    public FigureWithCoords get(Object key) {
        return (FigureWithCoords) map.get(key);
    }

    public boolean isEmpty() {
        return map.isEmpty();
    }

    public void clear() {
        map.clear();
    }
}

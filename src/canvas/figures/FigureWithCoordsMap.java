/*
 * User: Serhiy Yevtushenko
 * Date: 09.03.2002
 * Time: 10:13:34
  */
package canvas.figures;

import java.util.HashMap;
import java.util.Map;

public class FigureWithCoordsMap {

    protected Map map = new HashMap();

    public void put(Object object, IFigureWithCoords figure){
        map.put(object, figure);
    }

    public FigureWithCoords get(Object key){
        return (FigureWithCoords)map.get(key);
    }

    public boolean isEmpty(){
        return map.isEmpty();
    }

    public void clear(){
        map.clear();
    }
}

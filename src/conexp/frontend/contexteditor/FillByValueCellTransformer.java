/*
 * User: Serhiy Yevtushenko
 * Date: 09.04.2002
 * Time: 13:15:18
 */
package conexp.frontend.contexteditor;

public class FillByValueCellTransformer implements CellTransformer{

    public FillByValueCellTransformer(Object fillValue){
        this.fillValue = fillValue;
    }

    Object fillValue;

    public Object transformedValue(Object oldValue) {
        return fillValue;
    }
}

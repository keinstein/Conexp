/*
 * Created by IntelliJ IDEA.
 * User: Serhiy Yevtushenko
 * Date: 09.04.2002
 * Time: 13:28:05
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package conexp.frontend.contexteditor;

public class InverseValueCellTransformer implements CellTransformer{
    public Object transformedValue(Object oldValue) {
        if(oldValue instanceof Boolean){
           if(Boolean.TRUE.equals(oldValue)){
               return Boolean.FALSE;
           }
           return Boolean.TRUE;
        }
        return oldValue;
    }
}

/*
 * Created by IntelliJ IDEA.
 * User: Serhiy Yevtushenko
 * Date: Jun 17, 2002
 * Time: 9:31:51 PM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package conexp.frontend.components;


public abstract class BasicSingleSelectionAttributeMaskImplementation extends BasicAttributeMaskImplementation {
    public BasicSingleSelectionAttributeMaskImplementation() {
        super();
    }

    public void setAttributeSelected(int index, boolean attributeSelected) {
        boolean oldValue = isAttributeSelected(index);
        if (oldValue != attributeSelected) {
            final int attributeCount = getAttributeCount();
            if (attributeSelected) {
                for (int i = 0; i < attributeCount; i++) {
                    doSetAttributeSelected(i, i == index);
                }
            }else{
                doSetAttributeSelected(index, false);
            }
            getPropertyChangeSupport().firePropertyChange(ATTRIBUTE_SELECTION_CHANGED, null, null);
        }
    }
}


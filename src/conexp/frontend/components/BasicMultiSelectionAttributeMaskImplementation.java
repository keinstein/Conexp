/*
 * User: Serhiy Yevtushenko
 * Date: May 28, 2002
 */
package conexp.frontend.components;



public abstract class BasicMultiSelectionAttributeMaskImplementation extends BasicAttributeMaskImplementation {

    public void setAttributeSelected(int index, boolean attributeSelected) {
        boolean oldValue = isAttributeSelected(index);
        if (oldValue != attributeSelected) {
            doSetAttributeSelected(index, attributeSelected);
            getPropertyChangeSupport().firePropertyChange(ATTRIBUTE_SELECTION_CHANGED, null, null);
        }
    }

    public void selectAll() {
       setValueForAll(Boolean.TRUE);
    }

    public void deselectAll() {
        setValueForAll(Boolean.FALSE);
    }


}

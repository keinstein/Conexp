/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend;

import util.PropertyChangeSupplier;

public interface AttributeMask extends PropertyChangeSupplier {
    String ATTRIBUTE_NAMES_CHANGED = "AttributeMask.AttributeNamesChanged";
    String ATTRIBUTE_SELECTION_CHANGED = "AttributeMask.AttributeSelectionChanged";
    String ATTRIBUTE_COUNT_CHANGED = "AttributeMask.AttributeCountChanged";

    int getAttributeCount();

    boolean isAttributeSelected(int index);

    void setAttributeSelected(int index, boolean attributeSelected);

    String getAttributeName(int index);
}

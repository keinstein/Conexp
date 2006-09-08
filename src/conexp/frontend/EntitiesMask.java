/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend;

import util.PropertyChangeSupplier;

public interface EntitiesMask extends PropertyChangeSupplier {
    String ENTITIES_NAMES_CHANGED = "EntitiesMask.AttributeNamesChanged";
    String ENTITIES_SELECTION_CHANGED = "EntitiesMask.AttributeSelectionChanged";
    String ENTITIES_COUNT_CHANGED = "EntitiesMask.AttributeCountChanged";

    int getCount();

    boolean isSelected(int index);

    void setSelected(int index, boolean newValue);

    String getName(int index);
}

/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core;

public interface Fragment extends Cloneable {
    public Object clone();

    boolean intersects(Fragment other);

    boolean isEquals(Fragment obj);

    boolean isSupersetOf(Fragment other);

    boolean isSubsetOf(Fragment s);

    boolean isEmpty();

    ModifiableFragment makeModifiableFragment();
}

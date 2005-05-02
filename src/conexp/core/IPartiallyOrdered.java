package conexp.core;

/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 */
public interface IPartiallyOrdered {
    boolean isLesserThan(IPartiallyOrdered other);

    boolean isEqual(IPartiallyOrdered other);
}

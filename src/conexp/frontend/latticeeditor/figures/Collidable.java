package conexp.frontend.latticeeditor.figures;

/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 * Date: 14/7/2003
 * Time: 2:48:01
 */

public interface Collidable {
    boolean hasCollision();

    void setCollision(boolean value);
}

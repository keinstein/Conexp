/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core;

public interface ModifiableFragment extends Fragment {
    void copy(Fragment fragment);

    /**
     *   performs inplace and operation
     */
    void and(Fragment set);

    /**
     *      performs inplace andNot operation
     */
    void andNot(Fragment set);

}

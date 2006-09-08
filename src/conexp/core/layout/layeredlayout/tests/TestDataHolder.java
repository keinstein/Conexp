/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.core.layout.layeredlayout.tests;


public class TestDataHolder {
    public static final int[][] FULL_RELATION_NOMINAL_3 = new int[][]{
            {0, 0, 0},
            {1, 0, 0},
            {0, 1, 0},
            {0, 0, 1},
            {1, 1, 1}
    };

    public static final int[][] FULL_RELATION_NOMINAL_2 = new int[][]{
            {0, 0},
            {0, 1},
            {1, 0},
            {1, 1}
    };

    public static final double[][] SYMMETRIC_LAYOUT_NOMINAL_2 = new double[][]{
            {0, 0},
            {-1, 1},
            {1, 1},
            {0, 2}
    };

    public static final double[][] ASYMMETRIC_LAYOUT_NOMINAL_2 = new double[][]{
            {0, 0},
            {0, 100},
            {100, 150},
            {0, 200}
    };


    public static final double[][] SYMMETRIC_LAYOUT_NOMINAL_3 = new double[][]{
            {0, 0},
            {-1, 1},
            {0, 1},
            {1, 1},
            {0, 2}
    };

    public static final int[][] FULL_RELATION_INTERVAL_4 = new int[][]{
            {0, 0, 0, 0},
            {1, 0, 0, 0},
            {0, 1, 0, 0},
            {1, 1, 0, 0},
            {1, 0, 1, 0},
            {0, 1, 0, 1},
            {1, 1, 0, 1},
            {1, 1, 1, 0},
            {1, 1, 1, 1}
    };

    public static final double[][] LAYOUT_INTERVAL_4_ASSYMETRIC = new double[][]{
            {120, 0},
            {80, 60},
            {160, 60},
            {120, 120},
            {40, 120},
            {200, 120},
            {160, 200},
            {80, 200},
            {120, 230}
    };


    public static final double PRECISION = 0.01;
}

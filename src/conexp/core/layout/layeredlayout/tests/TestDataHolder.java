package conexp.core.layout.layeredlayout.tests;

/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 */
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
        {0, 1},
        {1, 1},
        {0, 1}
    };

    public static final double[][] SYMMETRIC_LAYOUT_NOMINAL_3 = new double[][]{
                        {0, 0},
                        {-1, 1},
                        {0, 1},
                        {1, 1},
                        {0, 2}
                    };
    public static final double PRECISION = 0.01;
}

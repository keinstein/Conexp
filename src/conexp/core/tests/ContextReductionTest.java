/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.core.tests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import conexp.core.Context;

public class ContextReductionTest extends TestCase {
    private static final Class THIS = ContextReductionTest.class;
    public static final int[][] BURMEISTER__EXAMPLE = new int[][]{
                        {0, 0, 0, 1, 0, 1, 1, 1, },
                        {1, 1, 1, 1, 1, 1, 1, 1, },
                        {1, 1, 1, 0, 0, 1, 1, 1, },
                        {1, 1, 1, 1, 1, 0, 1, 1, },
                        {1, 0, 0, 1, 1, 0, 0, 0, },
                        {1, 1, 1, 1, 1, 0, 0, 1, },
                        {0, 0, 0, 1, 0, 0, 1, 1, },
                        {1, 1, 1, 0, 0, 0, 0, 1, },
                        {1, 0, 0, 0, 0, 0, 0, 0, },
                        {1, 1, 1, 0, 0, 0, 0, 0, },
                        {0, 1, 0, 0, 0, 0, 0, 0, },
                        {0, 1, 0, 0, 0, 0, 0, 1, },
                        {0, 1, 0, 0, 0, 0, 0, 1, },
                        {0, 1, 0, 1, 0, 0, 0, 1, },
                        {1, 0, 0, 1, 1, 0, 0, 1, },
                        {1, 0, 0, 0, 0, 0, 0, 1, },
                        {1, 1, 1, 0, 0, 0, 1, 1, }
                    };
    public static final int[][] BURMEISTER_EXAMPLE_REDUCED = new int[][]{
                {0, 0, 0, 1, 0, 1, 1, 1, },
                {1, 1, 1, 0, 0, 1, 1, 1, },
                {1, 1, 1, 1, 1, 0, 1, 1, },
                {1, 0, 0, 1, 1, 0, 0, 0, },
                {1, 1, 1, 1, 1, 0, 0, 1, },
                {1, 1, 1, 0, 0, 0, 0, 0, },
                {0, 1, 0, 1, 0, 0, 0, 1, },
                {1, 0, 0, 1, 1, 0, 0, 1, },
            };

    public static Test suite() {
        return new TestSuite(THIS);
    }

    public void testReduceObjects() {
/*
          --------
        0!...x.xxx!
        x!xxxxxxxx!
       -x!xxx..xxx!
      x+1!xxxxx.xx!
    x,x+1!x..xx...!
      x3!xxxxx..x!
        1!...x..xx!
     -x3!xxx....x!
  -x,-x-1!x.......!
   x,-x,x!xxx.....!
  -x,-x+1!.x......!
    x3-x!.x.....x!
-x,0,-x+1!.x.....x!
  x,0,x-1!.x.x...x!
      e^x!x..xx..x!
     e^-x!x......x!
     -x+1!xxx...xx!
         ----------
*/

        Context cxt = SetBuilder.makeContext(BURMEISTER__EXAMPLE);

/*
           s ms
          iubot
          nrinm
          jjj_ol s
          eeewniat
          kkka_nfe
          tttcweft
          iiihaaii
          vvvscrng
          --------
        0!...x.xxx!
       -x!xxx..xxx!
      x+1!xxxxx.xx!
    x,x+1!x..xx...!
      x3!xxxxx..x!
   x,-x,x!xxx.....!
  x,0,x-1!.x.x...x!
      e^x!x..xx..x!
         ----------
 */

        Context expReduced = SetBuilder.makeContext(BURMEISTER_EXAMPLE_REDUCED);

        cxt.reduceObjects();
        assertEquals(expReduced.getRelation(), cxt.getRelation());
        cxt.reduceObjects();
        assertEquals(expReduced.getRelation(), cxt.getRelation());
    }

}

package conexp.core.tests;

import conexp.core.AttributeInformationSupplier;
import conexp.core.Implication;
import conexp.core.ImplicationSet;
import util.Assert;

/**
 * User: sergey
 * Date: 26/6/2005
 * Time: 2:51:19
 */
public class ImplicationsBuilder {
    private ImplicationsBuilder() {
        super();
    }

    public static Implication makeImplication(int[] premise, int[] concslusion,
                                              int support) {
        return new Implication(SetBuilder.makeSet(premise),
                SetBuilder.makeSet(concslusion), support);
    }

    public static Implication makeImplication(int[] premise, int[] concslusion) {
        return makeImplication(premise, concslusion, 0);
    }

    public static Implication makeImplication(int[][] implication) {
        return makeImplication(implication[0], implication[1]);
    }

    public static ImplicationSet makeImplicationSet(
            AttributeInformationSupplier attrInfo,
            int[][][] implicationDescriptions) {
        ImplicationSet ret = new ImplicationSet(attrInfo);
        for (int i = 0; i < implicationDescriptions.length; i++) {
            Implication dep;

            int[][] implicationDescription = implicationDescriptions[i];
            if (implicationDescription.length == 2) {
                dep = makeImplication(implicationDescription[0],
                                implicationDescription[1]);
            } else {
                Assert.isTrue(implicationDescription.length == 3);
                dep = makeImplication(implicationDescription[0],
                                implicationDescription[1],
                                implicationDescription[2][0]);
            }
            ret.addImplication(dep);
        }
        return ret;
    }
}

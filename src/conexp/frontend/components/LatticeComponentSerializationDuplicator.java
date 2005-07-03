package conexp.frontend.components;

import conexp.frontend.io.ConExpXMLReader;
import conexp.frontend.io.ConExpXMLWriter;
import util.DataFormatException;

/**
 * User: sergey
 * Date: 23/5/2005
 * Time: 16:20:53
 */
public class LatticeComponentSerializationDuplicator implements ILatticeComponentDuplicator {
    public LatticeComponent duplicate(LatticeComponent toCopy) {

        LatticeComponent copy = new LatticeComponent(toCopy.getContext(), toCopy.attributeMask.makeCopy(),
                toCopy.objectMask.makeCopy());
        if(toCopy.isRecalcLatticeOnMaskChange()){
            copy.setUpLatticeRecalcOnMasksChange();
        }

        //todo: move storing isRecalcLatticeOnMaskChange to Reader/Writer pair
        return duplicateContent(toCopy, copy);
    }

    public LatticeComponent duplicateContent(LatticeComponent toCopy, LatticeComponent copy) {
        try {
            ConExpXMLReader.loadLatticeComponent(copy, ConExpXMLWriter.makeLatticeElement(toCopy));
        } catch (DataFormatException e) {

        }
        return copy;
    }


}

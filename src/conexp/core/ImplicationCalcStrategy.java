package conexp.core;

/**
 * Creation date: (02.07.01 18:28:15)
 * @author Sergey Yevtushenko
 */
public interface ImplicationCalcStrategy extends BinaryRelationProcessor {
    void setImplications(ImplicationSet implSet);
    void calcDuquenneGuiguiesSet();
}
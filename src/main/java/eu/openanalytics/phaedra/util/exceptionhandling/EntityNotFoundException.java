package eu.openanalytics.phaedra.util.exceptionhandling;

abstract public class EntityNotFoundException extends UserVisibleException {

    public EntityNotFoundException(String msg) {
        super(msg);
    }

}
